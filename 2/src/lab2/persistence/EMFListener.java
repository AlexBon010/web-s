package lab2.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class EMFListener implements ServletContextListener {

    public static final String ATTR_EMF = "lab2.entityManagerFactory";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = System.getenv("DB_URL");
        if (url == null || url.isEmpty()) {
            url = System.getProperty("db.url", "jdbc:postgresql://localhost:5432/lab2");
        }
        String user = System.getenv("DB_USER");
        if (user == null) user = System.getProperty("db.user", "lab2");
        String password = System.getenv("DB_PASSWORD");
        if (password == null) password = System.getProperty("db.password", "lab2");

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.url", url);
        props.put("javax.persistence.jdbc.user", user);
        props.put("javax.persistence.jdbc.password", password);
        props.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "update");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab2PU", props);
        sce.getServletContext().setAttribute(ATTR_EMF, emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute(ATTR_EMF);
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
