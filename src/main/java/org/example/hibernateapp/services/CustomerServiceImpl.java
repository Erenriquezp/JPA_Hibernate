package org.example.hibernateapp.services;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.repositories.CrudRepository;
import org.example.hibernateapp.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private EntityManager em;
    private CrudRepository<Customer> customerRepository;

    public CustomerServiceImpl(EntityManager em) {
        this.em = em;
        this.customerRepository = new CustomerRepository(em);
    }


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id));
    }

    @Override
    public void save(Customer entity) {
        try {
            em.getTransaction().begin();
            customerRepository.save(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            customerRepository.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
