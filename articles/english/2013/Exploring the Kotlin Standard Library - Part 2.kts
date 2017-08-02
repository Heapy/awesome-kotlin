
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
In [Part 1](http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-1.html) of this series, I went through the default Kotlin namespace. In Part 2 I'll be going over [kotlin.io](http://jetbrains.github.com/kotlin/versions/snapshot/apidocs/kotlin/io/package-summary.html).

Most of the public function in kotlin.io are overloaded versions of print and println, which all delegate to System.out.

kotlin.io also introduces readLine for reading lines from stdin and the use method. The use method is a great example of a general purpose extension method. The signature illustrates how to create an extension method that will apply to every class that implements Closeable:

```kotlin
public inline fun <T: Closeable, R> T.use(block: (T)-> R) : R
```

The iterate method from the default namespace works very well with the readLine method. A function to count the number of lines from stdin would look as follows:

```kotlin
fun main(args: Array<String>) = iterate{ readLine() }.count({ true })
```

The following example uses the 'use' method to read the text from a reader while managing its lifecycle:

```kotlin
// gets the contents fo the reader and then closes it
var contents = reader.use { it.readText() }
```

The use method will return whatever the closure returns (in this case, the contents of the reader as a String).

kotlin.io extends java.io.File, java.io.InputStream, java.io.OutputStream, java.io.Reader, java.io.Writer, java.io.BufferedReader and java.net.URL.

For reading files or streams, the API docs distinguish between those methods that can be used on huge amounts of data and those that can't. Those that can't are the ones that wait for the entire file to be read before returning a result e.g. readLines, whereas the those that use a closure or iterator pull data lazily. The following calls should not be used with huge amounts of data

* readBytes
* readLines
* readText

The following can be used for huge amounts of data:

* copyTo
* forEachBlock
* forEachLine

For InputStreams, Readers and Writers, the caller is typically responsible for closing the various streams, the exception being the useLines method.

The Kotlin stdlib enhances with java.io.File with a number of useful methods: copyTo, forEachBlock, forEachLine, isDescendant, listFiles, readBytes, readLines, readText, reader, recurse, relativePath, writeBytes, writeText

The following is an example of copyTo being used:

```kotlin
File("/tmp/from.txt").copyTo(File("/tmp/to.txt"))
```

Note the following:

* copyTo only works on files (not directories)
* copyTo will create the target directory
* copyTo will overwrite the target file
* if the source file can't be found a FileNotFoundException will be thrown
* copyTo will block until the file is written

As an example, the forEachBlock method could be used to provide a version of copyTo that reports progress as the target file is written as follows:

```kotlin
/**
 * Copies a file and calls the closure with the current number of bytes read as each block is read
 * to indicate progress
 */
public fun File.copyToWithProgress(file: File, closure : (Long) -> Unit) {
    file.directory.mkdirs()
    val output = FileOutputStream(file)
    output.use{
        var length = 0.toLong()
        this.forEachBlock { bytes, size ->
            length += size
            output.write(bytes, 0, size)
            closure(length)
        }
    }
}
```

Here are a few more examples of what can be done with kotlin.io

```kotlin
// forEachLine
//
// reads all the lines from a file and prints them out
File("/tmp/from.txt").forEachLine { println(it) }

// isDescendent
//
// Confirms that from.txt is a descendant of /tmp
val isDescendent = File("/tmp/from.txt").isDescendant(File("/tmp"))

// listFiles
//
// Lists all the subdirectories in /tmp
val files = File("/tmp").listFiles{ it.isDirectory() }

// readBytes
//
// reads all the bytes from /tmp/from.bin
val bytes = File("/tmp/from.bin").readBytes()

// readLines
//
// reads all the lines from /tmp/from.txt into a list
// and then runs through each line in the list and prints it out
val lines = File("/tmp/from.txt").readLines()
lines.forEach { println(it) }

// readText
//
// reads all the text from /tmp/from.txt into a string
// and then checks if it contains a multiline string
val text = File("/tmp/from.txt").readText()
val found = text.contains("hello\nworld\n!")

// reader
//
// creates a reader and calls useLines on it
val fileReader = File("/tmp/from.txt").reader().useLines {
    // same as calling forEachLine
}

// recurse
//
// recurse through all the files in /tmp and print out their names
File("/tmp").recurse { println(it.name) }

// relativePath
//
// returns nested/directory/file.txt
File("/tmp").relativePath(File("/tmp/nested/directory/file.txt"))

// writeBytes
//
// write Hello World as bytes to /tmp/helloworld.bin
File("/tmp/helloworld.bin").writeBytes("Hello World!".getBytes())

// writeText
//
// write Hello World as bytes to /tmp/helloworld.txt
File("/tmp/helloworld.txt").writeText("Hello World!")
```

"""

Article(
  title = "Exploring the Kotlin Standard Library - Part 2",
  url = "http://jamie.mccrindle.org/2013/01/exploring-kotlin-standard-library-part-2.html",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Jamie McCrindle",
  date = LocalDate.of(2013, 1, 25),
  body = body
)
