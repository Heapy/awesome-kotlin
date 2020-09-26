
import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.LinkType.*
import link.kotlin.scripts.dsl.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
In the [previous installment](http://beust.com/weblog/2016/05/27/neural-network-in-kotlin/), I introduced a mystery class `NeuralNetwork` which is capable of calculating different results depending on the data that you train it with. In this article, we’ll take a closer look at this neural network and crunch a few numbers.

## Neural networks overview

A neural network is a graph of neurons made of successive layers. The graph is typically split in three parts: the leftmost column of neurons is called the “input layer”, the rightmost columns of neurons is the “output layer” and all the neurons in-between are the “hidden” layer. This hidden layer is the most important part of your graph since it’s responsible for making the calculations. There can be any numbers of hidden layers and any number of neurons in each of them (note that the Kotlin class I wrote for this series of articles only uses one hidden layer).

Each edge that connects two neurons has a weight which is used to calculate the output of this neuron. The calculation is a simple addition of each input value multiplied by its weight. Let’s take a look at a quick example:

![](https://docs.google.com/drawings/d/1lnkGCoJ5DsJcXlqXnjmE0oGM6l1wHrf9pRuN-8ZU7Xc/pub?w=480&h=360)  

This network has two input values, one hidden layer of size two and one output. Our first calculation is therefore:

```kotlin
w11-output = 2 * 0.1 + (-3) * (-0.2) = 0.8
w12-output = 2 * (-0.4) + (-3) * 0.3 = -1.7
```

We’re not quite done: the actual outputs of neurons (also called “activations”) are typically passed to a normalization function first. To get a general intuition for this function, you can think of it as a way to constrain the outputs within the [-1, 1] range, which prevents the values flowing through the network from overflowing or underflowing. Also, it’s useful in practice for this function to have additional properties connected to its derivative but I’ll skip over this part for now. This function is called the “activation function” and the implementation I used in the `NeuralNetwork` class is the [hyperbolic tangent, `tanh`](http://mathworld.wolfram.com/HyperbolicTangent.html).

In order to remain general, I’ll just refer to the activation function as `f()`. We therefore refine our first calculations as follows:

```kotlin
w11-output = f(2 * 0.1 + (-3) * (-0.2))
w12-output = f(2 * (-0.4) + (-3) * 0.3)
```

There are a few additional details to this calculation in actual neural networks but I’ll skip those for now.

Now that we have all our activations for the hidden layer, we are ready to move to the next layer, which happens to be the ouput layer, so we’re almost done:

```kotlin
output = f(0.1 * w11-output - 0.2 * w12-output
       = 0.42
```

As you can see, calculating the output of a neural network is fairly straightforward and fast, much faster than actually training that network. Once you have created your networks and you are satisfied with its results, you can just pass around the characteristics of that network (weights, sizes, ...) and any device (even phones) can then use that network.

## Revisiting the xor network

Let’s go back to the `xor` network we created in the first episode. I created this network as follows:

```kotlin
NeuralNetwork(inputSize = 2, hiddenSize = 2, outputSize = 1)
```

We only need two inputs (the two bits) and one output (the result of `a xor b`). These two values are fixed. What is not fixed is the size of the hidden layer, and I decided to pick 2 here, for no particular reason. It’s interesting to tweak these values and see whether your neural network performs better of worse based on these values and there is actually a great deal of both intuition and arbitrary choices that go into these decisions. These values that you use to configure your network before you run it are called “hyper parameters”, in contrast to the other values which get updated while your network runs (e.g. the weights).

Let’s now take a look at the weights that our `xor` network came up with, which you can display by running the Kotlin application with <tt>--log 2</tt>:

```plain
Input weights:
-1.21 -3.36
-1.20 -3.34
1.12 1.67
 
Output weights:
3.31
-2.85 
```

Let’s put these values on the visual representation of our graph to get a better idea:

![](https://docs.google.com/drawings/d/1vzNDxpKkIP0h6pp8KglUn55a-pLE5PIAaO204ZNqYR0/pub?w=960&h=720)  

You will notice that the network above contains a neuron called “bias” that I haven’t introduced yet. And I’m not going to just yet besides saying that this bias helps the network avoid edge cases and learn more rapidly. For now, just accept it as an additional neuron whose output is not influenced by the previous layers.

Let’s run the graph manually on the input (1,0), which should produce 1:

```kotlin
hidden1-1 = 1 * -1.21
hidden1-2 = 0 * -1.20
bias1     = 1 * 1.12
 
output1 = tanh(-1.21 + 1.12) = -0.09
 
hidden2-1 = 1 * -3.36
hidden2-2 = 0 * -3.34
bias2     = 1 * 1.67
 
output2 = tanh(-3.36 + 1.6) = -0.94
 
// Now that we have the outputs of the hidden layer, we can caculate
// our final result by combining them with the output weights:
 
finalOutput = tanh(output1 * 3.31 + output2 * (-2.85))
            = 0.98
```

We have just verified that if we input `(0,1)` into the network, we’ll receive `0.98` in output. Feel free to calculate the other three inputs yourself or maybe just run the `NeuralNetwork` class with a log level of 2, which will show you all these calculations.

## Revisiting the parity network

So the calculations hold up but it’s still a bit hard to understand where these weights come from and why they interact in the way they do. Elucidating this will be the topic of the next installment but before I wrap up this article, I’d like to take a quick look at the parity network because its content might look a bit more intuitive to the human eye, while the `xor` network detailed above still seems mysterious.

If you train the parity network and you ask the `NeuralNetwotk` class to dump its output, here are the weights that you’ll get:

```plain
Input weights:
0.36 -0.36
0.10 -0.09
0.30 -0.30
-2.23 -1.04
0.57 -0.58
 
Output weights:
-1.65
-1.64 
``` 

If you pay attention, you will notice an interesting detail about these numbers: the weights of the first three neurons of our hidden layer cancel each other out while the two inputs of the fourth neuron reinforce each other. It’s pretty clear that the network has learned that when you are testing the parity of a number in binary format, the only bit that really matters is the least significant one (the last one). All the others can simply be ignored, so the network has learned from the training data that the best way to get close to the desired output is to only pay attention to that last bit and cancel all the other ones out so they don’t take part in the final result.

## Wrapping up

This last result is quite remarkable if you think about it, because it really looks like the network learned how to test parity at the formal level (“The output of the network should be the value of the least significant bit, ignore all the others”), inferring that result just from the numeric data we trained it with. Understanding how the network learned how to modify itself to reach this level will be the topic of the next installment of the series.

"""

Article(
  title = "Neural Networks in Kotlin (part 2)",
  url = "http://beust.com/weblog/2016/05/30/neural-networks-in-kotlin-part-2/",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Cédric Beust",
  date = LocalDate.of(2016, 5, 30),
  body = body
)
