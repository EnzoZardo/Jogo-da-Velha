module com.example.jogo_da_velha {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.jogo_da_velha to javafx.fxml;
    exports com.example.jogo_da_velha;
}