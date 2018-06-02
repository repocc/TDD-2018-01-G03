package tp2.src.Vista.Controller2;

import javafx.scene.control.TextField;
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
        System.out.println(name);
        User user = main.monitorSystem.getUser(name);
        if (user == null){
            System.out.println("Deberia entrar 1 vez");
            user = this.main.createUser(name);
        }
        main.actualUser = user;
        this.main.setUserScene();
    }
}
