
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
When I started to code in kotlin, one of the libraries that I found that was really useful is [PaperParcel](https://github.com/grandstaish/paperparcel). PaperParcel is a library that would generate Parcelable using annotation. The good thing about it, is that it will reduce the amount of code and mistake you could make if you manually create those classes. Having said that, the library is not purely for Kotlin and it could be used in Java Android project.  

To use it in kotlin  
You will need to add these to your app build gradle dependencies  

```gradle
compile 'com.github.grandstaish.paperparcel:paperparcel:1.0.0-rc4'
compile 'com.github.grandstaish.paperparcel:paperparcel-kotlin:1.0.0-rc4'
kapt 'com.github.grandstaish.paperparcel:compiler:1.0.0-rc4'
```

while still in that gradle file, add these before dependencies  

```gradle
kapt {
    generateStubs = true
}
repositories {
    maven { url 'https://jitpack.io' }
}
```

Now the fun part, this is taken from my app [Daily Picture Quotes](https://play.google.com/store/apps/details?id=com.monmonja.dailyPictureQuotes)  

```kotlin
@PaperParcel
data class QuoteImage(val id: String = "",
                 val name: String = "",
                 val text_quote: String = "",
                 val url: String = "",
                 val created: String = "",
                 val cursor:String? = "") : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(QuoteImage::class.java)
    }
}
```

Thats about it, you can use QuoteImage to pass around Activities or Fragment or use it with your custom class.  

Two things you have to remember is that your data class name must be supplied in PaperParcelable.Creator and every time you make changes to this data class (atleast for me) you have to do rebuild project.  

If this interest you then have a check on the [kotlin usage](https://github.com/grandstaish/paperparcel/wiki/Kotlin-Usage) section of their github page.  

Hope this helps :)  
"""

Article(
  title = "PaperParcel with Kotlin",
  url = "http://www.tutorialforandroid.com/2016/08/paperparcel-with-kotlin.html",
  categories = listOf(
    "Kotlin",
    "Android"
  ),
  type = article,
  lang = EN,
  author = "Almond Joseph Mendoza",
  date = LocalDate.of(2016, 8, 10),
  body = body
)
