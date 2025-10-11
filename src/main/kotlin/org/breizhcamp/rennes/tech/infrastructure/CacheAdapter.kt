package org.breizhcamp.rennes.tech.infrastructure

import org.breizhcamp.rennes.tech.config.annotations.Adapter
import org.breizhcamp.rennes.tech.domain.ports.CachePort
import java.util.concurrent.ConcurrentHashMap

@Adapter
class CacheAdapter: CachePort {
    private val caches = ConcurrentHashMap<String, Any>()

    override fun invalidate(name: String) {
        caches.remove(name)
    }

    override fun <T> clean(name: String, filter: (T) -> Boolean) {
        val cache = caches[name] as? List<T> ?: return
        caches[name] = cache.filter(filter)
    }

    override fun <T> get(name: String, loadFn: () -> T): T {
        return (caches[name] as? T) ?: loadFn().also { caches[name] = it as Any }
    }
}
