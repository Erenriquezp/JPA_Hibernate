package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateList {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        EntityManager em = JpaUtil.getEntityManager();
        List<Customer> customers = em.createQuery("select c from Customer c", Customer.class).getResultList();
        customers.forEach(System.out::println);
        em.close();
    }
}