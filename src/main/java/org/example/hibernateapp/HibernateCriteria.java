package org.example.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.example.hibernateapp.entity.Customer;
import org.example.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateCriteria {
    public static void main(String[] args) {
        System.out.println("Hello Hibernate Criteria!");
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder criteria = em.getCriteriaBuilder();

        CriteriaQuery<Customer> query = criteria.createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);
        query.select(from);

        List<Customer> customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== List where equals ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        ParameterExpression<String> name = criteria.parameter(String.class, "name");
        query.select(from).where(criteria.equal(from.get("name"), name));

        customers = em.createQuery(query).setParameter("name", "Mai").getResultList();
        customers.forEach(System.out::println);


        System.out.println("=============== List where like ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        ParameterExpression<String> nameLike = criteria.parameter(String.class, "nameLike");
        query.select(from).where(criteria.like(criteria.upper(from.get("name")), criteria.upper(nameLike)));
        customers = em.createQuery(query).setParameter("nameLike", "%i%").getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== List where between ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        query.select(from).where(criteria.between(from.get("id"), 2, 5));
        customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== List where in ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        ParameterExpression<List> listParameter = criteria.parameter(List.class, "lastnames");
        query.select(from).where(from.get("lastname").in(listParameter));
        customers = em.createQuery(query).setParameter("lastnames", List.of("Scarlet", "Tokisaki")).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== List greater than ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        query.select(from).where(criteria.greaterThan(from.get("id"), 3));
        customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        query.select(from).where(criteria.gt(criteria.length(from.get("name")), 4));
        customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== And y Or ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        Predicate byName = criteria.equal(from.get("name"), "Mai");
        Predicate byPayment = criteria.equal(from.get("paymentMethod"), "Credit");
        Predicate predicateId = criteria.gt(from.get("id"), 2);
        query.select(from).where(criteria.and(criteria.or(byName, byPayment), predicateId));
        customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== Order by ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        query.select(from).orderBy(criteria.asc(from.get("name")), criteria.desc(from.get("lastname")));
        customers = em.createQuery(query).getResultList();
        customers.forEach(System.out::println);

        System.out.println("=============== find by ===============");
        query = criteria.createQuery(Customer.class);
        from = query.from(Customer.class);
        ParameterExpression<Long> idParam = criteria.parameter(Long.class, "id");
        query.select(from).where(criteria.equal(from.get("id"), idParam));
        Customer customer = em.createQuery(query).setParameter("id", 3L).getSingleResult();
        System.out.println(customer);

        System.out.println("=============== List names ===============");
        CriteriaQuery<String> queryString = criteria.createQuery(String.class);
        from = queryString.from(Customer.class);
        queryString.select(from.get("name"));
        List<String> names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("=============== List distinct names ===============");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(Customer.class);
        queryString.select(criteria.upper(from.get("name"))).distinct(true);
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("=============== List names and lastnames ===============");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(Customer.class);
        queryString.select(criteria.concat(criteria.concat(from.get("name"), " "), from.get("lastname")));
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("=============== List upper names and lastnames ===============");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(Customer.class);
        queryString.select(criteria.upper(criteria.concat(criteria.concat(from.get("name"), " "), from.get("lastname"))));
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("=============== List personalized columns ===============");
        CriteriaQuery<Object[]> queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Customer.class);
        queryObject.multiselect(from.get("id"),from.get("name"), from.get("lastname"));
        List<Object[]> objects = em.createQuery(queryObject).getResultList();
        objects.forEach(object -> System.out.println(object[0] + " " + object[1] + " " + object[2]));

        System.out.println("=============== List personalized columns with where ===============");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Customer.class);
        queryObject.multiselect(from.get("id"),from.get("name"), from.get("lastname")).where(criteria.like(from.get("name"), "%i%"));
        objects = em.createQuery(queryObject).getResultList();
        objects.forEach(object -> System.out.println(object[0] + " " + object[1] + " " + object[2]));

        System.out.println("=============== List personalized columns with where id ===============");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Customer.class);
        queryObject.multiselect(from.get("id"),from.get("name"), from.get("lastname")).where(criteria.equal(from.get("id"), 3L));
        Object[] object = em.createQuery(queryObject).getSingleResult();
        System.out.println(object[0] + " " + object[1] + " " + object[2]);

        System.out.println("=============== Count ===============");
        CriteriaQuery<Long> queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Customer.class);
        queryLong.select(criteria.count(from));
        Long count = em.createQuery(queryLong).getSingleResult();
        System.out.println("Count: " + count);

        System.out.println("=============== Sum ===============");
        queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Customer.class);
        queryLong.select(criteria.sum(from.get("id")));
        Long sum = em.createQuery(queryLong).getSingleResult();
        System.out.println("Sum: " + sum);

        System.out.println("=============== Max ===============");
        queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Customer.class);
        queryLong.select(criteria.max(from.get("id")));
        Long max = em.createQuery(queryLong).getSingleResult();
        System.out.println("Max: " + max);

        System.out.println("=============== Min ===============");
        queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(Customer.class);
        queryLong.select(criteria.min(from.get("id")));
        Long min = em.createQuery(queryLong).getSingleResult();
        System.out.println("Min: " + min);

        System.out.println("=============== Various results ===============");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(Customer.class);
        queryObject.multiselect(criteria.count(from), criteria.sum(from.get("id")), criteria.max(from.get("id")), criteria.min(from.get("id")));
        object = em.createQuery(queryObject).getSingleResult();
        System.out.println("Count: " + object[0] + " Sum: " + object[1] + " Max: " + object[2] + " Min: " + object[3]);

        em.close();
    }
}
