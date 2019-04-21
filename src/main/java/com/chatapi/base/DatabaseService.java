package com.chatapi.base;

import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.authentication.models.UserCredentials;
import com.chatapi.base.models.Conversation;
import com.chatapi.base.models.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;

// TODO - Make this class less disgusting

public class DatabaseService {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(UserCredentials.class).addAnnotatedClass(User.class).addAnnotatedClass(Conversation.class).addAnnotatedClass(Message.class).addAnnotatedClass(Token.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public User getUser(String username) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        Predicate condition = cb.equal(root.get("username"), username);
        cr.select(root).where(condition);
        Query<User> query = session.createQuery(cr);
        return query.setMaxResults(1).uniqueResult();
    }

    public Conversation getConversation(int groupId) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Conversation> cr = cb.createQuery(Conversation.class);
        Root<Conversation> root = cr.from(Conversation.class);
        Predicate condition = cb.equal(root.get("id"), groupId);
        cr.select(root).where(condition);
        Query<Conversation> query = session.createQuery(cr);
        return query.setMaxResults(1).uniqueResult();
    }

    public Token getToken(User user) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Token> cr = cb.createQuery(Token.class);
        Root<Token> root = cr.from(Token.class);
        Predicate condition = cb.equal(root.get("user"), user);
        cr.select(root).where(condition);
        Query<Token> query = session.createQuery(cr);
        return query.setMaxResults(1).uniqueResult();
    }

    public Token getTokenByTokenString(String token) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Token> cr = cb.createQuery(Token.class);
        Root<Token> root = cr.from(Token.class);
        Predicate condition = cb.equal(root.get("token"), token);
        cr.select(root).where(condition);
        Query<Token> query = session.createQuery(cr);
        return query.setMaxResults(1).uniqueResult();
    }

    public Session deleteToken(Token token) {
        User user = token.getUser();
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(token);
        session.getTransaction().commit();
        System.out.println("Deleted token for user " + user.getUsername());
        return session;
    }

    public void deleteUser(User user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Deleted user " + user.getUsername());
    }

    public void deleteUserByUsername(String username) {
        User user = getUser(username);
        deleteUser(user);
    }

    public void addConversation(Conversation conversation) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(conversation);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created new conversation with id: " + conversation.getId());
    }

    public void updateConversation(Conversation conversation) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(conversation);
        session.getTransaction().commit();
        session.close();
        System.out.println("Updated conversation with id: " + conversation.getId());
    }

    public void addToken(Token token) {
        User user = token.getUser();
        String username = user.getUsername();
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(token);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created token for user " + username);
    }

    public void addUser(User user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created user " + user.getUsername());
    }

    public void updateUser(User user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Updated user " + user.getUsername());
    }

    public void addMessage(Message message) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(message);
        session.getTransaction().commit();
        session.close();
        //System.out.println("Created message to group " + message.getConversation().getId() + ": " + message.getContent());
    }
}
