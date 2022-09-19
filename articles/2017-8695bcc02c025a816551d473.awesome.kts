

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
[Avian](https://readytalk.github.io/avian/) is a lightweight JVM that will allow you to create self-contained apps. That means that you can ship your app without forcing your users to install any JRE. In here, we will follow simple steps that are going to allow us to create a stand-alone hello-world app using Kotlin.

## Why Kotlin?

Kotlin is a great language with a lot of potential. I am not going to go through all the advantages of it since a lot of people before me has done it already. So we will leave it at that, great language with a lot of potential. And if you want to read why, there’s plenty of info online.

## Why Avian?

One of the biggest challenges that Java has is the fact that it requires a Java Runtime Environment. That means that if I write a Java program, every single one of my users will need to download, install, and maybe configure the JRE before they can even launch my program. And in some cases, people are going to prefer not to install certain software to avoid the hassle, if they don’t have a JRE already (or if they have a non compatible version of the JRE.)

## The Result

What we are going to finish with is a 1.9MB binary for macOS or 2.2MB for Linux (with some little changes it should work with the other supported platforms, see [here](https://github.com/ReadyTalk/avian/blob/master/README.md),) that once executed will greet the user and print all the arguments that it received. Like this:

```
$ ./hello Miguel Castiblanco
Hello from Kotlin and Avian
args:
-Miguel
-Castiblanco
```

## 1. Before we start

 *  ${"$"}JAVA\_HOME must be pointing to the correct location
 *  Kotlin must be installed, and kotlinc (Kotlin compiler) must be in ${"$"}PATH
 *  The commands below are written to work in macOS. Unless mentioned, the command should work in Linux by changing macosx to linux, and the architecture (if needed.) For example *macosx-x86\_64* would become *linux-i386* or *linux-x86\_64*
 *  This tutorial is based on the [README](https://github.com/ReadyTalk/avian/blob/master/README.md) of Avian. Most of my work was to make it work with Kotlin instead of Java, and to put it in groups of instructions that makes sense for people new to Avian along with explanations of what we are doing.

## 2. Getting and building Avian

Let’s start by getting the latest version of Avian:

```
$ git clone https://github.com/ReadyTalk/avian.git
```

Then build it with the default configuration, and test that it’s working

```
$ cd avian
$ make
$ build/macosx-x86_64/avian -cp build/macosx-x86_64/test Hello
```

The last command should print “hello, world!” if everything is correct.

## 3. Writing our simple Kotlin program, and packing it

Let’s create a folder, put our little Kotlin script there, and pack it into a jar.

```
$ cd ../
$ mkdir helloKotlin && cd helloKotlin
$ cat >Hello.kt <<EOF
fun main(args: Array<String>) {
   println("Hello from Kotlin and Avian")
   println("args: ")
   args.forEach {
      println("-\${"$"}it")
   }
}
EOF
$ kotlinc Hello.kt -include-runtime -d boot.jar
```

## 4. Preparing Avian’s runtime to be merged with Kotlin’s

Notice that we are compiling including Kotlin’s runtime, that means that if you run un*zip -l boot.jar* you will see all Kotlin’s classes in there. This is important since we want a stand-alone application.

We are going to get Avian’s runtime and extract it, and also get the files needed to create a binary with Avian.

```
$ ar x ../avian/build/macosx-x86_64/libavian.a
$ mkdir avian-cp
$ cp ../avian/build/macosx-x86_64/classpath.jar avian-cp/avian-cp.jar
$ cd avian-cp/
$ unzip avian-cp.jar && rm -rf avian-cp.jar
```

## 5. Merging Avian, Kotlin, and our app in one jar

Here we create one jar that has Avian’s runtime, Kotlin’s runtime, and our little application. We’ll just merge all the content that we extracted before from *avian-cp.jar* into *boot.jar* (which already has Kotlin and our code.)

```
$ mv ../boot.jar .
$ zip -r boot.jar META-INF avian dalvik java libcore sun
$ mv boot.jar ../ && cd ../
```

If you run un*zip -l boot.jar* now, you will see that all the classes are now happy together.

## 6. Creating a binary

Now we have a self-contained jar. Left is only to use Avian to create a binary out of the jar. First we create an object from the jar.

```
$ ../avian/build/macosx-x86_64/binaryToObject/binaryToObject boot.jar \boot-jar.o _binary_boot_jar_start _binary_boot_jar_end macosx x86_64
```

The following command will create the c++ main class for our binary. Please notice that *FindClass* is looking for **HelloKt**, since the classes compiled with *kotlinc* wil have a *Kt* suffix (*javac* compiles *Hello.java* into *Hello.class*, whereas *kotlinc* compiles *Hello.kt* into *HelloKt.class.*)

```
$ cat >embedded-jar-main.cpp <<EOF
#include "stdint.h"
#include "jni.h"
#include "stdlib.h"

#if (defined __MINGW32__) || (defined _MSC_VER)
#  define EXPORT __declspec(dllexport)
#else
#  define EXPORT __attribute__ ((visibility("default"))) \
  __attribute__ ((used))
#endif

#if (! defined __x86_64__) && ((defined __MINGW32__) || (defined _MSC_VER))
#  define SYMBOL(x) binary_boot_jar_##x
#else
#  define SYMBOL(x) _binary_boot_jar_##x
#endif

extern "C" {

extern const uint8_t SYMBOL(start)[];
  extern const uint8_t SYMBOL(end)[];

EXPORT const uint8_t*
  bootJar(size_t* size)
  {
    *size = SYMBOL(end) - SYMBOL(start);
    return SYMBOL(start);
  }

} // extern "C"

extern "C" void __cxa_pure_virtual(void) { abort(); }

int
main(int ac, const char** av)
{
  JavaVMInitArgs vmArgs;
  vmArgs.version = JNI_VERSION_1_2;
  vmArgs.nOptions = 1;
  vmArgs.ignoreUnrecognized = JNI_TRUE;

JavaVMOption options[vmArgs.nOptions];
  vmArgs.options = options;

options[0].optionString = const_cast<char*>("-Xbootclasspath:[bootJar]");

JavaVM* vm;
  void* env;
  JNI_CreateJavaVM(&vm, &env, &vmArgs);
  JNIEnv* e = static_cast<JNIEnv*>(env);

jclass c = e->FindClass("HelloKt");
  if (not e->ExceptionCheck()) {
    jmethodID m = e->GetStaticMethodID(c, "main", "([Ljava/lang/String;)V");
    if (not e->ExceptionCheck()) {
      jclass stringClass = e->FindClass("java/lang/String");
      if (not e->ExceptionCheck()) {
        jobjectArray a = e->NewObjectArray(ac-1, stringClass, 0);
        if (not e->ExceptionCheck()) {
          for (int i = 1; i < ac; ++i) {
            e->SetObjectArrayElement(a, i-1, e->NewStringUTF(av[i]));
          }

e->CallStaticVoidMethod(c, m, a);
        }
      }
    }
  }

int exitCode = 0;
  if (e->ExceptionCheck()) {
    exitCode = -1;
    e->ExceptionDescribe();
  }

vm->DestroyJavaVM();

return exitCode;
}
EOF
```

Now we will compile the C++ class into an object, and link all the objects (notice that in step 4 we copied a bunch of Avian’s objects into the current folder) to finally create our binary, that will be called *hello*.

**For macOS:**

```
$ g++ -I${"$"}JAVA_HOME/include -I${"$"}JAVA_HOME/include/darwin \
     -D_JNI_IMPLEMENTATION_ -c embedded-jar-main.cpp -o main.o
$ g++ -rdynamic *.o -ldl -lpthread -lz -o hello -framework CoreFoundation
$ strip -S -x hello
```

**For Linux:**

```
$ g++ -I${"$"}JAVA_HOME/include -I${"$"}JAVA_HOME/include/linux \
     -D_JNI_IMPLEMENTATION_ -c embedded-jar-main.cpp -o main.o
$ g++ -rdynamic *.o -ldl -lpthread -lz -o hello
$ strip --strip-all hello
```

## 7. Run it

Now we can finally run it and see how it works

```
$ ./hello Kotlin Avian
Hello from Kotlin and Avian
args:
-Kotlin
-Avian
$ ls -lha hello
-rwxr-xr-x 1 starcarr starcarr 2.0M Feb  8 17:03 hello
```

## Conclusions

 *  This process is long and of course it can potentially be transformed into a script that does all the work for us. That being said, it’s important to take the time to do it this way at least once in order to understand how Avian works so that we can troubleshoot when things go south
 *  Avian is opening the door to stand-alone Kotlin and Java apps, which will allow us to create script or utility applications that can be distributed easily for JRE-less machines
 *  The total size of the application was 1.9MB (2.2MB in Linux), which goes to show that Avian is effectively lightweight
"""

Article(
  title = "Creating a self-contained Kotlin program using Avian",
  url = "https://hackernoon.com/creating-a-self-contained-kotlin-app-using-avian-ca7f2987fdd5",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Miguel Castiblanco",
  date = LocalDate.of(2017, 2, 9),
  body = body
)
