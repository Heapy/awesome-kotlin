
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
[Ubuntu Make](https://github.com/ubuntu/ubuntu-make) is a command line tool created by Canonical, which allows installing various development tools on Ubuntu. It can be used to install Android Studio, Unity3D, IntelliJ IDEA, Firefox Development Edition, Node.js and much more.

**Ubuntu Make 16.03, released today, adds support for [Eclipse JEE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/mars2) and [IntelliJ IDEA EAP](https://www.jetbrains.com/support/eap/), as well as for the [Kotlin compiler](https://kotlinlang.org/), along with various fixes, such as:**

* fix Unity3D on lts mesa;
* fix VSCode license page due to server changes;
* fix Android-NDK not working due to server changes (download is now for 64bit only);
* fix Clang support due to server changes;
* fix Intellij .desktop file.

A complete changelog can be found [HERE](https://github.com/ubuntu/ubuntu-make/blob/master/debian/changelog).

![Ubuntu Make Eclipse Java EE IDE](https://2.bp.blogspot.com/-IxqVQB8bEqM/Vvu4uaN9s4I/AAAAAAAAXeY/LC8Uj6BECD8_GBY0DIDpho0-BxlUVDFVA/s1600/ubuntumake-eclipse-jee-ide.png)

To install the the newly added Eclipse JEE in Ubuntu using the latest Ubuntu Make (after installing Ubuntu Make, obviously), use the following command:

`umake ide eclipse-jee`

For IntelliJ IDEA EAP, use:

`umake ide idea --eap`

To install the Kotlin language compiler, use:

`umake kotlin kotlin-lang`

If you want to remove any package installed using Ubuntu Make, simply append "--remove" to the command you used to install it. For example, to remove eclipse-jee, you would use:

`umake ide eclipse-jee --remove`

To see all Ubuntu Make can do, type:

```bash
umake --help
# and
man umake
```

## Install Ubuntu Make

Ubuntu Make is available in the official Ubuntu repositories (starting with Ubuntu 15.04) however, it's not the latest version, as you can see [HERE](https://launchpad.net/ubuntu/+source/ubuntu-make) (though the latest Ubuntu Mate 16.03 will probably make it into Ubuntu 16.04 Xenial Xerus).

**To install the latest Ubuntu Make, in Ubuntu (16.04, 15.10 and 14.04), Linux Mint 17.x and derivatives, you can use its PPA. Add the PPA and install Ubuntu Make by using the following commands:**

```bash
sudo add-apt-repository ppa:ubuntu-desktop/ubuntu-make
sudo apt-get update
sudo apt-get install ubuntu-make
```

If you want to help with the Ubuntu Make development, report bugs, etc., check out its [GitHub page](https://github.com/ubuntu/ubuntu-make).

_via [didrocks](http://blog.didrocks.fr/post/Ubuntu-Make-16.03-features-Eclipse-JEE%2C-Intellij-EAP%2C-Kotlin-and-a-bunch-of-fixes!2)_

"""

Article(
  title = "Ubuntu Make 16.03 Released With Eclipse JEE And IntelliJ IDEA EAP Support, More",
  url = "http://www.webupd8.org/2016/03/ubuntu-make-1603-released-with-eclipse.html",
  categories = listOf(
    "Kotlin",
    "Ubuntu"
  ),
  type = article,
  lang = EN,
  author = "Alin Andrei",
  date = LocalDate.of(2016, 3, 30),
  body = body
)
