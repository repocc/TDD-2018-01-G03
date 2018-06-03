package tp2.src.Controller;

public class Observer {
    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    public void updateView(){
        this.userController.updateView();
    }
}
