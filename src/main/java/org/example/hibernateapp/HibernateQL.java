package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import org.example.hibernateapp.dto.CustomerDto;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateQL {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        System.out.println("================ List all ================");
        List<Customer> customers = em.createQuery("select c from Customer c", Customer.class).getResultList();
        customers.forEach(System.out::println);

        System.out.println("================ List by id ================");
        Customer customer = em.createQuery("select c from Customer c where c.id=:id", Customer.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println(customer);

        System.out.println("================ List name ================");
        String name = em.createQuery("select c.name from Customer c where c.id=:id", String.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println(name);

        System.out.println("================ List personalized data ================");
        Object[] data = em.createQuery("select c.id, c.name, c.lastame from Customer c where c.id=:id", Object[].class)
                .setParameter("id", 2L)
                .getSingleResult();

        Long id = (Long) data[0];
        String name2 = (String) data[1];
        String lastame = (String) data[2];
        System.out.println("id=" + id + ", name=" + name2 + ", lastame=" + lastame);

        System.out.println("================ List of personalized data ================");
        List<Object[]> dataList = em.createQuery("select c.id, c.name, c.lastame from Customer c", Object[].class)
                .getResultList();
        dataList.forEach(arr -> System.out.println("id=" + arr[0] + ", name=" + arr[1] + ", lastame=" + arr[2]));

        System.out.println("================ Find customer and payment method ================");
        dataList = em.createQuery("select c, c.paymentMethod from Customer c", Object[].class)
                .getResultList();
        dataList.forEach(arr -> {
            Customer c = (Customer) arr[0];
            String paymentMethod = (String) arr[1];
            System.out.println(c + ", paymentMethod=" + paymentMethod);
        });

        System.out.println("================ Personalized clase query ================");
        customers = em.createQuery("select new Customer(c.name, c.lastame) from Customer c", Customer.class)
                .getResultList();
        customers.forEach(System.out::println);

        //DTO
        System.out.println("================ DTO ================");
        List<CustomerDto> customerDto = em.createQuery("select new org.example.hibernateapp.dto.CustomerDto(c.name, c.lastame) from Customer c", CustomerDto.class)
                .getResultList();
        customerDto.forEach(System.out::println);

        System.out.println("================ List names ================");
        List<String> names = em.createQuery("select c.name from Customer c", String.class)
                .getResultList();
        names.forEach(System.out::println);

        System.out.println("================ List distinct names ================");
        names = em.createQuery("select distinct(c.name) from Customer c", String.class)
                .getResultList();
        names.forEach(System.out::println);

        System.out.println("================ List unics payment method's ================");
        List<String> paymentMethods = em.createQuery("select distinct(c.paymentMethod) from Customer c", String.class)
                .getResultList();
        paymentMethods.forEach(System.out::println);

        System.out.println("================ List count unics payment method's ================");
        Long count = em.createQuery("select count(distinct(c.paymentMethod)) from Customer c", Long.class)
                .getSingleResult();
        System.out.println(count);

        System.out.println("================ List concat name and lastname ================");
//        List<String> fullName = em.createQuery("select concat(c.name, ' ', c.lastame) as fullName from Customer c", String.class)
//                .getResultList();
        List<String> fullName = em.createQuery("select c.name || ' ' || c.lastame as fullName from Customer c", String.class)
                .getResultList();
        fullName.forEach(System.out::println);

        System.out.println("================ List upper concat name and lastname ================");
        fullName = em.createQuery("select upper(concat(c.name, ' ', c.lastame)) as fullName from Customer c", String.class)
                .getResultList();
        fullName.forEach(System.out::println);

        System.out.println("================ List by name ================");
        customers = em.createQuery("select c from Customer c where c.name like :parameter", Customer.class)
                .setParameter("parameter", "%Lu%")
                .getResultList();
        customers.forEach(System.out::println);

        System.out.println("================ List by range ================");
        customers = em.createQuery("select c from Customer c where c.id between :min and :max", Customer.class)
                .setParameter("min", 1L)
                .setParameter("max", 4L)
                .getResultList();
        customers.forEach(System.out::println);

        System.out.println("================ List with order ================");
        customers = em.createQuery("select c from Customer c order by c.name desc", Customer.class)
                .getResultList();
        customers.forEach(System.out::println);

        System.out.println("================ Count all ================");
        Long total = em.createQuery("select count(c) as total from Customer c", Long.class)
                .getSingleResult();
        System.out.println(total);

        System.out.println("================ Min id ================");
        Long min = em.createQuery("select min(c.id) as min from Customer c", Long.class)
                .getSingleResult();
        System.out.println(min);

        System.out.println("================ Max id ================");
        Long max = em.createQuery("select max(c.id) as max from Customer c", Long.class)
                .getSingleResult();
        System.out.println(max);

        System.out.println("================ Name length ================");
        dataList = em.createQuery("select c.name, length(c.name) from Customer c", Object[].class)
                .getResultList();
        dataList.forEach(arr -> System.out.println("name=" + arr[0] + ", length=" + arr[1]));

        System.out.println("================ Find shortest name ================");
        Integer shortest = em.createQuery("select min(length(c.name)) from Customer c", Integer.class)
                .getSingleResult();
        System.out.println(shortest);

        System.out.println("================ Find longest name ================");
        Integer longest = em.createQuery("select max(length(c.name)) from Customer c", Integer.class)
                .getSingleResult();
        System.out.println(longest);

        System.out.println("================ Resume ================");
        Object[] resume = em.createQuery("select count(c), min(c.id), max(c.id), avg(c.id), sum(c.id) from Customer c", Object[].class)
                .getSingleResult();
        System.out.println("count=" + resume[0] + ", min=" + resume[1] + ", max=" + resume[2] + ", avg=" + resume[3] + ", sum=" + resume[4]);

        System.out.println("================ Shortest name and it's length ================");
        dataList = em.createQuery("select c.name, length(c.name) from Customer c where length(c.name) = (select min(length(c2.name)) from Customer c2)", Object[].class)
                .getResultList();
        dataList.forEach(arr -> System.out.println("name=" + arr[0] + ", length=" + arr[1]));

        System.out.println("================ Latest customer ================");
        customer = em.createQuery("select c from Customer c where c.id = (select max(c2.id) from Customer c2)", Customer.class)
                .getSingleResult();
        System.out.println(customer);

        System.out.println("================ Where in ================");
        customers = em.createQuery("select c from Customer c where c.id in (:ids)", Customer.class)
                .setParameter("ids", List.of(1L, 3L, 5L))
                .getResultList();
        customers.forEach(System.out::println);

        em.close();
    }
}
