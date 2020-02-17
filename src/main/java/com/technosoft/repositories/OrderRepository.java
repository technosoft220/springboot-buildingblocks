package com.technosoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technosoft.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
