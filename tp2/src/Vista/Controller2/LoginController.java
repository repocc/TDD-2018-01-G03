package tp2.src.Vista.Controller2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tp2.src.Model.MonitorSystem.User;

import java.io.IOException;

public class LoginController extends Controller {
    @FXML
    Text errorMessage;
    @FXML
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
            user = this.main.createUser(name);
        }
        if (main.userView.validUser(user)){
            errorMessage.setVisible(false);
            main.actualUser = user;
            this.main.setUserScene();
        } else{
            errorMessage.setVisible(true);
        }

    }
}
