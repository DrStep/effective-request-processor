package org.vminds.requests.processor.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vminds.requests.processor.config.CacheConfig;
import org.vminds.requests.processor.model.UaEntity;

@Repository
public interface UaBlacklistRepository extends CrudRepository<UaEntity, String> {

    @CachePut(cacheNames = CacheConfig.UA_BLACKLIST_CACHE_NAME, key = "#ua")
    UaEntity save(UaEntity entity);

    @Cacheable(cacheNames = CacheConfig.UA_BLACKLIST_CACHE_NAME)
    boolean existsById(String ip);
}
