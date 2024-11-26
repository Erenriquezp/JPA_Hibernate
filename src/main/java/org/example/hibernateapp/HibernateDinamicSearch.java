package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateDinamicSearch {
    public static void main(String[] args) {
        System.out.println("Hello Hibernate Dinamic Search!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name to search: ");
        String name = scanner.nextLine();

        System.out.println("Enter the lastname to search: ");
        String lastname = scanner.nextLine();

        System.out.println("Enter the payment method to search: ");
        String paymentMethod = scanner.nextLine();

        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.equal(from.get("name"), name));
        }
        if (lastname != null && !lastname.isEmpty()) {
            predicates.add(cb.equal(from.get("lastname"), lastname));
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            predicates.add(cb.equal(from.get("paymentMethod"), paymentMethod));
        }

        query.select(from).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        List<Customer> customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        em.close();
    }
}
