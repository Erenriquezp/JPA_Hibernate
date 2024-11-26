package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateResultListWhere {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select c from Customer c where c.paymentMethod=?1", Customer.class);

        System.out.println("Enter payment method:");
        String paymentMethod = s.nextLine();
        query.setParameter(1, paymentMethod);

        List<Customer> customers = query.getResultList();
        System.out.println(customers);
        em.close();
    }
}
