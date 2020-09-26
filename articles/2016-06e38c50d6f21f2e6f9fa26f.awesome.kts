
import link.kotlin.scripts.Article
import link.kotlin.scripts.LinkType.*
import link.kotlin.scripts.model.LanguageCodes.*
import java.time.LocalDate

// language=Markdown
val body = """
### Kotlin : Retrofit + RxAndroid + Realm

In this article I thought I should cover some of the libraries (that I use regularly), like Retrofit and Realm in the context of Kotlin and Rx.

If you’re new to Retrofit... Then I suggest you visit this [article](https://medium.com/@ahmedrizwan/rxandroid-and-retrofit-2-0-66dc52725fff#.51syz2rr5) — and things will (hopefully) be crystal clear! And if you’re new to Rx & Kotlin — go [here](https://medium.com/@ahmedrizwan/rxandroid-and-kotlin-part-1-f0382dc26ed8#.i0smhvwlm)!

#### So let’s begin : Kotlin + Rx + Retrofit + Realm

If you’re anything like me, then you absolutely hate writing unnecessary code. *cough Java 6 cough*. And in my Android development experience, I’ve come across many situations where I was like — “But... Why?!”

In the context of Languages, I think Kotlin is definitely a life-saver. And libraries like Retrofit, Realm and RxAndroid also significantly reduce the amount of unnecessary code.

Horror story: I once came across a project, which had around **12** model classes aka POJOs. Then there were **12** Database (ORM) Table classes and there were also **12** Model-to-Database-Mapping classes. If you’re good at counting, you’ll notice... Those are **36** classes right there! And not only that, a change to the a single class meant, changing all 3 classes.

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*j31fbds0p0D_WwDim60TEQ.jpeg)

_Yeah, that was me..._

I suppose, even if we don’t use any ORMs, we still would be writing code for mapping the Models to the Database! And that’s no good!

So, what was the solution... Well... For me **Realm** + **Retrofit** was it... I added in **RxAndroid** to the mix later on, because Rx makes almost everything better and more delicious! And of course **Kotlin**, because...

> Kotlin is love... Kotlin is life...

With the combination of these, those 36 classes would be represented in just 12 (as they should be). And not just that, code will be more concise and expressive because of Kotlin!

### Simple Example: Getting data from Github API

Imagine an app which gets data from an API, stores/persists that data to the database and also displays it. A pretty common scenario, right?

This is what I’d be making... An extremely simple example... Which should hopefully cover the basics!

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*CLjgK7p_Cdro4oh0gu4_Qg.png)

_Nothing fancy — Just plain old me and my github info!_

Let me walk you through on how I would go about creating this app...

#### Creating the Project and Enabling Kotlin

The first thing we do is enable Kotlin in the project — Just make sure the Kotlin Plugin is installed in your Android Studio.

I created an Empty project with a single MainActivity — Went straight into the build.gradle file and... Triggered the action...

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*pI9K9b0ww9jAduIrjZ10eg.png)

_Trigger Action and search for Configure Kotlin_

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*jMre8FWu_02iHJRnNLaj0w.png)

_Select the modules and click OK_

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*w-WHqk_EDQm8uu09fTyTXg.png)

_After clicking OK — this gets added to the build file — *Sync Changes*_

Cool! Now we can write code in Kotlin!

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*gUTF_1dfhJLGJbQZxiJ-nA.png)

_MainActivity.java at the moment — Pretty standard stuff_

We can (magically) convert the existing java code to Kotlin... By doing the following...

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*8p7TdBtxViZlM50nBGBdww.png)

_Action: Convert Java File to Kotlin File_

Now the MainActivity becomes...

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*jtzRyBq1Parb6TYzdsgwVw.png)

_Holy mother of Kotlin!_

#### Adding Rx — Retrofit — Realm Dependencies

Time to add some dependencies!

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*CAdkhnWaF0MeqwYHpqKo1Q.png)

_I also use Databinding! Because cool people use Databinding!_

Now you might be thinking what the hell is **kapt**? Well it’s an annotation processor built for Kotlin.

![](https://d262ilb51hltx0.cloudfront.net/max/600/1*6MHRyDAF7oqrxhlf2LHwcg.png)

_sudo add this to build.gradle_

In our project we’ll also have to enable **generateStubs** as well for kapt, in order for it to generate code.

So... Now we have everything we need! Time to start coding!

#### The Model

The endpoint we’re dealing with...

`https://api.github.com/users/ahmedrizwan`

And the response...

```json
{
  "login": "ahmedrizwan",
  "id": 4357275,
  "avatar_url": "https://avatars.githubusercontent.com/u/4357275?v=3",
  "gravatar_id": "",
  "url": "https://api.github.com/users/ahmedrizwan",
  "html_url": "https://github.com/ahmedrizwan",
  "followers_url": "https://api.github.com/users/ahmedrizwan/followers",
  "following_url": "https://api.github.com/users/ahmedrizwan/following{/other_user}",
  "gists_url": "https://api.github.com/users/ahmedrizwan/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/ahmedrizwan/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/ahmedrizwan/subscriptions",
  "organizations_url": "https://api.github.com/users/ahmedrizwan/orgs",
  "repos_url": "https://api.github.com/users/ahmedrizwan/repos",
  "events_url": "https://api.github.com/users/ahmedrizwan/events{/privacy}",
  "received_events_url": "https://api.github.com/users/ahmedrizwan/received_events",
  "type": "User",
  "site_admin": false,
  "name": "ahmed",
  "company": null,
  "blog": null,
  "location": "Rawalpindi, Pakistan",
  "email": null,
  "hireable": null,
  "bio": null,
  "public_repos": 9,
  "public_gists": 0,
  "followers": 5,
  "following": 9,
  "created_at": "2013-05-06T18:32:59Z",
  "updated_at": "2015-08-29T18:17:58Z"
}
```

Ok for this example, I just want to extract _id_, _name_, _avatar_url_ and _public_repos_ from the response. So my model class (which is also a realm class btw), would look something like

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*XkIxB1PtetctYpuPBMDM3Q.png)

_\*\_\*_


> Note: If you want to extract everything from the JSON response, then I suggest visiting to this awesome [website](http://www.jsonschema2pojo.org), and generate a POJO. Then convert the Java code to Kotlin as described earlier.

Ok, lots of things to cover here... Starting off from the _RealmClass_ annotation... For Realm, this annotation is necessary for Realm’s code generation in Kotlin... _PrimaryKey_ annotation is also a Realm annotation, representing the Primary Key field (duh!)... Rest of the annotations are for Gson...

Now the **open** keyword! In kotlin it’s the opposite of **final** in Java. By default, Kotlin classes are final — that means if you want a class to be inherrited — we explicitly have to declare it as **open**. Same is the case with properties. Like in our model, _name_ is a property, with an actual getter and a setter (thanks to Kotlin). And in order for the getter/setter to be overridable (which Realm requires them to be), we put the keyword **open** along with the declaration of the property. Which makes sense (to me at least).

#### Retrofit Interface

Again the endpoint url is

`https://api.github.com/users/[some_user]`

So the interface would look something like :-

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*D8ABvZYNBRsw_Ffe5ilS0g.png)

_Simplicity of Retrofit :’)_

Notice how I’m returning Observable of a Github. This is possible because Retrofit allows Rx integration. And that’s super-super-cool!

#### Retrofit Builder

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*RAPCtfF_ztT6qB_wHoxLjw.png)

_Yay... Now I can make API calls... ^_^_

Because of Realm we use a special Gson instance, which basically adds an exclusion strategy for skipping Realm generated fields. Otherwise Gson doesn’t work with the model.

Next we create an instance of Retrofit with RxJavaCallAdapter factory which allows Rx integration and we also add the Gson converter using the Gson instance we created before.

#### Rx Magic

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*_fSWqpcqhzNfoJNKc8PMig.png)

_Lambdaaaaaaas! :’) Btw binding is my Databinding object!_


Now all I do is get the observable, subscribe to it and get our Github object. Once we have the object, it’s easy to persist the user to Realm database.

#### Caching

Caching is also (sort of) possible, because we can first fetch data from Realm Database (if it has already been saved)... Like this...

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*_31bgwIcMTmcCTJHKv03UQ.png)

_Dayum!_

And... That’s it! Now if you run the app...

![](https://d262ilb51hltx0.cloudfront.net/max/800/1*CLjgK7p_Cdro4oh0gu4_Qg.png)

_*slow clap*_

You can find the full code example [here](https://github.com/ahmedrizwan/RxRealmRetroKotlin/tree/master). Hope the article was somewhat useful!

Happy coding!


"""

Article(
  title = "Kotlin : Retrofit + RxAndroid + Realm",
  url = "https://medium.com/@ahmedrizwan/kotlin-retrofit-rxandroid-realm-39d7be5dc9dc#.w7ufbebxh",
  categories = listOf(
    "Retrofit",
    "Realm",
    "RxAndroid",
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "Ahmed Rizwan",
  date = LocalDate.of(2016, 3, 15),
  body = body
)
