package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import javax.swing.*;

public class HibernateEdit {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        try {
            Long id = Long.valueOf(JOptionPane.showInputDialog("Enter id:"));
            Customer c = em.find(Customer.class, id);

            String name = JOptionPane.showInputDialog("Enter name: ", c.getName());
            String lastName = JOptionPane.showInputDialog("Enter last name: ", c.getLastname());
            String paymentMethod = JOptionPane.showInputDialog("Enter payment method: ", c.getPaymentMethod());
            em.getTransaction().begin();
            c.setName(name);
            c.setLastname(lastName);
            c.setPaymentMethod(paymentMethod);
            em.merge(c);
            em.getTransaction().commit();
            System.out.println(c);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
