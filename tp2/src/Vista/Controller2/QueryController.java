package tp2.src.Vista.Controller2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class QueryController implements EventHandler<ActionEvent> {


    private UserController userController;
    private String nameQuery;

    public QueryController(UserController userController, String nameQuery) {
        this.userController= userController;
        this.nameQuery = nameQuery;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.userController.updateQuery(nameQuery);

    }
}
