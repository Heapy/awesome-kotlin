
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
### Kotlin Native

A frequent question about Kotlin is if/when it will support compilation to native binaries that run without a JVM. Usually this takes the form of a more technical question like, “will the Kotlin compiler get an LLVM backend?”

I am not on the JetBrains team and I believe they’ve already made up their minds to do this, but I don’t personally think this feature would be the best way to solve the problems that are driving these questions.

Luckily, there are better ways that can. Kotlin doesn’t need an LLVM backend: the wider JVM community is producing all the needed puzzle pieces already. Someone just has to put them together.

### Why do people want native binaries?

Here’s an entirely unscientific summary of the top reasons why people so often request a native backend for managed languages, based on conversations I’ve had over the years:

1. They think the code would run faster or use less memory
2. They want better startup time
3. They think deploying programs that have only one file is simpler
4. They want to avoid garbage collection
5. They want to run on iOS, which forbids JIT compilers by policy
6. They want better native-code interop

This list covers two of the three reasons cited by [the Scala Native website](http://www.scala-native.org/) for why it exists. The other reason is that Scala Native is extending the language to support C++ like constructs that are normally forbidden by the JVM, like forced stack allocation, but building a better C++ is already being handled by Rust. I suspect that trying to convert Scala or Kotlin into C++/Rust competitors wouldn’t go well.

Sometimes people think a ‘native’ version of a managed language would fix problems that have nothing to do with compiler technology, like [this guy](https://news.ycombinator.com/item?id=11226023) who believes it would change font rendering. I’ve seen ‘native’ become a mythical cure-all for any frustration or problem the user might have ... which isn’t surprising, given that most people have no experience with managed-to-native technologies.

### A ‘Kotlin Native’ would make slower code

It is a common belief that code compiled by an ahead-of-time compiler must be faster or less memory hungry than just-in-time compiled code. I used to believe this too. It makes sense: C++ apps run fast, and C++ compilers are very slow. It stands to reason that the compilers are slow because they’re spending a lot of time optimising the code they’re producing. Surely, a compiler that can only grab a handful of spare cycles here and there whilst the app is running can never do such a good job?

Unfortunately, performance is subtle and often unintuitive. C++ compilers are slow mostly due to the #include and generics model the language uses, not due to optimisations. And a team at Oracle is adding ahead of time compilation to HotSpot. They gave a tech talk about it last year called “[Java goes AOT](https://www.youtube.com/watch?v=Xybzyv8qbOc)”. Plain old AOT seems to give around 10%–20% slower code, depending on the kind of app (in the talk this is the difference between tiered and non-tiered). The reason is that virtual machines that support deoptimization, as the JVM does, can make speculative optimisations that aren’t always correct. Programs written in higher level languages benefit from this technique more, for instance Scala code benefits more than Java code does. The AOT compiler they’re using (Graal) isn’t weak or amateur: it’s competitive with GCC when compiling C code. It’s just that these advanced optimisations are really powerful.

It’s for this reason that the HotSpot AOT mode actually supports using a mix of AOT and JIT compiled code. The app can be compiled ahead of time so the interpreter isn’t used, but in a way that self-profiles. Then the JIT compiler is still invoked at runtime to recompile the hot spots, winning back most of the performance loss. You get a much less severe warmup period, whilst still obtaining the best peak performance.

Android has learned the same lesson. The replacement for Dalvik that was introduced in Marshmallow (ART) started out as a pure AOT compiler. In Android N, it will start using a mix of AOT and JIT compilation.

Additionally, the shortest path to a Kotlin-specific native compiler backend would be LLVM. LLVM is primarily used to compile C and C++. Many optimisations that can really help high level managed languages like Kotlin simply don’t apply to C++, so LLVM doesn’t implement them at all. That’d result in an even bigger speed hit.

> **Sidenote**: the .NET virtual machine does not have an interpreter nor does it support speculative optimisations. It’s really just a regular compiler that happens to run the first time a method is called. This is why Microsoft has tended to be more interested in AOT compilation than the Java space has been: they never exploited the potential of JIT compilation, so they have less to lose from abandoning it.

What about other forms of bloat, like memory usage, binary size and startup time?

Even those are complicated. Most of the memory usage of apps written in managed languages like Kotlin, Scala, Java, C# etc comes from their reliance on object identity, garbage collection, unicode strings and other things that native compilation doesn’t affect.

Worse, native CPU code is a lot larger than JVM bytecode. Larger code bloats downloads and uses more RAM, lowering cache utilisation. An AOT compiled “Hello World” Java app doesn’t start any faster than the regular interpreted version because even though the interpreter runs far fewer instructions per second than the CPU can, each instruction does a lot more and takes much less space in memory. Runtime of a hello world app is only about 80 milliseconds anyway, which is a relevant cost only if you’re making tiny tools for a UNIX shell.

And whilst hauling around a couple of JIT compilers and three garbage collectors adds bloat, that’s not inherent to using a virtual machine rather than compiling to native. It’s just that HotSpot is a one-size-fits-all program that is designed to run on everything from laptops to giant servers. You can make much smaller virtual machines if you’re willing to specialise.

### Enter Avian

> “Avian is a lightweight virtual machine and class library designed to provide a useful subset of Java’s features, suitable for building self-contained applications.”

So says [the website](https://readytalk.github.io/avian/). They aren’t joking. The example app demos use of the native UI toolkit on Windows, MacOS X or Linux. It’s not a trivial Hello World app at all, yet it’s a standalone self-contained binary that clocks in at only one megabyte. In contrast, “Hello World” in Go generates a binary that is 1.1mb in size, despite doing much less.

Avian can get these tiny sizes because it’s fully focused on doing so: it implements optimisations and features the standard HotSpot JVM lacks, like the use of LZMA compression and ProGuard to strip the standard libraries. Yet it still provides a garbage collector and a JIT compiler.

For people who want to use Kotlin to write small, self contained command line apps of the kind Go is sometimes used for, a much simpler and better solution than an LLVM backend would be to make a fully integrated Avian/Kotlin combination. Avian is hard to use right now — you’re expected to be familiar with native-world tools like GCC and make. Making a one-click JetBrains style GUI would yield programs that _look_ to the user like they were AOT compiled: they’re single executables that only require the base OS. And using SWT you can build GUIs that look native on every platform because under the hood they _are_ native. But you wouldn’t need to abandon the benefits of JIT compilation or garbage collection.

### Losing garbage collection

Sometimes the people requesting a native backend want it because they want to avoid GC, and associate “native” with “not garbage collected”. There is no connection between these things: you can have garbage collected C++ and you can do manual memory management in Java (and some high performance libraries do).

The problem with extensively mixing techniques is that it forks the language. A library that assumes garbage collection cannot be used without a collector and likewise, a library that expects manual management becomes a lot harder to use from code that expects a GC. You’d have to introduce smart pointers and other C++ like constructs to the language to make it really convenient.

I wouldn’t like to see Kotlin splinter into two different languages, Kotlin-GC and Kotlin-Manual. That would hurt the community and ecosystem for questionable benefits.

And the benefits _are_ questionable. Many devs who think they can’t tolerate GC are basing their opinions on old/crappy GCs in mobile phones or (worse) web browsers. This impression is heightened by the fact that some well known garbage collected apps are written by people who just don’t seem to care about performance at all, like Minecraft or Eclipse, leading people to blame GC for what is in reality just badly written code. But there are counterexamples that show it doesn’t have to be this way: Unreal Engine is written in C++ and has used a core garbage collected game heap since version 3. It powers many of the worlds AAA titles. They can hit 60 frames per second and they are using a very basic GC. Tim Sweeney’s secret is that he cares about performance and productivity simultaneously. If they can do it, so can you.

### iOS and native code interop

The final reasons people want a native compiler backend are iOS support and to make native code interop easier.

iOS is a good reason. That platform bans JIT compilers because it helps Apple enforce their incredibly rigid policies. But doing a native backend at the language level is the wrong approach. RoboVM is a project that built a JVM bytecode to ARM AOT compiler, and although RoboVM is now a dead project due to being acquired by Microsoft, old versions of its code are still available under an open source license. It works for any JVM language and doesn’t really suffer from this generality: a Scala or Kotlin specific ARM compiler wouldn’t do much different.

But that’s probably not the long term direction the JVM platform will go in for iOS. As HotSpot itself is getting support for AOT compilation, and HotSpot has an ARM backend too, and there’s an official OpenJDK mobile project that’s already made an iOS (interpreter only) mobile version, it would make sense for them to plug these things together and end up with a mode in which HotSpot can generate AOT iOS binaries too. I wouldn’t be surprised to see something like this announced between Java 9 and 10.

The final reason is native code interop. If you compile to native, so the reasoning goes, it’ll be easier to use C/C++ libraries like the ones your operating system provides.

But the existence of projects like JNA seem to disprove this — you can have convenient interop without generating native code yourself. And there are some exciting techniques for working with native libraries coming up:

*   The OpenJDK Panama project is adding support for things like inline assembly, pointers and struct layouts directly to Java and HotSpot. Yes, if you check out the Panama branch of the hotspot repository you can actually define assembly snippets in Java and they’ll be inlined directly into usage sites, just like an __asm__ block in C would. Panama also provides a clang-based tool that parses C/C++ headers and auto generates the equivalent Java declarations. All this should be automatically available to Kotlin and Scala users too.
*   The Graal/Truffle research projects are creating [Sulong](http://github.com/graalvm/sulong/), which is a way to JIT compile LLVM bitcode on top of the JVM. There is a simple C API that exists when code is run on top of Sulong that allows for near zero-overhead interop with managed code.

### Conclusion

Kotlin doesn’t need an LLVM backend, and by extension I believe neither does Scala. Creating such a thing would be a huge and ongoing drain of manpower, take a long time, and end up duplicating work already being done elsewhere in the JVM ecosystem ... very likely with worse results.

Instead, I think the right direction for the community to go is:

1.  Building a simple IntelliJ plugin that uses Avian to spit out tiny, self contained binaries, for when weight is more important than features. This would present some strong competition to Go in the command line tools space.
2.  Using the AOT mode being added to HotSpot to eliminate warmup times and (hopefully) support iOS, for times when AOT compilation really is the only solution.
3.  Waiting for JVM upgrades to reduce memory usage, support better native interop and so on. Don’t try to duplicate these efforts at the language level.
4.  Educating the developer community about how to write high performance garbage collected apps _outside_ of the server context.

This approach isn’t flawless: the AOT mode being added to HotSpot is planned to be a commercial feature, and big upgrades like Panama are long term projects. But adding an LLVM backend to the Kotlin or Scala compilers would be a long term project too, it’d mean sacrificing other features that might be more useful, and it would likely never close the performance gap.

As always in engineering, there are no solutions — only trade offs. I’d rather have more features in the core Kotlin language/tooling than a native backend, and let other teams tackle the varying challenges involved.

"""

Article(
  title = "Kotlin Native",
  url = "https://medium.com/@octskyward/kotlin-native-310ffac94af2#.3oep6gxpa",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Mike Hearn",
  date = LocalDate.of(2016, 6, 17),
  body = body
)
