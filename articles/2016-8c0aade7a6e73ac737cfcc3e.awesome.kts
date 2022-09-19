
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """
![validation_for_your_own_programming_language](http://i0.wp.com/tomassetti.me/wp-content/uploads/2016/08/validation_for_your_own_programming_language.jpg?resize=730%2C489)

So you have parsed your code and built a clean AST for it. Now it is time to check if what the user has expressed make sense at all. We should perform validation, identifying semantical errors, to communicate together with lexical and syntactical errors (provided by the parser).

## Series on building your own language

Previous posts:

1.  _[Building a lexer](http://tomassetti.me/getting-started-with-antlr-building-a-simple-expression-language/)_
2.  [_Building a parser_](http://tomassetti.me/building-and-testing-a-parser-with-antlr-and-kotlin/)
3.  [Creating an editor with syntax highlighting](http://tomassetti.me/how-to-create-an-editor-with-syntax-highlighting-dsl/)
4.  [Build an editor with autocompletion](http://tomassetti.me/autocompletion-editor-antlr/)
5.  [Mapping the parse tree to the abstract syntax tree](http://tomassetti.me/parse-tree-abstract-syntax-tree/)
6.  [Model to model transformations](http://tomassetti.me/building-compiler-language-model-model-transformations/)

Code is available [on GitHub](https://github.com/ftomassetti/LangSandbox) under the tag **07_validation**

## Implement semantic checks

In the previous post we have seen how to implement the function _process_ to execute an action on all the nodes of our AST. A typical case is that we want to execute certain operation only on certain nodes. We want still to use process to navigate the tree. We can do that by creating this function named _specificProcess_.

```kotlin
fun <T: Node> Node.specificProcess(klass: Class<T>, operation: (T) -> Unit) {
    process { if (klass.isInstance(it)) { operation(it as T) } }
}
```

Let’s see how to use _specificProcess _to:

*   find all the _VariableDeclarations_ and check they are not re-declaring a variable already declared
*   find all the _VarReferences_ and verify they are not referring to a variable that has been not declared or has been declared after the _VarReference_
*   perform the same check done on _VarReferences_ also for _Assignments_

```kotlin
data class Error(val message: String, val position: Point)

fun SandyFile.validate() : List<Error> {
    val errors = LinkedList<Error>()

    // check a variable is not duplicated
    val varsByName = HashMap<String, VarDeclaration>()
    this.specificProcess(VarDeclaration::class.java) {
        if (varsByName.containsKey(it.varName)) {
            errors.add(Error("A variable named '${"$"}{it.varName}' has been already declared at ${"$"}{varsByName[it.varName]!!.position!!.start}",
                    it.position!!.start))
        } else {
            varsByName[it.varName] = it
        }
    }

    // check a variable is not referred before being declared
    this.specificProcess(VarReference::class.java) {
        if (!varsByName.containsKey(it.varName)) {
            errors.add(Error("There is no variable named '${"$"}{it.varName}'", it.position!!.start))
        } else if (it.isBefore(varsByName[it.varName]!!)) {
            errors.add(Error("You cannot refer to variable '${"$"}{it.varName}' before its declaration", it.position!!.start))
        }
    }
    this.specificProcess(Assignment::class.java) {
        if (!varsByName.containsKey(it.varName)) {
            errors.add(Error("There is no variable named '${"$"}{it.varName}'", it.position!!.start))
        } else if (it.isBefore(varsByName[it.varName]!!)) {
            errors.add(Error("You cannot refer to variable '${"$"}{it.varName}' before its declaration", it.position!!.start))
        }
    }

    return errors
}
```

Ok, so invoking _validate_ on the root of the AST will return all possible semantic errors.

## Getting all errors: lexical, syntactic, and semantic

We first need to invoke the ANTLR parser and get:

*   the parse tree
*   the list of lexical and syntactic error

```kotlin
data class AntlrParsingResult(val root : SandyFileContext?, val errors: List<Error>) {
    fun isCorrect() = errors.isEmpty() && root != null
}

fun String.toStream(charset: Charset = Charsets.UTF_8) = ByteArrayInputStream(toByteArray(charset))

object SandyAntlrParserFacade {

    fun parse(code: String) : AntlrParsingResult = parse(code.toStream())

    fun parse(file: File) : AntlrParsingResult = parse(FileInputStream(file))

    fun parse(inputStream: InputStream) : AntlrParsingResult {
        val lexicalAndSyntaticErrors = LinkedList<Error>()
        val errorListener = object : ANTLRErrorListener {
            override fun reportAmbiguity(p0: Parser?, p1: DFA?, p2: Int, p3: Int, p4: Boolean, p5: BitSet?, p6: ATNConfigSet?) {
                // Ignored for now
            }

            override fun reportAttemptingFullContext(p0: Parser?, p1: DFA?, p2: Int, p3: Int, p4: BitSet?, p5: ATNConfigSet?) {
                // Ignored for now
            }

            override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInline: Int, msg: String, ex: RecognitionException?) {
                lexicalAndSyntaticErrors.add(Error(msg, Point(line, charPositionInline)))
            }

            override fun reportContextSensitivity(p0: Parser?, p1: DFA?, p2: Int, p3: Int, p4: Int, p5: ATNConfigSet?) {
                // Ignored for now
            }
        }

        val lexer = SandyLexer(ANTLRInputStream(inputStream))
        lexer.removeErrorListeners()
        lexer.addErrorListener(errorListener)
        val parser = SandyParser(CommonTokenStream(lexer))
        parser.removeErrorListeners()
        parser.addErrorListener(errorListener)
        val antlrRoot = parser.sandyFile()
        return AntlrParsingResult(antlrRoot, lexicalAndSyntaticErrors)
    }

}
```

Then we map the parse tree to the AST and perform the semantic validation. Finally we return the AST and all the errors combined.

```kotlin
data class ParsingResult(val root : SandyFile?, val errors: List<Error>) {
    fun isCorrect() = errors.isEmpty() && root != null
}

object SandyParserFacade {

    fun parse(code: String) : ParsingResult = parse(code.toStream())

    fun parse(file: File) : ParsingResult = parse(FileInputStream(file))

    fun parse(inputStream: InputStream) : ParsingResult {
        val antlrParsingResult = SandyAntlrParserFacade.parse(inputStream)
        val lexicalAnsSyntaticErrors = antlrParsingResult.errors
        val antlrRoot = antlrParsingResult.root
        val astRoot = antlrRoot?.toAst(considerPosition = true)
        val semanticErrors = astRoot?.validate() ?: emptyList()
        return ParsingResult(astRoot, lexicalAnsSyntaticErrors + semanticErrors)
    }

}
```

In the rest of the system we will simply call the _SandyParserFacade_ without the need to invoke the ANTLR parser directly.

## Test validation

_Will it fly? _Let’s verify that.


```kotlin
class ValidationTest {

    @test fun duplicateVar() {
        val errors = SandyParserFacade.parse(${"\"\"\""}var a = 1
                                               |var a =2${"\"\"\""}.trimMargin("|")).errors
        assertEquals(listOf(Error("A variable named 'a' has been already declared at Line 1, Column 0", Point(2,0))), errors)
    }

    @test fun unexistingVarReference() {
        val errors = SandyParserFacade.parse("var a = b + 2").errors
        assertEquals(listOf(Error("There is no variable named 'b'", Point(1,8))), errors)
    }

    @test fun varReferenceBeforeDeclaration() {
        val errors = SandyParserFacade.parse(${"\"\"\""}var a = b + 2
                                               |var b = 2${"\"\"\""}.trimMargin("|")).errors
        assertEquals(listOf(Error("You cannot refer to variable 'b' before its declaration", Point(1,8))), errors)
    }

    @test fun unexistingVarAssignment() {
        val errors = SandyParserFacade.parse("a = 3").errors
        assertEquals(listOf(Error("There is no variable named 'a'", Point(1,0))), errors)
    }

    @test fun varAssignmentBeforeDeclaration() {
        val errors = SandyParserFacade.parse(${"\"\"\""}a = 1
                                               |var a =2${"\"\"\""}.trimMargin("|")).errors
        assertEquals(listOf(Error("You cannot refer to variable 'a' before its declaration", Point(1,0))), errors)

}
```

## Conclusions

This is all nice and well: with a simple call we can get a list of all errors we have. For each of them we have a description and the position. This is enough for our compiler but we would now need to show these errors in the editor. We will do that in out of the future posts.

"""

Article(
  title = "Building a compiler for your own language: validation",
  url = "http://tomassetti.me/building-compiler-language-validation/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Federico Tomassetti",
  date = LocalDate.of(2016, 9, 6),
  body = body
)
