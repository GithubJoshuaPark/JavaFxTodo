module com.soro.javafxex01 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens com.soro.javafxex01 to javafx.fxml; // For FXML files to access the package
    exports com.soro.javafxex01;
}