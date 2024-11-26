package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernateById {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter id:");
        Long id = s.nextLong();

        EntityManager em = JpaUtil.getEntityManager();
        Customer c = em.find(Customer.class, id);

        System.out.println(c);
        Customer customer = em.find(Customer.class, id);
        System.out.println(customer);

        em.close();
    }
}
