package com.task.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.task.orders.model.Order;


@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    @Cacheable("orderEntityCache")
    Iterable<Order> findAll();
}