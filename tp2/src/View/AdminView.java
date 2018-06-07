package tp2.src.View;

import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.User;

public class AdminView extends UserView{

    public AdminView(Main main) {
        super(main);
    }


    @Override
    public User createUser(String name) {
        Admin admin = new Admin(name, main.monitorSystem);
        main.monitorSystem.addUser(admin);
        return admin;

    }

    @Override
    public void setScene() {
        try {
            this.main.replaceSceneContent("AdminStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean validUser(User user){
           return user.logAsAdmin();
    }
}
