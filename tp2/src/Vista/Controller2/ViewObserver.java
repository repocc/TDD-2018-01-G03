package tp2.src.Vista.Controller2;

public class ViewObserver {
    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    public void updateView(){
        this.userController.updateView();
    }
}
