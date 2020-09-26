
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
![](http://beust.com/pics/neural-network.png)

It’s hard not to hear about machine learning and neural networks these days since the practice is being applied to an ever increasingly wide variety of problems. Neural networks can be intimidating and look downright magical to the untrained (ah!) eye, so I’m going to attempt to dispel these fears by demonstrating how these mysterious networks operate. And since there are already so many tutorials on the subject, I’m going to take a different approach and go from top to bottom.

## Goal

In this first series of articles, I will start by running a very simple network on two simple problems, show you that they work and then walk through the network to explain what happened. Then I’ll backtrack to deconstruct the logic behind the network and why it works.

The neural network I’ll be using in this article is a simple one I wrote. No TensorFlow, no Torch, no Theano. Just some basic Kotlin code. The original version was about 230 lines but it’s a bit bigger now that I broke it up in separate classes and added comments. The [whole project can be found on github under the temporary “nnk” name](http://github.com/cbeust/nnk). In particular, [here is the source of the neural network we’ll be using](https://github.com/cbeust/nnk/blob/master/src/main/kotlin/com/beust/nnk/NeuralNetwork.kt).

I will be glossing over a lot of technical terms in this introduction in order to focus on the numeric aspect but I’m hoping to be able to get into more details as we slowly peel the layers. For now, we’ll just look at the network as a black box that get fed input values and which outputs values.

The main characteristic of a neural network is that it starts completely empty but it can be taught to solve problems. We do this by feeding it values and telling it what the expected output is. We iterate over this approach many times, changing these inputs/expected parameters and as we do that, the network updates its knowledge to come up with answers that are as close to the expected answers as possible. This phase is called “training” the network. Once we think the network is trained enough, we can then feed it new values that it hasn’t seen yet and compare its answer to the one we’re expecting.

## The problems

Let’s start with a very simple example: `xor`.

This is a trivial and fundamental binary arithmetic operation which returns 1 if the two inputs are different and 0 if they are equal. We will train the network by feeding it all four possible combinations and telling it what the expected outcome is. With the Kotlin implementation of the Neural Network, the code looks like this:

```kotlin
with(NeuralNetwork(inputSize = 2, hiddenSize = 2, outputSize = 1)) {
    val trainingValues = listOf(
        NetworkData.create(listOf(0, 0), listOf(0)),
        NetworkData.create(listOf(0, 1), listOf(1)),
        NetworkData.create(listOf(1, 0), listOf(1)),
        NetworkData.create(listOf(1, 1), listOf(0)))
 
    train(trainingValues)
    test(trainingValues)
}
```

Let’s ignore the parameters given to `NeuralNetwork` for now and focus on the rest. Each line of `NetworkData` contains the inputs (each combination of 0 and 1: (0,0), (0,1), (1,0), (1,1)) and the expected output. In this example, the output is just a single value (the result of the operation) so it’s a list of one value, but networks can return an arbitrary number of outputs.

The next step is to test the network. Since there are only four different inputs here and we used them all for training, let’s just use that same list of inputs but this time, we’ll display the ouput produced by the network instead of the expected one. The result of this run is as follows:

Running neural network xor()

```plain
[0.0, 0.0] -> [0.013128957]
[0.0, 1.0] -> [0.9824073]
[1.0, 0.0] -> [0.9822749]
[1.0, 1.0] -> [-2.1314621E-4]
```

As you can see, these values are pretty decent for such a simple network and such a small training data set and you might rightfully wonder: is this just luck? Or did the network cheat and memorize the values we fed it while we were training it?

One way to find out is to see if we can train our network to learn something else, so let’s do that.

## A harder problem

This time, we are going to teach our network to determine whether a number is odd or even. Because the implementation of the graph is pretty naïve and this is just an example, we are going to train our network with binary numbers. Also, we are going to learn a first important lesson in neural networks which is to choose your training and testing data wisely.

You probably noticed in the example above that I used the same data to train and test the network. This is not a good practice but it was necessary for `xor` since there are so few cases. For better results, you usually want to train your network on a certain population of the data and then test it on data that your network hasn’t seen yet. This will guarantee that you are not “overfitting” your network and also that it is able to generalize what you taught it to input values that it hasn’t seen yet. Overfitting means that your network does great on the data you trained it with but poorly on new data. When this happens, you usually want to tweak your network so that it will possibly perform less well on the training data but it will return better results for new data.

For our parity test network, let’s settle on four bits (integers 0 – 15) and we’ll train our network on about ten numbers and test it on the remaining six:

```kotlin
with(NeuralNetwork(inputSize = 4, hiddenSize = 2, outputSize = 1)) {
    val trainingValues = listOf(
        NetworkData.create(listOf(0, 0, 0, 0), listOf(0)),
        NetworkData.create(listOf(0, 0, 0, 1), listOf(1)),
        NetworkData.create(listOf(0, 0, 1, 0), listOf(0)),
        NetworkData.create(listOf(0, 1, 1, 0), listOf(0)),
        NetworkData.create(listOf(0, 1, 1, 1), listOf(1)),
        NetworkData.create(listOf(1, 0, 1, 0), listOf(0)),
        NetworkData.create(listOf(1, 0, 1, 1), listOf(1)),
        NetworkData.create(listOf(1, 1, 0, 0), listOf(0)),
        NetworkData.create(listOf(1, 1, 0, 1), listOf(1)),
        NetworkData.create(listOf(1, 1, 1, 0), listOf(0)),
        NetworkData.create(listOf(1, 1, 1, 1), listOf(1))
    )
    train(trainingValues)
 
    val testValues = listOf(
        NetworkData.create(listOf(0, 0, 1, 1), listOf(1)),
        NetworkData.create(listOf(0, 1, 0, 0), listOf(0)),
        NetworkData.create(listOf(0, 1, 0, 1), listOf(1)),
        NetworkData.create(listOf(1, 0, 0, 0), listOf(0)),
        NetworkData.create(listOf(1, 0, 0, 1), listOf(1))
    )
    test(testValues)
}
```

And here is the output of the test:

```plain
Running neural network isOdd()
 
[0.0, 0.0, 1.0, 1.0] -> [0.9948013]
[0.0, 1.0, 0.0, 0.0] -> [0.0019584869]
[0.0, 1.0, 0.0, 1.0] -> [0.9950419]
[1.0, 0.0, 0.0, 0.0] -> [0.0053276513]
[1.0, 0.0, 0.0, 1.0] -> [0.9947305]
```


Notice that the network is now outputting correct results for numbers that it hadn’t seen before, just because of the way it adapted itself to the training data it was initially fed. This gives us good confidence that the network has configured itself to classify numbers from any input values and not just the one it was trained for.

## Wrapping up

I hope that this brief overview will have whetted your appetite or at least piqued your curiosity. In the [next installment](http://beust.com/weblog/2016/05/30/neural-networks-in-kotlin-part-2/), I’ll dive a bit deeper into the `NeuralNetwork` class, explain the constructor parameters and we’ll walk through the inner working of the neural network that we created to demonstrate how it works.

"""

Article(
  title = "Neural Network in Kotlin",
  url = "http://beust.com/weblog/2016/05/27/neural-network-in-kotlin/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Cédric Beust",
  date = LocalDate.of(2016, 5, 27),
  body = body
)
