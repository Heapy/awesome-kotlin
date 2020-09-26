
import link.kotlin.scripts.Article
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
[Test doubles](http://www.martinfowler.com/bliki/TestDouble.html) are one of the most essential part of unit testing. A test double replaces a class that we need in our test but that is not the focus of our test. We remove that dependency by using a “fake” object. This way our test becomes more robust due to a reduced dependency on other classes. Such a double could be a stub, a dummy or a mock. All of these need the ability to replace an object with something artificial that looks and behaves like the object we need. In Java, the common approach for test doubles is using interfaces: one implementation of the class is the “real” one, and the other one is used for testing.

Writing a lot of unit tests means that all your classes need interfaces. This creates a lot of boilerplate. Therefore it became a common rule for many developers that if you have only one implementation for your interface the interface is useless and can be dropped.

This rule can easily be applied for testing also. As long as we only test the “public contract” of a class we can still replace this by a double. This can be done via extension and overriding or via a mocking framework like [Mockito](https://github.com/mockito/mockito). Mocking classes not only interfaces became pretty common and are a powerful feature. In other languages, like ObjectiveC, where developers do not focus on interfaces as much as in Java, this is the default approach.

### The Kotlin problem

Everything seemed fine, until [Kotlin](https://kotlinlang.org/) came around, a great modern language. Given how well Kotlin and Java work together, the Android developer community was hooked almost from the beginning.
But Kotlin brought a new concept: everything is _final_ by default. The rule: “closed for modifications, open for extension” is baked into the language. Every class is _final_ by default, every method is _final_ by default! Inheritance was widely overused in the last decades and Kotlin tries to make this a bit better.

From a testing perspective this is a problem: if you can not extend a class you can not replace it with any kind of double, not even a generated mock:

```kotlin
org.mockito.exceptions.base.MockitoException:
Cannot mock/spy class com.mypackage.MyKotlinClass
Mockito cannot mock/spy following:
 — final classes
 — anonymous classes
 — primitive types
```

And if the class would not be _final_ you still have all the _final_ methods. But as _final_ methods can not be overridden, real code will suddenly run in your mock. This is something you want to avoid.
The problem itself is not new, especially APIs or different third party code were sealed with _finals_. The developers from [Jayway](https://www.jayway.com/sharing-knowledge/blog/) solved this solution a long time ago by creating [PowerMock](https://github.com/jayway/powermock/).
PowerMock allows to mock _finals_ and even _statics_. The question for us Kotlin developers is: is there a better way, easier way? Using PowerMock is like using a sledge hammer to crack a nut.

What else could we do? Going back to interfaces? This would lead us to the situation described above.
We could open all the classes we need. As we (hopefully) test all the classes, could this lead to opening all our classes!? And would this mean modifying our code just for testing? This sounds wrong! The main feature of the language would be broken.

### An idea to solve it

In an ideal world we would just have something like:

```kotlin
controller = mockFinal()
```

which would be an extension of normal _mock()_ method. Everything this call would need to do, is removing the _final_ modifier from the class and all its public methods.

With Java reflection is very easy to remove the _final_ modifier of a field.
For example:

```kotlin
Field modifiersField = Field.class.getDeclaredField(“modifiers”); modifiersField.setAccessible(true);
modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
```

But for classes and methods things tend to be a bit trickier. You can not achieve this with pure Java reflection API.

But thanks to frameworks like [_javassist_](http://jboss-javassist.github.io/javassist/) there is a way. Javassist is a large toolkit around byte code. It allows the manipulation and even creation of Java classes at runtime. And it also includes a way to change the class and method modifiers:

```kotlin
CtClass clazz = ...
int notFinalModifier = Modifier.clear(clazz.getModifiers(), Modifier.FINAL);
clazz.setModifiers(notFinalModifier);
return clazz.toClass();  // returns new non final class
```

With this, a new non-final class can be easily created. But this leads to the next problem: _javassist_ creates a new class but we can not load the new class into the current test. Because the class is already loaded by the class loader. Two version of it are not allowed:

```kotlin
javassist.CannotCompileException: by java.lang.LinkageError: loader (instance of  sun/misc/Launcher${"$"}AppClassLoader): attempted  duplicate class definition for name: "com/myPackage/MyKotlinClass"
```

So we just need a different class loader right?
The problem: an assignment like

```kotlin
controller = mockFinal()
```

would not work if two different class loaders would be used. So even if we could get passed that Linker error for duplicate class from above we would get a ClassCastException! This is because for the JVM classes are identified by _name, package_ and _class loade_r. So even without modification, instances of two identical classes cannot be assigned to each other if the class loader differs.

This means we need to aim a bit larger than the initial idea: there must be only one class loader for our full test class.
Good news! With JUnit there is way to achieve this. We can change the TestRunner that is used for our class.

```kotlin
public class MyTestRunner extends BlockJUnit4ClassRunner {

    public MyTestRunner(Class<?> clazz) throws InitializationError {
        super(getFromMyClassloader(clazz));
    }

    private static Class<?> getFromMyClassloader(Class<?> clazz) ...
```

then we would write our own _ClassLoader_ and override:

```kotlin
@Override
public Class<?> loadClass(String name) throws ClassNotFoundException { ...
```

Here you can remove the _final_ modifier with _javassist_ as shown above and return the new “better” class.

Have a look on how all of these look when put together:
[_https://github.com/dpreussler/kotlin-testrunner_](https://github.com/dpreussler/kotlin-testrunner)

If you want to use it, all you need to do is to add **_@RunWith(KotlinTestRunner.class)_ **for Java tests
or
**_@RunWith(KotlinTestRunner::class)_ **for Kotlin tests

```kotlin
@RunWith(KotlinTestRunner::class)
class MyKotlinTestclass {
   @Test
   fun test() {
   ...
   }
}
```

With this you will never have to care about if your Kotlin class is _final_ or _open_. Simply mock them, it will work :-)

### Wrap up

For now it seems there is no way around without changing the Testrunner. PowerMock uses the same approach but requires more configuration. So for what we wanted to achieve this is a reasonable solution. Give it a try and let me know how it works for you.

And of course the Runner is not limited to Kotlin. It can also be used for any Java test where a _final_ method or class needs to be mocked.
"""

Article(
  title = "Never say final: mocking Kotlin classes in unit tests",
  url = "https://medium.com/@dpreussler/never-say-final-mocking-kotlin-classes-in-unit-tests-314d275b82b1#.9oldk16f5",
  categories = listOf(
    "Testing",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Danny Preussler",
  date = LocalDate.of(2016, 7, 6),
  body = body
)
