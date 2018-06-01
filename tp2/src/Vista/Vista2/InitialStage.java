package tp2.src.Vista.Vista2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class InitialStage extends StackPane {

    private Main2 mainApp;

    public InitialStage(Main2 mainApp) throws IOException {
        this.mainApp = mainApp;
        Parent root = FXMLLoader.load(getClass().getResource("initialStage.fxml"));
        mainApp.stage.setScene(new Scene(root,500,500));
    }

    public void openAssociatedStage(ActionEvent actionEvent) {
        System.out.println("ASOC");
    }

    public void openAdminStage(ActionEvent actionEvent) {
        System.out.println("ADMIN");

    }
}
