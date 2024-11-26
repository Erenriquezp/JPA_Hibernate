package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateCreate {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        try {
            String name = JOptionPane.showInputDialog("Enter name:");
            String lastName = JOptionPane.showInputDialog("Enter last name:");
            String paymentMethod = JOptionPane.showInputDialog("Enter payment method:");

            em.getTransaction().begin();

            Customer c = new Customer();
            c.setName(name);
            c.setLastname(lastName);
            c.setPaymentMethod(paymentMethod);

            em.persist(c);
            em.getTransaction().commit();

            System.out.println("Customer created: " + c.getId());
            c = em.find(Customer.class, c.getId());
            System.out.println(c);
        }
        catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
