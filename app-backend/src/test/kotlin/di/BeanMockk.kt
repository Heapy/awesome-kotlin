package di

inline fun <reified V : Any> MutableBean<V>.mockk() {
    mock { io.mockk.mockk() }
}

inline fun <reified V : Any> MutableBean<V>.mockk(
    crossinline block: V.() -> Unit,
) {
    mock { io.mockk.mockk(block = block) }
}
