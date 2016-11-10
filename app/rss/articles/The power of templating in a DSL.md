---
title: 'The power of templating in a DSL'
url: http://jonnyzzz.com/blog/2016/09/16/power-of-dsl/
categories:
    - Kotlin
    - DSL
author: Eugene Petrenko
date: Sep 22, 2016 01:25
features:
    - mathjax
---

Welcome a powerful templating engine that 
is available for every DSL in a general 
purpose language.


Introduction
============

Let's consider a general purpose language (e.g. Scala, Java, Kotlin)
and a library (or a DSL API library) which helps to define 
objects for some domain. 

We use the library in a program to yield a domain objects.
This means we are allowed to mix the libraly calls with other 
general language calls features. This forms a templates. 
 
You may recall a `.php` or `.jsp` condition or loop tags as an example.

For the DSL case the general purpose 
language turns into a powerfull templage engine. Unlike string 
templage engines, this approach allows a semantic aware templating as
all calls goes to the DSL API library. 

Semantic aware templates can be used to enrich 
[The DSL Way](http://jonnyzzz.com/blog/2016/09/02/dsl-building/) approach
too. 

Let's consider examples.

A DSL example
=============

I will be using [IntelliJ IDEA](https://www.jetbrains.com/idea/) as an IDE 
and [Kotlin](https://kotlinlang.org) as \\(Target Language \\) below. Suppose
we have a DSL API library for logger configuration implemented in Kotlin. An
evaluation of `log4j` function yield a logger configuration 
(e.g. for [Log4j](http://logging.apache.org/log4j/1.2/)).

Say we have the following code to setup
[loggers](http://jonnyzzz.com/blog/2016/09/09/log4j-dsl/). 

```kotlin
log4j {
  logger("category2warn") {
    + WARN
  }
}
```

The goal is to configure a number of loggers in the exactly 
same way. Thanks to Kotlin language features, one is allowed to use a 
loop, e.g.

```kotlin
log4j {
  listOf("A", "B", "C").forEach {
    logger("category2warn.$it") {
      + WARN
    }
  }
}
```

This is a straitforward example of a template engine. There 
is nothing specific to be done to have a template engine at all. A mix 
of languge features and DSL API library calls forms the template engine.

There are no loops support in the logger configuration itself, 
but thats to DSL API we are allowed to loop over several categories 
to generate all definitions. 

The following part is now a template:

```kotlin
    logger("category2warn.$it") {
      + WARN
    }
}
```

One may go further and decide to extract the collection of categoring into 
a function. So we turn the logger configuration code into the following

```kotlin
log4j {
  listAllRootPackages().forEach {
    logger("category2warn.$it") {
      + WARN
    }
  }
}
```

Here we assume the `listAllRootPackages()` to return a list of 
categories. There is no longer necessary to have this 
function to return a constant list. Instead, it can be implemented
as we like it to, e.g. it may scan an application package to collect
all possible root categories. It may use some resources as the input.

Overall, this is the way to turn a static (and declarative) logger 
configuration to a flexible thing. It is now psedo-declarative. Meaning
there is another program, that yields a declarative configuration script
during a build phase. On that phase all templates are getting substituted.

A next step is to extract the actual category setup code (a template)

```kotlin
log4j {
  listAllRootPackages().forEach {
    declareCategory(it)
  }
}
```

At that point we have a shared library, where a category is declared 
in the *right* way and reused. Next we use a function aka template all other 
the place.


Closing
=======

The examples above show how a general purpose language can 
be turned into a powerfull templating engine for any DSL APIs.

It turns out that a general purpose language features turning 
it to a powerfull template engine for a given DSL. It's up to 
a developer to decide which features to use. The only requirement
is to have a properly designed DSL API, so that such transformations 
were possible.

The example below illustrates a side-effect or a benefit of using 
[The DSL Way]({http://jonnyzzz.com/blog/2016/09/02/dsl-building/)
to extend an IDE **without** writing any IDE specific code.
It shows how powerfull an \\(Original Launguage\\) can be
form the \\(Target Language\\) perspective.

You may take a look to
[the post](http://jonnyzzz.com/blog/2016/09/09/log4j-dsl/)
for more formal DSL description for a logger configurations.
