package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.services.CustomerService;
import org.example.hibernateapp.services.CustomerServiceImpl;
import org.example.hibernateapp.util.JpaUtil;

import java.util.Optional;

public class HibernateCrudService {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        CustomerService service = new CustomerServiceImpl(em);

        System.out.println("Find all customers: ");
        service.findAll().forEach(System.out::println);

        System.out.println("Find customer with id 1: ");
        Optional<Customer> optionalCustomer = service.findById(1L);
        optionalCustomer.ifPresent(System.out::println);

        System.out.println("Create new customer: ");
        Customer newCustomer = new Customer();
        newCustomer.setName("Yui");
        newCustomer.setLastname("Hirasawa");
        newCustomer.setPaymentMethod("Paypal");

        service.save(newCustomer);
        System.out.println("New customer created: ");
        service.findAll().forEach(System.out::println);

        System.out.println("Update customer with id 1: ");
        Long id = newCustomer.getId();
        optionalCustomer = service.findById(id);
        optionalCustomer.ifPresent(customer -> {
            customer.setPaymentMethod("Transfer");
            service.save(customer);
            System.out.println("Customer updated: ");
            service.findAll().forEach(System.out::println);
        });

        System.out.println("Delete customer with id 7: ");
        id = newCustomer.getId();
        optionalCustomer = service.findById(id);
        optionalCustomer.ifPresent(customer -> {
            service.delete(customer.getId());
            System.out.println("Customer deleted: ");
            service.findAll().forEach(System.out::println);
        });

        em.close();
    }
}
