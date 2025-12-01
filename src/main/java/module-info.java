module org.livrariajpa {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.livrariajpa to javafx.fxml;
    exports org.livrariajpa;
}