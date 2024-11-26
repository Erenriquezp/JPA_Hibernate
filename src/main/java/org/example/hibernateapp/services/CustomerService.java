package org.example.hibernateapp.services;

import org.example.hibernateapp.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void save(Customer entity);
    void delete(Long id);
}
