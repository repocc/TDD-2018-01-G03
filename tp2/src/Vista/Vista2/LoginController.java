package tp2.src.Vista.Vista2;

import javafx.scene.control.TextField;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.User;

import java.io.IOException;

public class LoginController extends Controller {


    public TextField username;

    public void back(){
        try {
            this.main.showInitialStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(){
        String name = username.getText();
        User admin = main.monitorSystem.getUser(name);
        if (admin == null){
            admin = new Admin(name, main.monitorSystem);
            main.monitorSystem.addUser(admin);
        }
        main.actualUser = admin;
        try {
            this.main.replaceSceneContent("AdminStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
