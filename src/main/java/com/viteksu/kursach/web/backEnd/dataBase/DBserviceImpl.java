package com.viteksu.kursach.web.backEnd.dataBase;

import com.viteksu.kursach.web.backEnd.accounts.Message;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

public class DBserviceImpl implements DBservice {
    private final SessionFactory factory = createFactory(getH2Configuration());

    @Override
    public void update(UserProfile userProfile) {
        Session session = factory.openSession();
        session.saveOrUpdate(userProfile);
        session.beginTransaction().commit();


        session.close();
    }

    @Override
    public long addUser(UserProfile userProfile) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        long id = (long) session.save(userProfile);
        transaction.commit();
        session.close();

        return id;
    }


    @Override
    public UserProfile getUserById(long id) {
        Session session = factory.openSession();

        UserProfile student = session.get(UserProfile.class, id);

        session.close();
        return student;
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    private SessionFactory createFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);
        configuration.addAnnotatedClass(Message.class);


        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        //        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        return configuration;
    }

}
