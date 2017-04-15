
import link.kotlin.scripts.Article
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = """

[**Swift**](https://developer.apple.com/library/content/documentation/Swift/Conceptual/Swift_Programming_Language/index.html) and [**Kotlin**](https://kotlinlang.org/docs/reference/) are two great languages for iOS and Android development respectively. They both have very **modern features and syntax** that can help enormously to build native apps. But, how do they both compare to each other? Are they similar? Can we reuse more code between platforms if we adopt them in our projects? 

Those are the questions we will explore in this post together with some real app code examples implementing the same features in both languages.

### Side to side

As commented, both languages are very similar. In fact, they are so similar that **I will focus on the differences** rather than the similarities in this post. Before exploring the code samples, let’s cover **the most relevant ones** that will pop up when translating between them:

*   **Swift Enums are more powerful**: In Swift, enums are very powerful and first-class type. They can contain different associated values per case (discrete union types), computed properties and most of the features of `structs`. Because of that, it is frequent to see patterns like [the `Result` enum](https://www.natashatherobot.com/swift-generics-box/) that are not common in Kotlin due to language limitations (_UPDATE: A similar pattern can be implemented in Kotlin by [using `sealed class` instead of `enum`](https://kotlinlang.org/docs/reference/classes.html#sealed-classes), but it is more complicated that the Swift counterpart_)

*   **Swift has no `data class`**: One interesting construction in Kotlin is `data class`. It allows you to declare containers of information that automatically implement things like equality and copying. Very common pattern when dealing with plain objects that has no counterpart in Swift.

*   **Swift does not have delegated classes or delegated properties**: A very interesting feature of Kotlin are delegated classes and properties. With them, you can forward invocation of methods to another class automatically, or define behaviors for properties such as `lazy`, `obsrvable`, etc. You can even create your own property delegates. In Swift, things like `lazy` are modifiers implemented in the language, so that means that you can not create your own but you are limited to the ones provided.

*   **Swift does not allow annotations**: Coming from the Java world, Kotlin has full support for annotations. However, in Swift that is not considered at all, so annotations like `@Inject` or `@Test` that are so common in Java libraries do not have any counterpart in Swift.

*   **Kotlin classes are final by default**: Kotlin classes are by default closed for extension, so you have to add `open` in any class you expect to be extended with inheritance.

*   **Kotlin has no `struct` or passing data by value**: In Swift you can decide whether to use a `class` or a `struct` for your data. The decision is not trivial, and results in different implementation details. Structs are always passed by value, meaning that every time you call a method with it, return a struct, assign a struct,... the values are actually copied to the new variable, and any modification will only affect the modified variable and not the others. Besides that, structs do not allow inheritance, so they tend to be a perfect candidate for data classes. In Kotlin, there is no `struct` type and the language follows the same pattern than Java, where basic types (String, Int,...) are passed by value, but the rest are passed by reference.

*   **Kotlin has no tuples**: Tuples are not implemented in Kotlin, so you will find yourself creating small `data classes` as counterpart for Swift tuples.

*   **Kotlin has no `typealias`**: I just found out that it is in the roadmap for 1.1 but at the moment (Kotlin 1.0.4) there is no `typealias`, so patterns like the one I explained [in this previous post](http://angelolloqui.com/blog/37-Swifty-names-for-modules-protocols-and-implementation-classes) are not possible yet.

*   **Kotlin has no `guard` statement**: In Swift, `guard` statements are a more expressive way for checking conditions of some function than the standard `if` sentence. In Kotlin, you need to stay with the `if` check and reverse the condition.

There are also other differences when comparing both languages. Things like exceptions, pattern matching, constructors, `if let` sentences, loop iteration, casting,... work in slightly different ways, but in general they follow the same principles and they have very similar syntaxes.

One last difference to mention when working for iOS/Android is that the Operative System offers different **runtime environments and libraries to developers**. A couple of points to highlight:

*   **Memory management**: Kotlin assumes the existence of a Garbage Collector (provided by the Dalvik runtime in Android) and therefore memory management is transparent for the developer. On the other hand, Swift manages memory with a Reference Count approach (ARC) so you will need to think about memory ownership and retain cycles.

*   **System frameworks and libraries are very different**: When accessing system frameworks or libraries like networking, UI, etc. they both offer very different APIs so the resulting code will look pretty different.

The list of differences might look long, but trust me, the amount of features shared by both is much longer. I suggest you read [swift-is-like-kotlin](https://nilhcem.github.io/swift-is-like-kotlin/) for an extensive comparison of different features of the languages side to side to get the feeling about it and some of the syntactic differences.

### MVVM + Rx + Coordinators

OK, so we have some differences in the language and big differences in the system frameworks and libraries, especially in UI. But can we still reuse code? And can we increase code reuse somehow?

The answer is yes, we can still reuse the code, and yes, we can increase code reuse by properly **splitting responsibilities and isolating dependencies**. In other words, we need to carefully chose a proper app architecture, because a wrong choice will highly limit the amount of code reused.

There are multiple architectural patterns out there (MVC, MVP, MVVM, VIPER, FLUX,...), each of them with their own benefits/drawbacks and best usage scenarios. I am not going to explore or compare them here because that is out of the scope of this post, but let me quickly review the one that I chose for this sample project: MVVM + Rx + Coordinators.

In MVVM, the code is split in **Model**, **View** and **ViewModel**. Views include `UIViewController`/`Activities`/`Fragments`, while ViewModel is a new entity introduced to map the model into data that can be consumed by the View layer easily. The main benefit of this pattern is that the **Model and the ViewModel are completely agnostic from UI**, and it is the UI the one that will “listen” for changes in the ViewModel and not the other way around. Code in View layers get shorter, as they do not need to apply any logic but **just display what the ViewModel provides**. This is a good feature for our case because we mentioned that UI code will be pretty different for both apps, so then we can keep those differences isolated and as short as possible.

The addition of **[Rx](http://reactivex.io/)** gives us a very powerful framework to “listen” for the ViewModel changes, and at the same time keep consistency between platforms since there are [RxSwift](https://github.com/ReactiveX/RxSwift) and [RxJava](https://github.com/ReactiveX/RxJava) counterparts following the same conventions. They also have platform dependent additions ([RxCocoa](https://github.com/ReactiveX/RxSwift/tree/master/RxCocoa) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)), but we will keep those **limited to the View layer**.

Finally, I have added a **Coordinator layer**. This layer is sometimes also called Router, and it basically glues the different parts together and handles the navigation between them. The usage of coordinators helps us **building the dependency tree and isolating the navigation logic** out of the View layer.

### Example code snippets:

For this post we will explore a very simple app consuming the [OpenTable API as exposed here](https://github.com/sosedoff/opentable). The app will just present a list of restaurants, add some pagination when scrolling to the end of the table, and open a simple restaurant detail. I should also mention that I kept the connector to the API in an external library, not dependent to Rx, in case we want to build a different app without Rx but reusing the OpenTable connector, but we could have added the Rx dependency there as well to simplify the callbacks.

Here are some of the different parts side to side:

##### RestaurantSearch

```swift
//RestaurantSearch.swift
public struct RestaurantSearch {
    public let totalResults: Int
    public let currentPage: Int
    public let resultsPerPage: Int
    public let restaurants: [Restaurant]
}

extension RestaurantSearch: Unboxable {
    public init(unboxer: Unboxer) {
        self.totalResults = unboxer.unbox(key: "total_entries")
        self.currentPage = unboxer.unbox(key: "current_page")
        self.resultsPerPage = unboxer.unbox(key: "per_page")
        self.restaurants = unboxer.unbox(key: "restaurants")
    }
}
```


```kotlin
//RestaurantSearch.kt
public data class RestaurantSearch(
       @JsonProperty("total_entries") public val totalResults: Int,
       @JsonProperty("restaurants") public val restaurants: Array<Restaurant>,
       @JsonProperty("current_page") public val currentPage: Int,
       @JsonProperty("per_page") public val resultsPerPage: Int
)
```

String similarity: **47.45%**

`RestaurantSearch` is a data class containing a search response. You can see in this particular case that both have the **same external API, but a different internal implementation**. In the Swift case we are using [Unbox](https://github.com/johnsundell/unbox) for JSON mapping, while in Kotlin we use [Jackson](https://github.com/FasterXML/jackson). Here we can see the first issue that was commented above: library differences. The good news are that it is an implementation detail that will not affect how you use the object at all, and that you could probably find (or build) a different mapping library that looks in the same way (or at least much more similar) in both platforms.

##### RestaurantService



```swift
//RestaurantService.swift
public class RestaurantService {
    let networkSession: URLSession
    let baseUrl: URL

    public init(urlSession: URLSession = URLSession.shared,
        baseUrl: URL = URL(string: "http://opentable.herokuapp.com/api/")!) {
        self.networkSession = urlSession
        self.baseUrl = baseUrl
    }

    public func findRestaurants(priceRange: Int? = nil,
                         name: String? = nil,
                         address: String? = nil,
                         state: String? = nil,
                         city: String? = nil,
                         zip: String? = nil,
                         country: String? = nil,
                         page: Int = 1,
                         pageSize: Int = 25) -> ServiceTask<RestaurantSearch> {

        var params = [RequestParameter]()
        params.append(RequestParameter("price", priceRange))
        params.append(RequestParameter("name", name))
        params.append(RequestParameter("address", address))
        params.append(RequestParameter("state", state))
        params.append(RequestParameter("city", city))
        params.append(RequestParameter("zip", zip))
        params.append(RequestParameter("country", country))
        params.append(RequestParameter("page", page))
        params.append(RequestParameter("per_page", pageSize))

        return NetworkRequestServiceTask<RestaurantSearch>(
            networkSession: networkSession,
            baseUrl: baseUrl,
            endpoint: "restaurants",
            params: params)
    }

    public func findRestaurant(restaurantId: Int) -> ServiceTask<Restaurant> {
        return NetworkRequestServiceTask<Restaurant>(
            networkSession: networkSession,
            baseUrl: baseUrl,
            endpoint: "restaurants/\(restaurantId)")
    }
}
```

```kotlin
//RestaurantService.kt
public class RestaurantService(
        val networkSession: HttpVolleySession = HttpVolleySession.getInstance(null, null),
        val baseUrl: String = "http://opentable.herokuapp.com/api/") {

    public fun findRestaurants(priceRange: Int? = null,
                         name: String? = null,
                         address: String? = null,
                         state: String? = null,
                         city: String? = null,
                         zip: String? = null,
                         country: String? = null,
                         page: Int = 1,
                         pageSize: Int = 25): ServiceTask<RestaurantSearch> {

        val params = arrayListOf<RequestParameter>()
        params.add(RequestParameter("price", priceRange))
        params.add(RequestParameter("name", name))
        params.add(RequestParameter("address", address))
        params.add(RequestParameter("state", state))
        params.add(RequestParameter("city", city))
        params.add(RequestParameter("zip", zip))
        params.add(RequestParameter("country", country))
        params.add(RequestParameter("page", page))
        params.add(RequestParameter("per_page", pageSize))

        return NetworkRequestServiceTask(clazz = RestaurantSearch::class.java,
                networkSession = networkSession,
                baseUrl = baseUrl,
                endpoint = "restaurants",
                params = params)
    }

    public fun findRestaurant(restaurantId: Int): ServiceTask<Restaurant> {
        return NetworkRequestServiceTask(clazz = Restaurant::class.java,
                networkSession = networkSession,
                baseUrl = baseUrl,
                endpoint = "restaurants/${"$"}{restaurantId}")
    }
}
```

String similarity: **87.62%**

`RestaurantService` is a class that provides access to the OpenTable API. It provides a couple of methods to make search queries, and return a `ServiceTask` object that encapsulates the networking request and mapping. The resulting code as you can see is **almost identical**, except for the usage of a `UrlSession` vs a `VolleySession` and **minor syntactic differences**. Of course, once again the implementation details of the `NetworkRequestServiceTask` will be different in both platforms to deal with the networking libraries, but API wise they are the same and the `NetworkRequestServiceTask` and its dependencies can be reused across projects.

##### RestaurantListState

```swift
//RestaurantListState.swift
struct RestaurantsListState {
    let restaurants: [Restaurant]
    let page: Int?
    let lastPage: Int?
    let totalResults: Int?
    let error: Error?
    let isLoading: Bool

    private init(restaurants:[Restaurant] = [], page: Int? = nil, lastPage: Int? = nil, totalResults: Int? = nil, error: Error? = nil, loading: Bool = false) {
        self.restaurants = restaurants
        self.page = page
        self.lastPage = lastPage
        self.totalResults = totalResults
        self.error = error
        self.isLoading = loading
    }

    static func notLoaded() -> Restaurants.List.State {
        return Restaurants.List.State()
    }

    static func loading(page: Int, previousState: Restaurants.List.State) -> Restaurants.List.State {
        return Restaurants.List.State(restaurants: previousState.restaurants, page: page, lastPage: previousState.lastPage, totalResults: previousState.totalResults, loading: true)
    }

    static func loaded(restaurants: [Restaurant], lastPage: Int, totalResults: Int) -> Restaurants.List.State {
        return Restaurants.List.State(restaurants: restaurants, lastPage: lastPage, totalResults: totalResults)
    }

    static func failedLoad(error: Error, previousState: Restaurants.List.State) -> Restaurants.List.State {
        return Restaurants.List.State(restaurants: previousState.restaurants, page: previousState.page, error: error)
    }

    var isLoaded: Bool { return lastPage != nil && totalResults != nil && isLoading == false }
    var isNotLoaded: Bool { return page == nil && lastPage == nil && isLoading == false }
    var isFailed: Bool { return error != nil }

}
```

```kotlin
//RestaurantListState.kt
data class RestaurantsListState private constructor(
       val restaurants: Array<Restaurant> = arrayOf(),
       val page: Int? = null,
       val lastPage: Int? = null,
       val totalResults: Int? = null,
       val error: Error? = null,
       val isLoading: Boolean = false) {

   companion object {
       fun notLoaded(): RestaurantsListState {
           return RestaurantsListState()
       }
       fun loading(page: Int, previousState: RestaurantsListState): RestaurantsListState {
           return RestaurantsListState(restaurants = previousState.restaurants, page = page, lastPage = previousState.lastPage, totalResults = previousState.totalResults, isLoading = true)
       }
       fun loaded(restaurants: Array<Restaurant>, lastPage: Int, totalResults: Int): RestaurantsListState {
           return RestaurantsListState(restaurants = restaurants, lastPage = lastPage, totalResults = totalResults)
       }
       fun failedLoad(error: Error, previousState: RestaurantsListState): RestaurantsListState {
           return RestaurantsListState(restaurants = previousState.restaurants, page = previousState.page, error = error)
       }
   }

   val isLoaded: Boolean get() = lastPage != null && totalResults != null && isLoading == false
   val isNotLoaded: Boolean get() = page == null && lastPage == null && isLoading == false
   val isFailed: Boolean get() = error != null

}
```

String similarity: **77.33%**

Here he have `RestaurantListState`, which is just a plain class holding state information used by the ViewModel. In this particular case, there are no dependencies to any other library or system framework, and therefore the **code is again very similar** except for the minor differences commented above (ex: `static` methods vs `companion` methods or `struct` vs `data class`)

##### RestaurantListViewModel



```swift
//RestaurantListViewModel.swift
class RestaurantsListViewModel {
    private let restaurantService: RestaurantService
    private let state = BehaviorSubject<Restaurants.List.State>(value: .notLoaded())

    var stateObservable: Observable<Restaurants.List.State> { return state.asObservable() }
    var stateValue: Restaurants.List.State { return try! state.value() }

    init(restaurantService: RestaurantService) {
        self.restaurantService = restaurantService
    }

    func loadNextPage() {
        let nextPage = (stateValue.lastPage ?? 0) + 1
        loadPage(nextPage)
    }

    private func loadPage(_ page: Int) {
        guard !stateValue.isLoading else { return }

        state.onNext(.loading(page: page, previousState: stateValue))

        restaurantService.findRestaurants(country: "US", page: page)
            .onCompletion { [weak self] (result: RestaurantSearch?) in
                if let strongSelf = self {
                    let restaurants = strongSelf.stateValue.restaurants
                    strongSelf.state.onNext(.loaded(
                        restaurants: restaurants + (result?.restaurants ?? []),
                        lastPage: page,
                        totalResults: result?.totalResults ?? restaurants.count))
                }
            }.onError { [weak self] (error) in
                if let strongSelf = self {
                    strongSelf.state.onNext(.failedLoad(error: error, previousState: strongSelf.stateValue))
                }
            }.execute()
    }
}
```

```kotlin
    //RestaurantListViewModel.kt
    class RestaurantsListViewModel(
           private val restaurantService: RestaurantService) {
       private val state = BehaviorSubject.create(RestaurantsListState.notLoaded())

       val stateObservable: Observable<RestaurantsListState> get() = state.asObservable()
       val stateValue: RestaurantsListState get() = state.value

       fun loadNextPage() {
           val nextPage = (stateValue.lastPage ?: 0) + 1
           loadPage(nextPage)
       }

       private fun loadPage(page: Int) {
           if (stateValue.isLoading) {
               return
           }

           state.onNext(RestaurantsListState.loading(page = page, previousState = stateValue))

           restaurantService.findRestaurants(country = "US", page = page)
                .onCompletion { result ->
                    val restaurants = stateValue.restaurants
                    this.state.onNext(RestaurantsListState.loaded(
                        restaurants = restaurants + (result?.restaurants ?: arrayOf()),
                        lastPage = page,
                        totalResults = result?.totalResults ?: restaurants.size))
                }.onError { error ->
                    this.state.onNext(RestaurantsListState.failedLoad(error = error, previousState = stateValue))
                }.execute()
       }
    }
```

String similarity: **77.25%**

This class corresponds to the ViewModel that will be used by the list of restaurants screen. As you can see, once again **code fully resembles to each other**, with the small difference of memory management and `guard` statement in the Swift case. Very interesting to note as well is that this is the first code snippet using Rx. You can see how they both share the same methods and objects, so the resulting code is equivalent in both platforms. Thanks Rx for keeping consistency!

##### RestaurantsCoordinator

```swift
//RestaurantsCoordinator.swift
class RestaurantsCoordinator: BaseCoordinator {
    static var identifier = CoordinatorIdentifier<Restaurants.Coordinator>(identifier: "RestaurantsCoordinator")
    private let restaurantService: RestaurantService

    init(parentCoordinator: Coordinator?, restaurantService: RestaurantService = RestaurantService()) {
        self.restaurantService = restaurantService
        super.init(parentCoordinator: parentCoordinator)
    }

    override func start() {
        let vm = Restaurants.List.ViewModel(restaurantService: restaurantService)
        let vc = Restaurants.List.ViewController.newController(coordinator: self, viewModel: vm)
        presentViewController(viewController: vc)
    }

    func openRestaurant(restaurant: Restaurant) {
        let vm = Restaurants.Detail.ViewModel(restaurantService: restaurantService, restaurant: restaurant)
        let vc = Restaurants.Detail.ViewController(coordinator: self, viewModel: vm)
        presentViewController(viewController: vc)
    }
}

```

```kotlin
//RestaurantsCoordinator.kt
class RestaurantsCoordinator(context: Context, parentCoordinator: Coordinator?, val restaurantService: RestaurantService = RestaurantService()): BaseCoordinator(context, parentCoordinator) {
   companion object {
       val identifier = CoordinatorIdentifier<RestaurantsCoordinator>(identifier = "RestaurantsCoordinator")
   }

   override fun start() {
       val vm = RestaurantsListViewModel(restaurantService = restaurantService)
       val intent = RestaurantsListActivity.newIntent(coordinator = this, viewModel = vm)
       presentActivity(intent)
   }

   fun openRestaurant(restaurant: Restaurant) {
       val vm = RestaurantsDetailViewModel(restaurantService = restaurantService, restaurant = restaurant)
       val intent = RestaurantsDetailActivity.newIntent(coordinator = this, viewModel = vm)
       presentActivity(intent)
   }
}
```

String similarity: **70.40%**

`RestaurantsCoordinator` is the class responsible of navigation and coordination between the different parts in the MVVM. It basically instantiates new views, view models and services when needed, and present them in screen. Check it out because even if the navigation in both platforms is handled differently, **the resulting code is so similar that you can barely notice the differences** except for the constructors and statics.

##### RestaurantsListView



```kotlin
//RestaurantsListViewController.swift
class RestaurantsListViewController: UIViewController {
    fileprivate var viewModel: Restaurants.List.ViewModel!
    fileprivate var coordinator: Restaurants.Coordinator!
    fileprivate let disposeBag = DisposeBag()
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchTitle: UILabel!

    static func newController(coordinator: Restaurants.Coordinator, viewModel: Restaurants.List.ViewModel) -> UIViewController {
        let vc = R.storyboard.restaurantsListViewController.instantiateInitialViewController()!
        vc.viewModel = viewModel
        vc.coordinator = coordinator
        return vc
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.register(R.nib.restaurantTableViewCell)
        tableView.register(R.nib.loadingTableViewCell)
        tableView.register(R.nib.errorTableViewCell)

        viewModel.stateObservable.subscribe(onNext: { [unowned self] state in
            if let results = state.totalResults {
                self.searchTitle.text = "Number of restaurants: \(results)"
            }
            else if state.isLoading {
                self.searchTitle.text = "Loading restaurants"
            }
        }).addDisposableTo(disposeBag)

        //Data
        cellDataObservable.bindTo(tableView.rx.items(dataSource: tableDataSource))
            .addDisposableTo(disposeBag)

        //Item selection
        tableView.rx.modelSelected(CellData.self).subscribe(onNext: { [unowned self] (data) in
            if  let restaurant = data.restaurant {
                self.coordinator.openRestaurant(restaurant: restaurant)
            }
        }).addDisposableTo(disposeBag)

        //Page loading
        tableView.rx.willDisplayCell.filter { [unowned self] (cell, indexPath) -> Bool in
            indexPath.row == self.viewModel.stateValue.restaurants.count - 1
        }.subscribe(onNext: { [unowned self] (data) in
            self.viewModel.loadNextPage()
        }).addDisposableTo(disposeBag)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if viewModel.stateValue.isNotLoaded || viewModel.stateValue.isFailed {
            viewModel.loadNextPage()
        }
    }
}

//MARK: Table view management
extension Restaurants.List.ViewController {
    enum CellData {
        case restaurant(Restaurant)
        case loading
        case error(Error)

        var restaurant: Restaurant? {
            guard case .restaurant(let restaurant) = self else { return nil }
            return restaurant
        }        
    }

    struct SectionOfData: SectionModelType {
        typealias Item = CellData
        var items: [Item]

        init(items: [Item]) {
            self.items = items
            self.identity = identity
        }
        init(original: SectionOfData, items: [Item]) {
            self = original
            self.items = items
        }
    }

    fileprivate var cellDataObservable: Observable<[SectionOfData]> {
        return viewModel.stateObservable.map { [weak self] (state) -> [SectionOfData] in
            let restaurants: [CellData] = state.restaurants.map { .restaurant($0) }
            var sections = [SectionOfData(items: restaurants)]
            if self?.viewModel.stateValue.isLoading ?? false {
                sections.append(SectionOfData(items: [.loading]))
            }
            else if let err = self?.viewModel.stateValue.error {
                sections.append(SectionOfData(items: [.error(err)]))
            }
            return sections
        }
    }

    fileprivate var tableDataSource: RxTableViewSectionedDataSource<SectionOfData> {
        let dataSource = RxTableViewSectionedDataSource<SectionOfData>()
        dataSource.configureCell = { ds, tv, ip, item in
            switch item {
            case .restaurant(let restaurant):
                let cell = tv.dequeueReusableCell(withIdentifier: R.nib.restaurantTableViewCell, for: ip)!
                cell.configure(restaurant: restaurant)
                return cell

            case .loading:
                let cell = tv.dequeueReusableCell(withIdentifier: R.nib.loadingTableViewCell, for: ip)!
                cell.configure(message: "Loading restaurants")
                return cell

            case .error(let err):
                let cell = tv.dequeueReusableCell(withIdentifier: R.nib.errorTableViewCell, for: ip)!
                cell.configure(error: err)
                return cell
            }
        }
        return dataSource
    }

}
```

```kotlin
//RestaurantsListActivity.kt
class RestaurantsListActivity : AppCompatActivity() {
   enum class Sections { restaurant, loading, error }

   lateinit var coordinator: RestaurantsCoordinator
   lateinit var viewModel: RestaurantsListViewModel

   companion object {
       fun newIntent(coordinator: RestaurantsCoordinator, viewModel: RestaurantsListViewModel, @LayoutRes layoutId: Int = R.layout.restaurants_list_activity): Intent {
           val intent = Intent(coordinator.context, RestaurantsListActivity::class.java)
           ActivityInjector.register(intent) { activity: RestaurantsListActivity ->
               activity.coordinator = coordinator
               activity.viewModel = viewModel
               activity.setContentView(layoutId)
           }
           return intent
       }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       ActivityInjector.inject(this)

       viewModel.stateObservable.subscribe {
           if (it.totalResults != null) {
               search_title.text = "Number of restaurants: ${"$"}{it.totalResults}"
           }
           else if (it.isLoading) {
               search_title.text = "Loading restaurants"
           }
       }

       recyclerView.adapter = recyclerViewAdapter
       recyclerView.layoutManager = layoutManager

       //Item selection
       recyclerViewAdapter.modelSelected.subscribe { data ->
           val restaurant = data.restaurant
           if (restaurant != null) {
               this.coordinator.openRestaurant(restaurant = restaurant)
           }
       }

       //Page loading
       RxRecyclerView.scrollEvents(recyclerView).filter { event ->
           layoutManager.findLastVisibleItemPosition() == viewModel.stateValue.restaurants.size - 1
       }.subscribe {
           viewModel.loadNextPage()
       }
   }

   override fun onResume() {
       super.onResume()
       if (viewModel.stateValue.isNotLoaded || viewModel.stateValue.isFailed) {
           viewModel.loadNextPage()
       }
   }

   override fun onDestroy() {
       super.onDestroy()
       if (isFinishing) {        //Only unregister when explicit finish happens
           ActivityInjector.unregister(intent)
       }
   }

   // List management
   data class CellData(val restaurant: Restaurant? = null, val loading: Boolean = false, val error: Error? = null)

   val cellDataObservable: Observable<List<SectionModelType<CellData>>> by lazy {
       viewModel.stateObservable.flatMap { state ->
           val restaurants = state.restaurants?.map { CellData(restaurant = it) } ?: listOf()
           val sections = arrayListOf(SectionModelType<CellData>(restaurants, Sections.restaurant.ordinal))
           if (viewModel.stateValue.isLoading) {
               sections.add(SectionModelType<CellData>(listOf(CellData(loading = true)), Sections.loading.ordinal))
           }
           else if (viewModel.stateValue.isFailed) {
               sections.add(SectionModelType<CellData>(listOf(CellData(error = viewModel.stateValue.error)), Sections.error.ordinal))
           }
           Observable.just(sections.toList())
       }
   }

   val recyclerViewAdapter: RxRecyclerViewAdapter<CellData> by lazy {
       RxRecyclerViewAdapter(cellDataObservable)
               .useLayoutId { type ->
                   if (type == Sections.restaurant.ordinal) {
                       R.layout.restaurant_cell_layout
                   }
                   else if (type == Sections.loading.ordinal) {
                       R.layout.loading_cell_layout
                   }
                   else {
                       R.layout.error_cell_layout
                   }
               }
               .configureItem { view, data, position, type ->
                   if (type == Sections.restaurant.ordinal) {
                       val restaurant = data.restaurant
                       if (restaurant != null) {
                           view.pictureImageView.loadImage(restaurant)
                           view.nameTextView.setText(restaurant.name)
                           view.addressTextView.setText(restaurant.address)
                           var price = ""
                           kotlin.repeat(restaurant.price) {
                               price = price + "€"
                           }
                           view.priceTextView.setText(price)
                       }
                   }
                   else if (type == Sections.error.ordinal) {
                       view.textView.setText(data.error?.message)
                   }
               }
   }

   val layoutManager = LinearLayoutManager(this)

}
```

String similarity: **52.27%**

OK, so here is the big deal. So far, code was very similar and can be easily shared (with small editions) from one to the other platform. However, as we warned above, the UI code will be very different. How much? You can compare it by yourself...

Basically, they **both share a common approach** in which they have a constructor receiving the dependencies (for a very simple Dependency Injection); a view creation method (`viewDidLoad` / `onCreate`) that basically subscribes to the ViewModel observables and configures the list; a view appear method (`viewWillAppear` / `onResume`) that will load new results; and a set of methods and types to deal with the information from the list, which by the way could have been moved partially to the ViewModel probably.

As you can see, **they are similar (especially conceptually) but not equal**, so all in all I would say that you could use one as a template for the other, but you will still need to write about half of the code, but judge it yourself. 

Of course, if you create your own wrappers around the UI components, you could actually achieve a much higher code reuse rate by exposing similar APIs, but you will need to develop them and they will introduce extra learning steps for new developers (like I did for networking in the `NetworkServiceTask`, use a similar API with different implementations)

<u>Off-topic</u>: note how the usage of Kotlin extensions removed all of the `findViewById` so typical (and error prone) in Android UI classes, and in iOS the usage of [R.swift](https://github.com/mac-cain13/R.swift) is also cleaning up many of the hardcoded strings.

##### Layout

In this case I am not copying the layout code because **it is completely different**. In iOS, I have used Interface Builder to set my views, while in Android it uses layout XMLs.

### Conclusion

**Swift and Kotlin are great languages**, both adding a lot of added value to their corresponding alternative for mobile apps (Objective-C / Java). They are **safer** thanks to a strict strongly typed system that includes nullability in it. They are both very **pleasant** to work with because of a impressive type inference compiler and a beautiful and modern syntax. They both have very **powerful features**, like extensions, immutability and functional programming additions that allow new and better patterns and constructions to emerge.

However, **although very similar**, they are not identical. **They have a few differences intrinsic to the language and the SO/runtime** where they run that makes impossible to use the exact same code in both platforms. Nevertheless, if we structure our code with a good pattern and **separation of concerns**, we can certainly get very similar code in most of our classes, keeping the main differences when interacting with system APIs like UI or Networking isolated. 

We have seen a few examples of different parts of the app using MVVM + Rx + Coordinators, and we can see how code resembles to each other in the example classes (between 50% to 90% code matching, not counting layout). In fact, it is so similar that I made most of one platform as a copy+paste+edit from the other one, **saving a lot of time and reducing bugs** as both will behave almost identically. An added benefit is that Kotlin developers will be able to read/write Swift with ease and vice versa, which helps **consolidating teams, practices and code across platforms**, but still writing **fully native and high quality apps** with small differences to **adapt to different user’s expectations** derived from the platform itself (which is a big difference with other cross platform solutions out there like [ReactNative](https://facebook.github.io/react-native/), [Xamarin](https://www.xamarin.com/) or [PhoneGap/Cordova](http://phonegap.com/))

Note: String similarity calculated with [Tools4Noobs](https://www.tools4noobs.com/online_tools/string_similarity/)

"""

Article(
  title = "Swift vs Kotlin for real iOS/Android apps",
  url = "http://angelolloqui.com/blog/38-Swift-vs-Kotlin-for-real-iOS-Android-apps",
  categories = listOf(
    "Kotlin",
    "Swift"
  ),
  type = article,
  lang = EN,
  author = "Angel G. Olloqui",
  date = LocalDate.of(2016, 10, 18),
  body = body
)
