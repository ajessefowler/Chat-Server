package com.chatapi.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class ApplicationTest {
    public static void main(String[] args) {
        User user1 = new User("jesse", "test1");
        User user2 = new User("emma", "test2");
        addUser(user1);
        addUser(user2);
        Date date = new Date();
        Message message1 = new Message(user1, user2, date, "test message");
        addMessage(message1);
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class).addAnnotatedClass(Message.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static void addUser(User user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created user " + user.getUsername());
    }

    public static void addMessage(Message message) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(message);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created message to " + message.getRecipient().getUsername() + ": " + message.getContent());
    }
}
