package tp2.src.Controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Vista.Main;

public class LoginAssoc implements EventHandler<ActionEvent> {

    private Main mainApp;


    public LoginAssoc(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.mainApp.loginAsoc();
    }
}
