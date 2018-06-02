package tp2.src.Vista.Vista2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.Engine;
import tp2.src.Model.MonitorSystem.MonitorSystem;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;
import tp2.src.Model.MonitorSystem.TicketsDealer;
import tp2.src.Model.MonitorSystem.User;
import tp2.src.Vista.Controller2.Controller;

import java.io.IOException;

public class Main2 extends Application {

    public Stage stage;
    public MonitorSystem monitorSystem;
    public User actualUser;
    private Engine engine;
    private TicketsDealer ticketsDealer;
    private SimpleHttpServer server;
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
        this.ticketsDealer = new TicketsDealer(engine);

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
