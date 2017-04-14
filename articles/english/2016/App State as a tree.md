---
title: 'App State as a tree'
url: https://medium.com/lewisrhine/app-state-as-a-tree-a8eb6b26dd1b#.f9j7p0x89
categories:
    - Kotlin
author: Lewis Rhine
date: Nov 21, 2016 02:08
---
We tend to think of app state as just the current values at a given time. But what if we thought about state at a higher level? Wouldn’t it be useful to have more information, not just of the values of our properties, but the state of an application as a whole.

Image your whole app broken into trees. Let’s say for example, a restaurant review app, why a restaurant review app? I don’t know! I’m just trying to think of anything that’s not a To Do app, okay. Let’s draw out what the state tree of this kind of app would be.

I am using a site named [Sketchboard.io](https://sketchboard.io/). I highly recommend them.

First, what are the components of the app we are going to need?

List of nearby restaurants: A list of all restaurants near the user.

List of reviewed restaurants: All the restaurants the user has already reviewed, if any.

Compose a review: Where the user will go to write or edit a review.

Here it is sketched out:

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*k5oltMaFoEOkhR80Cm2Q9Q.png)

Now that we have our core components we can start to build out the trees for each one.

For this example we are only going to worry about the ‘Restaurants Nearby’ component. We know that to get the data we are going to need to get the user’s GPS location. Then, we need to send a request to some kind of rest API, like Yelp or Foursquare, using that location. So it’s pretty clear the first state this tree would have would be a **Loading State**.

Once we receive our response from the API we can move down the tree to a **Done State** and come to our first branch. At this point there are going to be three possible outcomes.

**Ready State** we have our list of restaurants.  
**Empty State** there were no nearby restaurants found.  
**Error State** we get some kind of error, either from the GPS or from the server.

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*uua9-C2PEPeT_9XJqxWwYQ.png)

There are a few things to notice here. We moved the ‘List of Restaurants’ down to our **Ready State**. So now, the only way for the app to get access to that list is once we’ve made it down to that branch. Also notice that in our **Done State** we have a function called **reloadData**. Now every state down the tree has the ability move back up to the loading state.

Looking at this, it’s easy to see how helpful this approach can be. When we are getting data from the tree in our UI layer we know exactly what state the app is in. We know that if the tree is in a **Loading State** all we can do is tell the user and wait. Once we are updated with the new state of the tree we know “Ok, is the list ready for us? Cool get the list and display it.” If it’s empty or there was an error, we don’t even have access to the list.

It also gives an easy to glance at map of your app. Need to add a feature? You can start by looking at where in your app branches need to be changed or created, and how it will affect the rest of the app.

“Ok. So yeah, drawing stuff is all well and good. But how can we do it in code?” Turns out Kotlin has a feature perfect for this.

Sealed classes are best described as enums on steroids. For this context you can think of them as a group of type safe abstract classes. This is what the above tree would look like as a sealed class

```kotlin
sealed class RestaurantNearby() {
   sealed class Loading() : RestaurantNearby() {
        //Code for getting Location, and API call
        sealed class Done() : Loading() {
          fun reloadData() {}
          class Ready(val restaurants: List<Restaurant>) : Done()
          class Empty() : Done()
          class Error(val message: String, val throwable: Throwable?) : Done()
        }
    }
}
```

This coupled with a Kotlins ‘when’ interacting with this sealed class is super simple.

```kotlin
when (restaurantNearby) {
  is RestaurantNearby.Loading -> // Tell user the data is loading
  
  is RestaurantNearby.Loading.Done -> {
    restaurantNearby.reloadData() // Attach this function to some user feedback.
  
    when (restaurantNearby) {
      is RestaurantNearby.Loading.Done.Ready -> restaurantNearby.restaurants // Do something with this list.
      
      is RestaurantNearby.Loading.Done.Empty -> // Tell the user there was no data found.
      
      is RestaurantNearby.Loading.Done.Error -> {
        restaurantNearby.message // Show this message to the user.
        restaurantNearby.throwable // Catch this and act on it as needed.
      }
    }
  }
}
```
    
Now there is a big part of this idea that is missing. How do you pass these classes around? How should you set it? Should it be immutable? The idea of building your state’s into trees is part of a library I am working on called [Akorn](https://github.com/LewisRhine/Akorn). It is a Flux style architecture, open source library, that uses RxJava to reactivity pass your state trees. Akron is still a little baby and I would love for people check it out.
