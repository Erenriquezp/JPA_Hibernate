package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernateSingleResultWhere {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select c from Customer c where c.paymentMethod=?1", Customer.class);

        System.out.println("Enter payment method:");
        String paymentMethod = s.nextLine();
        query.setParameter(1, paymentMethod);
        query.setMaxResults(1);

        Customer c = (Customer) query.getSingleResult();
        System.out.println(c);
        em.close();
    }
}
