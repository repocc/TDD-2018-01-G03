package tp2.src.Vista.Vista2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.*;
import tp2.src.Model.MonitorSystem.Exceptions.NotFoundException;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;
import tp2.src.Vista.Controller2.Controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class Main2 extends Application {

    public Stage stage;
    public MonitorSystem monitorSystem;
    public User actualUser;
    public Engine engine;
    public ArrayList<Query> queries;
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
        try {
            Rule rule0 = this.monitorSystem.getRule("open-count");
            Rule rule1 = this.monitorSystem.getRule("close-count");
            Rule rule2 = this.monitorSystem.getRule("open-actual-count");
            Rule rule3 = this.monitorSystem.getRule("open-fraction");


            Query query0 = new Query("open-count", Duration.ofSeconds(10), "open-count");
            Query query1 = new Query("close-count", Duration.ofSeconds(10), "close-count");
            Query query2 = new Query("open-actual-count", Duration.ofSeconds(10), "open-actual-count");
            Query query3 = new Query("open-fraction", Duration.ofSeconds(10), "open-fraction");
            this.queries = new ArrayList<Query>();
            this.queries.add(query0);
            this.queries.add(query1);
            this.queries.add(query2);
            this.queries.add(query3);


        }catch(NotFoundException e) {
            e.printStackTrace();

        }
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

    public Query getQuery(String name){
        for(int i=0 ; i<queries.size();i++){
            if(queries.get(i).getName().equals(name)){
                return queries.get(i);
            }
        }
        return null;
    }
}
