package tp2.src.Vista;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.*;
import tp2.src.Model.MonitorSystem.Exceptions.NotFoundException;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;

import java.time.Duration;
import java.util.HashMap;

public class Main extends Application {
    private Stage stage;
    private MonitorSystem monitorSystem;
    private Engine engine;
    private TicketsDealer ticketsDealer;
    private SimpleHttpServer server;
    private HashMap<String,Query> queries;
    private Admin admin;
    private Associated assoc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("TDD Monitoreo de Tickets");
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
        this.ticketsDealer = new TicketsDealer(engine);
        this.server  = new SimpleHttpServer(ticketsDealer);

        try {
            Rule rule0 = this.monitorSystem.getRule("open-count");
            Rule rule1 = this.monitorSystem.getRule("close-count");
            Rule rule2 = this.monitorSystem.getRule("open-actual-count");
            Rule rule3 = this.monitorSystem.getRule("open-fraction");


            Query query0 = new Query("open-count", Duration.ofSeconds(10), "open-count");
            Query query1 = new Query("close-count", Duration.ofSeconds(10), "close-count");
            Query query2 = new Query("open-actual-count", Duration.ofSeconds(10), "open-actual-count");
            Query query3 = new Query("open-fraction", Duration.ofSeconds(10), "open-fraction");
            this.queries = new HashMap<String,Query>();
            this.queries.put("open-count",query0);
            this.queries.put("close-count",query1);
            this.queries.put("open-actual-count",query2);
            this.queries.put("open-fraction",query3);


        }catch(NotFoundException e) {
            e.printStackTrace();

        }
        this.admin = new Admin("Admin 1", this.monitorSystem);
        this.monitorSystem.addUser(this.admin);
        this.assoc = new Associated("Assoc",this.monitorSystem);
        this.monitorSystem.addUser(this.assoc);
        this.mostrarPantallaDeInicio();
    }

    public void mostrarPantallaDeInicio(){
        VistaInicial vistaInicial = new VistaInicial(this);
        final Group group = new Group(vistaInicial);
        group.setLayoutX(370);
        group.setLayoutY(100);
        setearScene(group);
        stage.show();

    }
    private void setearScene(Group group){
        Scene scene = new Scene(group,1200, 800);
        scaleScene(group,scene);
        stage.setScene(scene);

    }


    public void scaleScene(Group group, Scene scene){
        Scale scale = new Scale();
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        scale.xProperty().bind(scene.widthProperty().divide(initWidth));
        scale.yProperty().bind(scene.heightProperty().divide(initHeight));
        scale.setPivotX(0);
        scale.setPivotY(0);
        group.getTransforms().addAll(scale);


    }

    public void loginAdmin(){



        VistaAdmin vistaAdmin = new VistaAdmin(this.admin,this.queries,this,monitorSystem);
        final Group group = new Group(vistaAdmin);
        setearScene(group);
        //stage.setFullScreen(true);
    }


    public void loginAsoc() {



        VistaAssoc vistaAssoc = new VistaAssoc(assoc,this,this.monitorSystem);
        final Group group = new Group(vistaAssoc);
        setearScene(group);
    }
}
