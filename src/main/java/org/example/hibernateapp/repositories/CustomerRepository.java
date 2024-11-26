package org.example.hibernateapp.repositories;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;

import java.util.List;

public class CustomerRepository implements CrudRepository<Customer> {
    private EntityManager em;

    public CustomerRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public void save(Customer entity) {
        if (entity.getId() != null && entity.getId() > 0) {
            em.merge(entity);
        } else {
            em.persist(entity);
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        em.remove(customer);
    }
}
