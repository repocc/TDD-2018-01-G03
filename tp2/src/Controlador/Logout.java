package tp2.src.Controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Vista.Main;

public class Logout implements EventHandler<ActionEvent> {
    private Main mainApp;


    public Logout(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.mainApp.mostrarPantallaDeInicio();
    }

}
