exports.default = `<main data-scroll="native" role="main" class="postArticle-content js-postField js-notesSource">
    <section name="314a" class=" section--body section--first">
        <div class="section-divider layoutSingleColumn">
            <hr class="section-divider">
        </div>
        <div class="section-content">
            <div class="section-inner layoutSingleColumn"><h3 name="f839" id="f839" class="graf--h3 graf--first">Kotlin
                — Love at first line</h3><p name="4271" id="4271" class="graf--p graf-after--h3">Kotlin 1.0 has been <a
                href="https://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/"
                data-href="https://blog.jetbrains.com/kotlin/2016/02/kotlin-1-0-released-pragmatic-language-for-jvm-and-android/"
                class="markup--anchor markup--p-anchor" rel="nofollow">released</a> a few days ago and it’s time to
                check out its awesome features.</p><p name="6cd9" id="6cd9" class="graf--p graf-after--p">For those of
                you who might have been living under a rock and don’t know what Kotlin is — Kotlin is a new JVM
                programming language that tries to “fill in the gaps” that Java has. It’s 100% interoperable with Java —
                meaning that you can have a mixed project that contains Kotlin &amp; Java classes. The classes are
                compiled to Java bytecode, and that bytecode is “runnable” on Java6+, which makes it runnable on
                Android.</p><p name="8330" id="8330" class="graf--p graf-after--p">The language is awesome, and combined
                with the <a href="https://github.com/Kotlin/anko" data-href="https://github.com/Kotlin/anko"
                            class="markup--anchor markup--p-anchor" rel="nofollow">Anko</a> library it’s even <a
                    href="http://www.urbandictionary.com/define.php?term=Awesome-er"
                    data-href="http://www.urbandictionary.com/define.php?term=Awesome-er"
                    class="markup--anchor markup--p-anchor" rel="nofollow">awesome-er*</a>. I won’t be explaining the
                syntax of the language (for that you have the official <a href="https://kotlinlang.org"
                                                                          data-href="https://kotlinlang.org"
                                                                          class="markup--anchor markup--p-anchor"
                                                                          rel="nofollow">page</a>). I’m just going to
                try and expose a few of it’s awesome features.</p><h4 name="657f" id="657f"
                                                                      class="graf--h4 graf-after--p"><a
                href="https://kotlinlang.org/docs/reference/functions.html#single-expression-functions"
                data-href="https://kotlinlang.org/docs/reference/functions.html#single-expression-functions"
                class="markup--anchor markup--h4-anchor" rel="nofollow">Single-Expression functions</a></h4><p
                name="a17c" id="a17c" class="graf--p graf-after--h4">If we have a function that boils down to a single
                expression, we can use the single-expression function syntax:</p>
                <pre name="7428" id="7428" class="graf--pre graf-after--p">override fun equals(other: Any?) = other is Task &amp;&amp;
                    other.id == id</pre>
                <h4 name="d1f3" id="d1f3" class="graf--h4 graf-after--pre"><a
                    href="https://kotlinlang.org/docs/reference/extensions.html"
                    data-href="https://kotlinlang.org/docs/reference/extensions.html"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">Extensions</a></h4><p name="2268" id="2268"
                                                                                                  class="graf--p graf-after--h4">
                    Extensions allow us to extend any existing class by adding functions and properties without the need
                    to inherit from that class.</p>
                <pre name="9cdc" id="9cdc" class="graf--pre graf-after--p">fun ViewGroup.inflate(<br> @LayoutRes
                    layoutRes: Int, <br> attachToRoot: Boolean = false) =<br> <br> LayoutInflater<br> .from(context)<br>
                        .inflate(layoutRes, this, attachToRoot)</pre>
                <p name="cd07" id="cd07" class="graf--p graf-after--pre">The extension function above adds the&nbsp;
                    <strong class="markup--strong markup--p-strong">.inflate(…)</strong> method to the ViewGroup class,
                    so instead of doing this every time:</p>
                <pre name="f5d9" id="f5d9" class="graf--pre graf-after--p">val view = LayoutInflater<br>
                    .from(parent)<br> .inflate(R.layout.todo_list_item, parent, false)</pre>
                <p name="8266" id="8266" class="graf--p graf-after--pre">now we can just do this:</p>
                <pre name="dc0c" id="dc0c" class="graf--pre graf-after--p">val view = parent.inflate(R.layout.todo_list_item)</pre>
                <p name="89ba" id="89ba" class="graf--p graf-after--pre">or:</p>
                <pre name="4400" id="4400" class="graf--pre graf-after--p">val view = parent.inflate(R.layout.todo_list_item, <br>
                    attachToRoot = true)</pre>
                <p name="9f49" id="9f49" class="graf--p graf-after--pre">I guess you already noticed that Kotlin also
                    supports <a href="https://kotlinlang.org/docs/reference/functions.html#default-arguments"
                                data-href="https://kotlinlang.org/docs/reference/functions.html#default-arguments"
                                class="markup--anchor markup--p-anchor" rel="nofollow">default arguments</a>.</p><h4
                    name="b773" id="b773" class="graf--h4 graf-after--p"><a
                    href="https://kotlinlang.org/docs/reference/lambdas.html#lambda-expressions-and-anonymous-functions"
                    data-href="https://kotlinlang.org/docs/reference/lambdas.html#lambda-expressions-and-anonymous-functions"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">Lambda</a></h4><p name="833b" id="833b"
                                                                                              class="graf--p graf-after--h4">
                    Intentionally left blank.</p><h4 name="9a6f" id="9a6f" class="graf--h4 graf-after--p"><a
                    href="https://kotlinlang.org/docs/reference/null-safety.html"
                    data-href="https://kotlinlang.org/docs/reference/null-safety.html"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">Optionals / Null safety</a></h4><p
                    name="7e3a" id="7e3a" class="graf--p graf-after--h4">Forget about NullPointerExceptions. Kotlin has
                    2 types of variables, nullable and non-nullable. If we declare our variable as non-nullable — the
                    compiler won’t let us assign a null value to it. Only nullable variables can be null.</p>
                <pre name="ac02" id="ac02" class="graf--pre graf-after--p">var nonNullable: String = "This is a title" // Non-nullable variable<br>var
                    nullable: String? = null // Nullable variable</pre>
                <p name="1f7a" id="1f7a" class="graf--p graf-after--pre">In case of the <strong
                    class="markup--strong markup--p-strong">nonNullable </strong>variable, we can safely call methods on
                    it, without any null checks, because it <strong class="markup--strong markup--p-strong">cannot have
                        a null value</strong>.</p><p name="220d" id="220d" class="graf--p graf-after--p">In case of the
                    <strong class="markup--strong markup--p-strong">nullable </strong>variable, we can safely call
                    methods with the help of the <strong class="markup--strong markup--p-strong">safe-trasversal
                        operator (?.)</strong>, and forget about null checks:</p>
                <pre name="1471" id="1471" class="graf--pre graf-after--p">val length = nullable?.length</pre>
                <p name="d5b6" id="d5b6" class="graf--p graf-after--pre">The code above won’t fail, even if the <strong
                    class="markup--strong markup--p-strong">nullable</strong> variable has a null value. In that case,
                    the value of the <strong class="markup--strong markup--p-strong">length </strong>variable will be
                    <strong class="markup--strong markup--p-strong">null</strong>.</p><h4 name="c945" id="c945"
                                                                                          class="graf--h4 graf-after--p">
                    <a href="https://kotlinlang.org/docs/reference/null-safety.html#elvis-operator"
                       data-href="https://kotlinlang.org/docs/reference/null-safety.html#elvis-operator"
                       class="markup--anchor markup--h4-anchor" rel="nofollow">Elvis operator</a></h4><p name="8594"
                                                                                                         id="8594"
                                                                                                         class="graf--p graf-after--h4">
                    The result of a <a href="https://kotlinlang.org/docs/reference/null-safety.html#safe-calls"
                                       data-href="https://kotlinlang.org/docs/reference/null-safety.html#safe-calls"
                                       class="markup--anchor markup--p-anchor" rel="nofollow"><strong
                    class="markup--strong markup--p-strong">safe call (?.)</strong></a><strong
                    class="markup--strong markup--p-strong"> </strong>is always a nullable variable. So in cases where
                    we are calling a method on a null variable — the result will be null.</p><p name="f87b" id="f87b"
                                                                                                class="graf--p graf-after--p">
                    That can be inconvenient sometimes. For example, in the code sample above, we want our <strong
                    class="markup--strong markup--p-strong">length </strong>variable to be a non-null variable because
                    it’s logical for it to have a value of 0 in case of a null string.</p><p name="77b8" id="77b8"
                                                                                             class="graf--p graf-after--p">
                    In cases like that, we can use the <strong class="markup--strong markup--p-strong">elvis operator
                    (&nbsp;?: ).</strong></p>
                <pre name="358b" id="358b" class="graf--pre graf-after--p">val length = nullable?.length ?: 0</pre>
                <p name="d284" id="d284" class="graf--p graf-after--pre">The elvis operator will use the left side value
                    if it’s not null. In case the left side value is null, it will use the right non-nullable value.</p>
                <p name="8023" id="8023" class="graf--p graf-after--p">You can even use it to make your sanity checks
                    more readable.</p>
                <pre name="77c5" id="77c5" class="graf--pre graf-after--p">    ...<br>}</pre>
                <p name="a320" id="a320" class="graf--p graf-after--pre">With help of the elvis operator, the same
                    method in Kotlin will look like this:</p>
                <pre name="12c2" id="12c2" class="graf--pre graf-after--p">public fun myMethod(str: String?) {<br> //
                    Sanity check<br> str ?: return<br> <br> ...<br>}</pre>
                <p name="1874" id="1874" class="graf--p graf-after--pre">The great part of using this is that the
                    compiler will <a href="https://kotlinlang.org/docs/reference/typecasts.html#smart-casts"
                                     data-href="https://kotlinlang.org/docs/reference/typecasts.html#smart-casts"
                                     class="markup--anchor markup--p-anchor" rel="nofollow"><strong
                        class="markup--strong markup--p-strong">smart cast</strong></a> our <strong
                        class="markup--strong markup--p-strong">str</strong> variable to a non-nullable variable after
                    the “<em class="markup--em markup--p-em">str&nbsp;?: return</em>” line.</p><h4 name="a936" id="a936"
                                                                                                   class="graf--h4 graf-after--p">
                    <a href="https://kotlinlang.org/docs/reference/properties.html#properties-and-fields"
                       data-href="https://kotlinlang.org/docs/reference/properties.html#properties-and-fields"
                       class="markup--anchor markup--h4-anchor" rel="nofollow">Optional getters/setters</a></h4><p
                    name="7268" id="7268" class="graf--p graf-after--h4">Unlike in Java, where we are used to define all
                    of our class properties <strong class="markup--strong markup--p-strong"><em
                        class="markup--em markup--p-em">private</em></strong><em class="markup--em markup--p-em"> </em>and
                    write getters and setters, in Kotlin we write getters and setters only if we want to have some <em
                        class="markup--em markup--p-em">custom </em>behaviour.</p><p name="1ba6" id="1ba6"
                                                                                     class="graf--p graf-after--p">The
                    simplest definition looks like this:</p>
                <pre name="5bac" id="5bac" class="graf--pre graf-after--p">class Task {<br> var completed =
                    false<br>} </pre>
                <p name="69c1" id="69c1" class="graf--p graf-after--pre">And we can access the property:</p>
                <pre name="81c6" id="81c6" class="graf--pre graf-after--p">val task = Task()<br>if (task.completed) ...</pre>
                <p name="8f8d" id="8f8d" class="graf--p graf-after--pre">If we wan’t to expose just the getter and allow
                    setting the value only from within the class:</p>
                <pre name="df98" id="df98" class="graf--pre graf-after--p">var completed = false<br> private set</pre>
                <p name="7325" id="7325" class="graf--p graf-after--pre">And if we want to have completely <em
                    class="markup--em markup--p-em">custom</em> behaviour:</p>
                <pre name="2163" id="2163" class="graf--pre graf-after--p">var completedInt = 0<br>var completed:
                    Boolean<br> get() = completedInt == 1<br> set(value) { completedInt = if (value) 1 else 0 }</pre>
                <h4 name="797a" id="797a" class="graf--h4 graf-after--pre"><a
                    href="https://kotlinlang.org/docs/reference/delegated-properties.html#lazy"
                    data-href="https://kotlinlang.org/docs/reference/delegated-properties.html#lazy"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">Lazy properties</a></h4><p name="4eb7"
                                                                                                       id="4eb7"
                                                                                                       class="graf--p graf-after--h4">
                    Kotlin allows us to declare lazy properties — properties that are initialized when we first access
                    them.</p>
                <pre name="6ea2" id="6ea2" class="graf--pre graf-after--p">private val recyclerView by <em
                    class="markup--em markup--pre-em">lazy </em><strong class="markup--strong markup--pre-strong">{
                    <br> </strong> < em class="markup--em markup--pre-em">find</em>&lt;RecyclerView&gt;(R.id.<em class="markup--em markup--pre-em">task_list_new</em>) <br><strong class="markup--strong markup--pre-strong">}</strong></pre>
                <p name="7098" id="7098" class="graf--p graf-after--pre">When we first access the <strong
                    class="markup--strong markup--p-strong">recyclerView</strong> property, the lambda expression is
                    evaluated and the returned value from the lamdba is saved and returned in that and every subsequent
                    call.</p><h4 name="2ba2" id="2ba2" class="graf--h4 graf-after--p"><a
                    href="https://kotlinlang.org/docs/reference/delegated-properties.html#observable"
                    data-href="https://kotlinlang.org/docs/reference/delegated-properties.html#observable"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">Observable properties</a></h4><p name="ea7a"
                                                                                                             id="ea7a"
                                                                                                             class="graf--p graf-after--h4">
                    In Kotlin we can observe properties. The syntax for declaring such properties is the following:</p>
                <pre name="54b5" id="54b5" class="graf--pre graf-after--p">var tasks by Delegates.observable(<em
                    class="markup--em markup--pre-em">mutableListOf</em>&lt;Task&gt;()) <strong
                    class="markup--strong markup--pre-strong">{
                    <br> </strong>prop, old, new <strong class="markup--strong markup--pre-strong">-&gt;</strong><br>        notifyDataSetChanged()<br>        dataChangedListener?.invoke()<br><strong class="markup--strong markup--pre-strong">}</strong></pre>
                <p name="a98c" id="a98c" class="graf--p graf-after--pre graf--last">This means that we are going to be
                    notified every time the value of our property changes (the provided lambda will be called).</p>
            </div>
        </div>
    </section>
    <section name="beb6" class=" section--body section--last">
        <div class="section-divider layoutSingleColumn">
            <hr class="section-divider">
        </div>
        <div class="section-content">
            <div class="section-inner layoutSingleColumn"><h3 name="5d69" id="5d69" class="graf--h3 graf--first"><a
                href="https://github.com/Kotlin/anko" data-href="https://github.com/Kotlin/anko"
                class="markup--anchor markup--h3-anchor" rel="nofollow">Anko extensions</a></h3><p name="7b54" id="7b54"
                                                                                                   class="graf--p graf-after--h3">
                Anko is a great library and has a lot of great extensions. I will list a couple.</p><h4 name="3dff"
                                                                                                        id="3dff"
                                                                                                        class="graf--h4 graf-after--p">
                find&lt;T&gt;(id: Int)</h4><p name="fa22" id="fa22" class="graf--p graf-after--h4">It replaces the
                <strong class="markup--strong markup--p-strong"><em class="markup--em markup--p-em">findViewById(int
                    id)</em></strong> method. This extension function returns the view already cast to the given type T,
                so there is no need to cast it.</p>
                <pre name="be37" id="be37" class="graf--pre graf-after--p">val recyclerView = <em
                    class="markup--em markup--pre-em">find</em>&lt;RecyclerView&gt;(R.id.<em
                    class="markup--em markup--pre-em">task_list_new</em>)</pre>
                <h4 name="1a5c" id="1a5c" class="graf--h4 graf-after--pre"><a
                    href="https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite"
                    data-href="https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite"
                    class="markup--anchor markup--h4-anchor" rel="nofollow">SQLite</a></h4><p name="558d" id="558d"
                                                                                              class="graf--p graf-after--h4">
                    Anko has great support for SQLite databases. For a complete overview, check their <a
                    href="https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite"
                    data-href="https://github.com/Kotlin/anko/blob/master/doc/SQLITE.md#anko-heart-sqlite"
                    class="markup--anchor markup--p-anchor" rel="nofollow">guide</a>. I will just show you one example:
                </p>
                <pre name="b9e4" id="b9e4" class="graf--pre graf-after--p">fun allTasks() = use <strong
                    class="markup--strong markup--pre-strong">{
                    <br> </strong> < em class="markup--em markup--pre-em">select</em>(table)<br>        .orderBy(completed)<br>        .orderBy(priority, SqlOrderDirection.DESC)<br>        .exec <strong class="markup--strong markup--pre-strong">{<br>            </strong><em class="markup--em markup--pre-em">parseList</em>(parser) <br>        <strong class="markup--strong markup--pre-strong">} <br>}</strong></pre>
                <p name="070a" id="070a" class="graf--p graf-after--pre">The <strong
                    class="markup--strong markup--p-strong">use {…} </strong>function opens the database for us, and
                    closes it after the given lambda executes. So we don’t have to worry about closing it anymore and
                    can forget about all those <strong class="markup--strong markup--p-strong"><em
                        class="markup--em markup--p-em">try {…} catch(…) {…} finally {…}</em></strong> blocks. Inside
                    the lambda that we pass to the <strong class="markup--strong markup--p-strong">use</strong>
                    function, <strong class="markup--strong markup--p-strong"><em
                        class="markup--em markup--p-em">this </em></strong>references our database. That is the power of
                    Kotlin’s <strong class="markup--strong markup--p-strong">type-safe builders</strong>. Read more
                    about them <a
                        href="http://blog.jetbrains.com/kotlin/2011/10/dsls-in-kotlin-part-1-whats-in-the-toolbox-builders/"
                        data-href="http://blog.jetbrains.com/kotlin/2011/10/dsls-in-kotlin-part-1-whats-in-the-toolbox-builders/"
                        class="markup--anchor markup--p-anchor" rel="nofollow">here</a> and <a
                        href="https://kotlinlang.org/docs/reference/type-safe-builders.html"
                        data-href="https://kotlinlang.org/docs/reference/type-safe-builders.html"
                        class="markup--anchor markup--p-anchor" rel="nofollow">here</a>.</p><p name="5b16" id="5b16"
                                                                                               class="graf--p graf-after--p">
                    The <strong class="markup--strong markup--p-strong">select(…)…exec {} </strong>call chain, selects
                    data from the database. And the <strong
                    class="markup--strong markup--p-strong">parseList(parser)</strong> call parses the rows of data and
                    returns a list of objects, that our <strong class="markup--strong markup--p-strong">parser</strong>
                    returns. The definition of our <strong class="markup--strong markup--p-strong">parser</strong> is:
                </p>
                <pre name="3c10" id="3c10" class="graf--pre graf-after--p graf--last">val parser = <em
                    class="markup--em markup--pre-em">rowParser </em><strong class="markup--strong markup--pre-strong">{
                    <br> </strong>id: Int, name: String, priority: Int, completed: Int <strong class="markup--strong markup--pre-strong">-&gt; <br>    </strong>Task(id, name, priority, completed) <br><strong class="markup--strong markup--pre-strong">}</strong></pre>
            </div>
        </div>
    </section>
</main>`;