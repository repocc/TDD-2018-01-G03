package tp2.src.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp2.src.Controller.Controller;
import tp2.src.Controller.Observer;
import tp2.src.Model.MonitorSystem.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main2 extends Application {

    public Stage stage;
    public MonitorSystem monitorSystem;
    public User actualUser;
    public Engine engine;
    public ArrayList<Rule> rules;
    public TicketsDealer ticketsDealer;
    public UserView userView;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("TDD Monitor System");
        this.stage = primaryStage;
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
        this.ticketsDealer = new TicketsDealer(engine, new Observer());
        primaryStage.show();
        this.showInitialStage();
    }

    public void showInitialStage() throws IOException {
        try {
            replaceSceneContent("initialStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Parent replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
        Parent page = (Parent) loader.load();
        Controller controller = loader.getController();
        controller.setMain(this);
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(page, 800, 600);
            scene.getStylesheets().add(getClass().getResource("demo.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public User createUser(String name) {
        return this.userView.createUser(name);
    }

    public void setUserScene() {
        this.userView.setScene();
    }

}
