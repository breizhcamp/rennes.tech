package org.breizhcamp.rennes.tech.domain.ports

interface CachePort {
    fun invalidate(name: String)
    fun <T> clean(name: String, filter: (T) -> Boolean)
    fun <T> get(name: String, loadFn: () -> T): T
}
