package tp2.src.Vista.Vista2;

import tp2.src.Model.MonitorSystem.User;

public abstract class UserView {

    public Main2 main;

    public  UserView(Main2 main){
        this.main = main;
    }



    public abstract User createUser(String name);

    public abstract void setScene();

    public abstract boolean validUser(User user);
}
