module projectdi {
    requires javafx.controls;
    requires javafx.fxml;
    requires Saxon.HE;
    requires java.logging;
    requires jdom2;
    requires java.xml;

    opens projectdi to javafx.fxml;
    exports projectdi;
}