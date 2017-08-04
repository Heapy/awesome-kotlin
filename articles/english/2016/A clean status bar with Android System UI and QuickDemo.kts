
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
More often than necessary, screenshots and videos of Android apps suffer from showing an untidy status bar with unwanted notification icons, a drained battery indicator, and a different time on every screenshot. In this blog post, we take a look at the Android System UI demo mode that remedies the aforementioned problems and present a tool [_QuickDemo_](https://github.com/PSPDFKit-labs/QuickDemo) for fast and convenient status bar cleansing.

> A special _Thank You_ to everyone who joined this month's [Android Heads Meetup](https://www.meetup.com/de-DE/AndroidHeads/events/234524954/) at Google Vienna. It was a real pleasure talking to you. You can [find my talk's slides here](https://speakerdeck.com/davidschreiberranner/writing-a-system-ui-demo-mode-quick-settings-tile-in-kotlin). For all of you that couldn't listen to my presentation, check out this blog post! 😉

At PSPDFKit, we're developing [PDF Viewer](https://pdfviewer.io) – a PDF app for Android and iOS that has a clean look, is simple to use, and enormously powerful! We put a lot of effort into making the app, as well as [the PDF framework powering it](https://pspdfkit.com/blog/2016/pspdfkit-android-2-7/), look as clean as possible. However, our quality control does not end with the app, but extends to screenshots and videos taken of the app, as well. A high quality screenshot speaks for itself and shows the level of detail that goes into every aspect of our products.

![PDF Viewer for Android](https://pspdfkit.com/images/blog/2016/clean-statusbar-with-quickdemo/viewer.opt-cce806d8.gif)

In the past we had two primary ways of capturing clean screenshots and videos on Android:

1.  [_Clean status bar_](https://play.google.com/store/apps/details?id=com.emmaguy.cleanstatusbar) is an app for developers. It draws on top of the original dirty status bar, hiding it below a clean-looking overdraw. While this works well when showing opaque status bars with a fixed background color, it cannot be used for transparent status bars or background colors that dynamically change based on the displayed activity's theme (like the real status bar does). The lack of color adaption is immediately noticeable in videos that show transitions between differently styled activities or when opening modal dialogs that darken the activity and status bar in the background.

![Overdrawn status bar](https://pspdfkit.com/images/blog/2016/clean-statusbar-with-quickdemo/overdrawn-statusbar.opt-dfd3c10f.gif)

_Previously: Opaque drawing on top of the original status bar._

2.  Since the launch of Android 6.0 Marshmallow, we've been using the System UI demo mode – an operating system setting that populates the systems status bar with fake data, to give it a cleaner look. However, since the standard System UI demo mode did not deliver all the features we required, we went ahead and started customizing this component.

Today, we're open sourcing [QuickDemo for Android](https://github.com/PSPDFKit-labs/QuickDemo) – a small tool that helps developers capture clean screenshots and videos of their apps without a cluttered status bar. If you're interested in how QuickDemo works, make sure to read the rest of this blog post. 🙌

![QuickDemo in action](https://pspdfkit.com/images/blog/2016/clean-statusbar-with-quickdemo/quickdemo.opt-b0e6c6d6.gif)

## Android System UI

With Android 6.0 Marshmallow, Google introduced a new customizable system component, the _System UI Tuner_. Once enabled, the System UI Tuner allows you to change appearance of the status bar, notifications, and other system UI components.

One more feature that was added with the System UI Tuner is the so-called "Demo Mode." When enabling the demo mode, Android provides fake data to the status bar, giving it a clean and uncompromised look. This allows for shooting clean screenshots and videos (for blog posts, presentation slides, and your app's Google Play Store page). It's also great for doing live demos, without suffering from a dirty notification tray, empty battery indicators, or a clock showing a time that indicates yet another late night coding marathon.

However, there are two downsides of the "vanilla" Android demo mode:

1.  **Inflexible**: You can't decide on what you would like to show in the status bar. The system settings don't provide any way to customize the demo mode. Luckily, there is a way to do this via system broadcast intents, which we're going to show further down in this article.
2.  **Inconvenient**: It is complicated to enable and disable demo mode, as the switch to enable it is buried deep inside the developer options. That's where _quick settings_ become interesting, since they provide a way to quickly access actions from any screen on your phone.

The next section will look at how to control the demo mode. The final section will round up the QuickDemo app by implementing a quick settings tile.

## Controlling the demo mode

There's an [internal documentation page of the System UI demo mode](https://android.googlesource.com/platform/frameworks/base/+/android-6.0.0_r1/packages/SystemUI/docs/demo_mode.md), which shows how the System UI can be set to demo mode and how demo mode can be configured. All that is required is sending broadcast messages to the System UI, carrying the desired commands.

This is an example of how you could access the demo mode from the command line via `adb`.

```bash
# Sets the clock to 07:00
adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 0700
```

Your app can do the same, with the right permissions. Since demo mode is a system component and not part of the Android framework, your app requires the permission `android.permission.DUMP` to control it.

```xml
<!--  This snippet omits some manifest entries and attributes for brevity. -->
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.pspdfkit.tools.demotile">

    <uses-permission
        android:name="android.permission.DUMP"
        tools:ignore="ProtectedPermissions" />

    ...
</manifest>
```

Moreover, since this is a _protected permission_, you can't simply request that permission at runtime like you do with normal permissions, but need to use `adb` to manually grant that permission.

```bash
adb shell pm grant com.pspdfkit.tools.demotile android.permission.DUMP
```

Note that this must be done _every time you uninstall the app or clear its data_. After permissions have been acquired, you can use a broadcast intent to control the demo mode. The equivalent code for the command to set the time looks like this:

```kotlin
val intent = Intent("com.android.systemui.demo")
intent.putExtra("command", "clock")
intent.putExtra("hhmm", "0700")
context.sendBroadcast(intent)
```

Have a look at the [`DemoMode`](https://github.com/PSPDFKit-labs/QuickDemo/blob/master/app/src/main/kotlin/com/pspdfkit/labs/quickdemo/DemoMode.kt) class in QuickDemo, which implements controls for most of the existing demo mode properties. In the next section, we're implementing a custom quick settings tile, to control the demo mode's visibility with just a single tap.

## Adding a `DemoModeTileService`

Ever since Android launched, the operating system features the status bar and the notification drawer. Both vanilla Android and OEM specific OS versions also provided "quick settings," small buttons at the top of the notification drawer that would give access to important functions like Wi-Fi, flight mode, the phone's torch, etc.

With 5.0 Lollipop and Material design's triumphal entry into Android, Google reworked the quick settings section, making it simpler to use and friendlier on the eye. One major version later, 6.0 Marshmallow refined the quick settings, giving the user the possibility to change his/her order, appearance, or completely remove unwanted items.

Fast forward to 2016 and Android 7.0 Nougat, the Android API 24 added [support for creating custom quick setting tiles](https://developer.android.com/about/versions/nougat/android-7.0.html#tile_api). The new [`TileService`](https://developer.android.com/reference/android/service/quicksettings/TileService.html) provides access to a single quick settings [`Tile`](https://developer.android.com/reference/android/service/quicksettings/Tile.html). An app can register as many tile services (thus tiles) as it wants by declaring them inside its `AndroidManifest.xml` file.

```xml
<!--  This snippet omits some manifest entries and attributes for brevity. -->
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.pspdfkit.tools.demotile">

    <application>
        <service
            android:name=".service.DemoModeTileService"
            android:icon="@drawable/ic_demo_mode"
            android:label="@string/demo_mode_tile_title"
            android:permission="android.permission.BIND_QUICK_SETTINGS_TILE">
            <intent-filter>
                <action android:name="android.service.quicksettings.action.QS_TILE" />
            </intent-filter>
        </service>
    </application>
</manifest>
```

The registered [`DemoModeTileService`](https://github.com/PSPDFKit-labs/QuickDemo/blob/master/app/src/main/kotlin/com/pspdfkit/labs/quickdemo/service/DemoModeTileService.kt) declares the required permissions for using the tiles API, as well as an `<intent-filter>` that will allow the service to register itself inside the "tiles picker" so it can be discovered by the user. Moreover, it specifies the tiles default icon and title. While both values can be changed dynamically at runtime, the operating system will use the manifest's values when displaying the tile inside the tiles picker (think of it as a preview).

Now for implementation. Don't be scared of it – it's really simple. The [`DemoModeTileService`](https://github.com/PSPDFKit-labs/QuickDemo/blob/master/app/src/main/kotlin/com/pspdfkit/labs/quickdemo/service/DemoModeTileService.kt) extends Android framework's [`TileService`](https://developer.android.com/reference/android/service/quicksettings/TileService.html) class. To allow toggling of the demo mode by tapping it, the tile service overrides three methods:

*   [`#onCreate`](https://developer.android.com/reference/android/app/Service.html#onCreate()) is called when the service is created. Our service creates its custom [`DemoMode`](https://github.com/PSPDFKit-labs/QuickDemo/blob/master/app/src/main/kotlin/com/pspdfkit/labs/quickdemo/DemoMode.kt) for controlling visibility of the demo mode.
*   [`#onStartListening`](https://developer.android.com/reference/android/service/quicksettings/TileService.html#onStartListening()) is called when the service should start updating the tile, normally before the tile becomes visible. When this happens the [`DemoModeTileService`](https://github.com/PSPDFKit-labs/QuickDemo/blob/master/app/src/main/kotlin/com/pspdfkit/labs/quickdemo/service/DemoModeTileService.kt) refreshes the tile's title, icon, and state.
*   [`#onClick`](https://developer.android.com/reference/android/service/quicksettings/TileService.html#onClick()) is called whenever the user tapped the tile in the quick settings. This is where we're toggling the demo mode between being enabled or disabled. Since the tile service requires a special permission to control the System UI demo mode, we're also checking for that permission.

```kotlin
class DemoModeTileService : TileService() {

    private lateinit var demoMode: DemoMode

    override fun onCreate() {
        super.onCreate()
        demoMode = DemoMode.get(this)
    }

    override fun onStartListening() {
        qsTile.icon = Icon.createWithResource(this, R.drawable.ic_demo_mode)
        qsTile.label = "Demo mode"
        updateIcon()
    }

    override fun onClick() {
        if(!demoMode.requiredPermissionsGranted) {
            startActivityAndCollapse(SetupGuideActivity.intent(this))
            return
        }

        demoMode.enabled = !demoMode.enabled
        updateIcon()

        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        sendBroadcast(it)
    }

    private fun updateIcon() {
        qsTile.state = if (demoMode.enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
```

That's it! You can check out the full source code of [QuickDemo on GitHub](https://github.com/PSPDFKit-labs/QuickDemo). If you have any questions on the System UI Demo Mode or the Android tiles API, feel free to [ping me on Twitter](https://twitter.com/Flashmasterdash) or [Google+](https://plus.google.com/112371263315253005287).

"""

Article(
  title = "A clean status bar with Android System UI and QuickDemo",
  url = "https://pspdfkit.com/blog/2016/clean-statusbar-with-systemui-and-quickdemo/",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "David Schreiber‑Ranner",
  date = LocalDate.of(2016, 11, 17),
  body = body
)
