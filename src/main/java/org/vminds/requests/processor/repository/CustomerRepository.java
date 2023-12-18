package org.vminds.requests.processor.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vminds.requests.processor.config.CacheConfig;
import org.vminds.requests.processor.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @CachePut(cacheNames = CacheConfig.CUSTOMER_CACHE_NAME, key = "#id")
    Customer save(Customer entity);

    @Cacheable(cacheNames = CacheConfig.CUSTOMER_CACHE_NAME)
    Optional<Customer> findById(Integer id);

    @Cacheable(cacheNames = CacheConfig.CUSTOMER_CACHE_NAME)
    boolean existsById(Integer id);
}
