---
title: 'Our Experiment Building a Multiselection Solution for Android in Kotlin'
url: https://yalantis.com/blog/how-we-created-a-multiselection-solution-for-android/
categories:
    - Kotlin
    - Android
author: Artem Kholodnyi
date: Nov 03, 2016 12:29
---
“Multiselection can be quite tricky on mobile,” says our designer, Vitaly Rubtsov. Multiselection solutions found in most applications – Telegram, Apple Music, Spotify and others – are usually not that flexible and can be quite uncomfortable to use.

When creating your own playlists in Apple Music, for example, it can be confusing to understand which songs have already been added without switching between several screens or endlessly scrolling through a list of selected songs.

And the situation may get even worse if we decide to apply filters. In this case, the list of compositions may change once we’ve applied a filter, but the compositions you’ve already selected may not be displayed at all. Vitaly decided to solve this problem with his own concept for multiselection (originally published to [Dribbble](https://dribbble.com/shots/2904577-Multi-Selection-Experiment)).

The idea is just brilliant: the screen is divided into two parts, so you can always “see and manage what you’ve selected without having to leave the current view,” as Vitaly explains. Filters are only applied to the main list from which you select, but are not applied to your list of selected items.

That's when I knew that Vitaly’s multiselection concept must be brought to life by any means; so I started my work on the component almost immediately. And now let’s see how the Android Multiselection Animation was born.

![](https://lh6.googleusercontent.com/yIkYWeVg0xYmKOWMEMlUn1hRk2B2DBjeD14bqTRS5cbsRYyLKpvkJ6bPg-vUbQjyCaVHsCahYEXQfM_2RCj-d1x0dcqfPZ34XKX3cMF0k2Vfr5ktrQrmpTqYHwA12LXm8ESxsmSj)

## Implementing the component

The animation’s logic seems straightforward, yet it has a few catches.

The component has a `ViewPager` with two `RecyclerViews`. We can make a `ViewPager` page narrower than the screen by overriding the `getPageWidth` method in the `ViewPager` adapter and returning a floating number between 0 and 1.

A `ViewPager` has two pages, each with the `RecyclerView`. Unselected items are on the left list. Selected items are on the right one. For instance, if you click an unselected item, a few things will happen:

1. The clicked item is deleted from the unselected items list and added to the container that holds both lists.

2. Item position in the selected items list is determined. (The unselected list always has its items sorted alphabetically. The selected list has its items in the order they were selected)

3. A hidden item is added to the selected items list.

4. The translation animation is run for the clicked item.

5. The clicked item is deleted and the hidden item in the selected list is shown.

The most tricky part of this sequence is removing a view from the layout manager; otherwise, the layout manager will try to recycle it, which will cause an error since we are deleting the view from `RecyclerView`:

    sourceRecycler.layoutManager.removeViewAt(position)

## Technology stack

I chose the Kotlin programming language as the tool for this job. The main benefits of Kotlin are its concise syntax and virtually no `NullPointerException` crashes compared to Java. (And, as a result, Kotlin makes for happier developers.) Here are a few useful Kotlin features that made my life easier while I was implementing this library:

### Extension functions

With extension functions, you can ‘extend’ pre-defined classes with useful methods – even classes you don’t own!

Take the Android View class, for example. Often you need to remove a view from its old parent and attach it to a new one:

```kotlin
​fun View.removeFromParent() {
   val parent = this.parent
   if (parent is ViewGroup) {
       parent.removeView(this)
   }
}
```

After defining the above method, you can now call it from anywhere in your project like this:

Or, you can even write a method that removes the view from its current parent and attaches the view to a new one:

```kotlin
view.attachTo(newParent)
```

One more advantage is that you can add the (strangely absent) `setScaleXY `method. I’ve hardly ever seen `setScaleX` used without `setScaleY`, or vice versa. So why not have a single method that sets both scales? Let’s do it:

```kotlin
fun View.setScaleXY(scale: Float) {
   scaleX = scale
   scaleY = scale
}
```

You can find more examples of how we used extension functions in the Extensions.kt file in the library’s source code.

### Null safety

Kotlin’s null safety feature is a game changer. The ‘?.’ operator works just like ‘.’ but if the object it’s called upon is null, ‘?.’ won’t throw a `NullPointerException`, but will return null instead:

```kotlin
var targetView: View? = targetRecycler.findViewHolderForAdapterPosition(prev)?.itemView
```

The code above, for instance, won’t crash even if `findViewHolderForAdapterPosition` returns null.

### Collections

Kotlin comes with `stdlib`, which includes a lot of neat collection functions like map and filter. These methods are widespread, and basically exhibit the same behavior across programming languages, including Java 8 (streams). Unfortunately, streams are still not available out of the box for Android development.

For our multiselection library, we needed to animate transparency of every view child except the child with a specific id. The following Kotlin code does the job well:

```kotlin
if (view is ViewGroup) {
   (0..view.childCount - 1)
           .map { view.getChildAt(it) }
           .filter { it.id != R.id.yal_ms_avatar }
           .forEach { it.alpha = value }
}
```

Accomplishing the same in Java would take perhaps twice as many lines of code as we used here.

* ### Better syntax

In general, Kotlin’s syntax is more concise and readable than Java’s.

One example is the when expression. Unlike switch (Java’s analogue), Kotlin’s when expression returns a value, so you have to assign it to a variable or return it from a function. That feature in and of itself allows for shorter and more readable code:

```kotlin
private fun getView(position: Int, pager: ViewPager): View = when (position) {
   0 -> pageLeft
   1 -> pageRight
   else -> throw IllegalStateException()
}
```

## How to use MultiSelect

Here are 5 simple steps to follow if you want to use our multiselect component in your project.

1. First, add this to your root build.gradle:

```gradle
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
}
```

And then add this to your module `build.gradle`:

```gradle
dependencies {
    compile 'com.github.yalantis:multi-selection:v0.1'
}
```

2. Then create a `ViewHolder`:

```javas
class ViewHolder extends RecyclerView.ViewHolder {
   TextView name;
   TextView comment;
   ImageView avatar;

   public ViewHolder(View view) {
       super(view);
       name = (TextView) view.findViewById(R.id.name);
       comment = (TextView) view.findViewById(R.id.comment);
       avatar = (ImageView) view.findViewById(R.id.yal_ms_avatar);
   }

   public static void bind(ViewHolder viewHolder, Contact contact) {
       viewHolder.name.setText(contact.getName());
       viewHolder.avatar.setImageURI(contact.getPhotoUri());
       viewHolder.comment.setText(String.valueOf(contact.getTimesContacted()));
   }
}
```

Take note of the static bind method. It’s useful to have it in here because that way you can use the same viewholder in both adapters.

3. Next, create two adapters for unselected and selected items. The first one should extend `BaseLeftAdapter`; the second, `BaseRightAdapter`:

```java
​public class LeftAdapter extends BaseLeftAdapter<Contact, ViewHolder>{

   private final Callback callback;

   public LeftAdapter(Callback callback) {
       super(Contact.class);
       this.callback = callback;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
       return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       super.onBindViewHolder(holder, position);

       ViewHolder.bind(holder, getItemAt(position));

       holder.itemView.setOnClickListener(view -> {
           // ...
           callback.onClick(holder.getAdapterPosition());
           // ...
       });

   }

}
```

Notice that you should call super constructor with the model class you use in the adapters.

The adapter for selected items is very similar:

```java
public class RightAdapter extends BaseRightAdapter<Contact, ViewHolder> {

   private final Callback callback;

   public RightAdapter(Callback callback) {
       this.callback = callback;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
       return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
       super.onBindViewHolder(holder, position);

       ViewHolder.bind(holder, getItemAt(position));

       holder.itemView.setOnClickListener(view -> {
           // ...
           callback.onClick(holder.getAdapterPosition());
           // ...
       });
   }
}
```

Adapters need to extend different base classes because unselected items are sorted, but selected items stay in the order they were previously selected.

4. Finally, call the builder:

```java
MultiSelectBuilder<Contact> builder = new MultiSelectBuilder<>(Contac
       .withContext(this)
       .mountOn((ViewGroup) findViewById(R.id.mount_point))
       .withSidebarWidth(46 + 8 * 2); // ImageView width with paddings
```

You’ll then need to:

* Pass the context.
* Pass the view (usually `FrameLayout`) that you want the component to be mounted on.
* Specify the sidebar width in dp (shown in the image below).

![how-we-build-a-multiselection-component-for-android-application](http://images.yalantis.com/w770/uploads/ckeditor/pictures/2105/content_Artboard.jpg)

5. Last but not least, set the adapters:

```java
LeftAdapter leftAdapter = new LeftAdapter(position -> mMultiSelect.select(position));
RightAdapter rightAdapter = new RightAdapter(position -> mMultiSelect.deselect(position));

leftAdapter.addAll(contacts);

builder.withLeftAdapter(leftAdapter)
       .withRightAdapter(rightAdapter);
```

Now all you need to do is call `builder.build()`, which returns the `MultiSelect<T>` instance.

You can find this MultiSelect library and many more in our GitHub repository, and can check out our designs on Dribbble:

* [GitHub](https://github.com/Yalantis/Multi-Selection)

* [Dribbble](https://dribbble.com/shots/2904577-Multi-Selection-Experiment)

You can also get a cool demo with our component on [Google Play Store](https://play.google.com/store/apps/details?id=com.yalantis.multiselect.demo).
