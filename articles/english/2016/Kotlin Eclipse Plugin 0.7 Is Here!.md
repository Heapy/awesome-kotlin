---
title: 'Kotlin Eclipse Plugin 0.7 Is Here!'
url: http://blog.jetbrains.com/kotlin/2016/06/kotlin-eclipse-plugin-0-7-is-here/
categories:
    - Kotlin
    - Eclipse
author: Nikolay Krasko
date: Jun 03, 2016 00:00
---
We are happy to present a new release of our plugin for Eclipse IDE. Along with the support for Kotlin **1.0.2** compiler, this update brings very important features and improvements.

The code formatting feature was rebuilt in this release. Instead of our first naive implementation we have mostly managed to port the advanced formatter from the Kotlin IntelliJ Idea plugin into Eclipse. This means that [a lot of fixes](https://youtrack.jetbrains.com/issues/KT?q=Formatter%20State:%20Fixed%20Subsystems:%20IDE) are already there and upcoming improvements will be picked up automatically!

![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/fromater.gif)

New line auto-indent also benefitted from this code reuse and now shows far more predictable and smart behaviour.

It was possible to add missing classes imports one-by-one with a quick-fix since 0.1.0 version, and now we’ve improved on that by introducing the _Organize Imports_ feature. It will clean unused imports, add missing imports for classes used in the file and resort them.

![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/organize.gif)

Our completion got several fixes in prioritizing variants and is now far more usable. Also not-imported classes are now suggested in completion popup at once and will be inserted together with the corresponding import.

![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/import.gif)

Several quick-fixes about missing or illegal modifiers were added:

*   It’s now possible to add an open modifier to a declaration which is overridden or subclassed.
    ![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/open.gif)
*   It’s now easy to deal with the _“class must be declared abstract”_ compiler error.
    ![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/abstract.png)

*   Invalid modifier removing is now also available from the quick-fix popup.
    ![](http://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2016/06/modifiers.png)

Please give it a try.

[![Drag to your running Eclipse workspace to install Kotlin Plugin for Eclipse](http://i2.wp.com/marketplace.eclipse.org/sites/all/themes/solstice/_themes/solstice_marketplace/public/images/btn-install.png?zoom=1.5&w=640)](http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2257536 "Drag to your running Eclipse workspace to install Kotlin Plugin for Eclipse")

If you run into any problems or encounter missing features, please don’t hesitate to leave your feedback here or file issues in YouTrack.
