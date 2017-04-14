---
title: 'Kotlin XML Binding'
url: http://jonnyzzz.com/blog/2016/01/06/kotlin-xml/
categories:
    - kotlin
    - xml
    - dsl
author: Eugene Petrenko
date: Jan 06, 2016 21:02
---

I like creating DSLs in [Kotlin](https://kotlinlang.org/). It was fun to try creating a DSL for
XML data binding. Of course there are so many other libraries for JVM that implements it in the other way.

I was looking for a way to use XPath like queries for data binding. I did not want to parse
queries from raw strings. The DSL inherited some ideas from XPath queries.

In Kotlin I used
[Delegated Properties](https://kotlinlang.org/docs/reference/delegated-properties.html)
to achieve better readability, avoid explicit type names for serialization/deserialization rules.

Let's consider an example. Say you have an XML:
```xml
<project>
  <settings type="q">
     <name>Foo</name>
  </settings>
  <keys>
     <key>A</key>
     <key>B</key>
     <key>C</key>
  </keys>
</project>
```


Thanks to the [kotlin.xml.bind](https://github.com/jonnyzzz/kotlin.xml.bind), you may
parse it with the following DSL in Kotlin:

```kotlin
class Project {
  var type by JXML / XAttribute("type")
  var name by JXML / "name" / XText
  var keys by JXML / "keys" / XElements("key") / XText
  var any by JXML / "parameters" / XAnyElements / XSub(Sub::class.java)
  var unknownElements by JXML / XUnknown
}
```

Kotlin compiler infers types for properties, so one should not write types at all.
In the example above `type` and `name` properties are `String?`,
`keys` is `List<String>?`. `XAnyElements` means result will be as collection, `XSub`
parses sub-elements as `XSub` objects.

[kotlin.xml.bind](https://github.com/jonnyzzz/kotlin.xml.bind) provides serialization and deserialization.

Sources / Binaries
==================
Sources are on GitHub under Apache 2.0 license. Binaries are published to a maven repo.

For more information, see [kotlin.xml.bind](https://github.com/jonnyzzz/kotlin.xml.bind) project on GitHub.


Implementation details
======================
I use [JDOM](http://www.jdom.org/) to work with XML. DSL is done in separate module that does not depend on JDOM directly.

You may find API declarations [here](https://github.com/jonnyzzz/kotlin.xml.bind/blob/master/api/src/main/kotlin/org/jonnyzzz/kotlin/xml/bind/XBind.API.kt).
For every DSL [grammar rule](https://en.wikipedia.org/wiki/Terminal_and_nonterminal_symbols) of the DSL I created an interface in Kotlin.

The main trick is that expressions like `JXML / "keys" / XElements("key") / XText` returns an object
that implements requirements of
[Delegated Properties](https://kotlinlang.org/docs/reference/delegated-properties.html)
.

The library adds a tiny overhead on every object creation. So now to create an object (e.g. `Project` class above)
it has to create a number of objects from the DSL that are used behind delegated properties. Reflections
are still in use to scan available object properties too on serialization/deserialization.

For tests I created yet another DSL to generate XML easily.
You may find more details in [kotlin.xml.dsl](https://github.com/jonnyzzz/kotlin.xml.dsl) on GitHub.
This is an example of unit test

```kotlin
@Test
public fun test_read_any_element_does_not_include_parsed() {
  class Data {
    var X by JXML / "x" / XUnknown
    var Y by JXML / "Y" / XUnknown
    var Z by JXML / XAnyElements / XUnknown
  }

  val el = jdom("aaa") {
    element("x") { text("yohoho")}
    element("Y") { text("123")}
    element("z") { text("www")}
    element("p") { text("www")}
  }

  val d : Data = JDOM.load(el , Data::class.java)

  Assert.assertEquals(d.Z?.size, 2)
  Assert.assertEquals(d.Z?.get(0)?.name, "z")
  Assert.assertEquals(d.Z?.get(1)?.name, "p")
}
```

I use [Gradle](https://gradle.org/) as project model now.
Artifacts are deployed to [Bintray](https://bintray.com/jonnyzzz/maven)

Future Work
===========
The ides of the DSL are easily mapped to JSON too. So it's possible to read/write/update JSON files in the same way
Get rid of reflections and use [Companion Objects](https://kotlinlang.org/docs/reference/object-declarations.html#companion-objects)
Support other XML implementations, not only JDOM
Improve, finalize and document binding DSL
Invite contributors!
Have more fun!

