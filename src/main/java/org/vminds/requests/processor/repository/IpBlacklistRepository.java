package org.vminds.requests.processor.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vminds.requests.processor.config.CacheConfig;
import org.vminds.requests.processor.model.IpEntity;

@Repository
public interface IpBlacklistRepository extends CrudRepository<IpEntity, String> {

    @CachePut(cacheNames = CacheConfig.IP_BLACKLIST_CACHE_NAME, key = "#ip")
    IpEntity save(IpEntity entity);

    @Cacheable(cacheNames = CacheConfig.IP_BLACKLIST_CACHE_NAME)
    boolean existsById(String ip);
}
