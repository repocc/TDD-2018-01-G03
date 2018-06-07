package tp2.src.View;

import tp2.src.Model.MonitorSystem.User;

public abstract class UserView {

    public Main main;

    public  UserView(Main main){
        this.main = main;
    }



    public abstract User createUser(String name);

    public abstract void setScene();

    public abstract boolean validUser(User user);
}
