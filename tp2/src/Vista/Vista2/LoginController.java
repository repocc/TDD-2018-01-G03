package tp2.src.Vista.Vista2;

import javafx.scene.control.TextField;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.User;

public class LoginController extends Controller {


    public TextField username;

    public void login(){

        User admin = new Admin(username.getText(), main.monitorSystem);
        main.monitorSystem.addUser(admin);
        main.actualUser = admin;
        try {
            this.main.replaceSceneContent("AdminStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
