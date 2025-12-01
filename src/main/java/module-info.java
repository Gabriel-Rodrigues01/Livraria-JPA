module org.livrariajpa {
    // Requisitos
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires org.postgresql.jdbc;


    exports org.view;
    opens org.model to org.hibernate.orm.core, javafx.base;
    opens org.view to javafx.fxml;
}