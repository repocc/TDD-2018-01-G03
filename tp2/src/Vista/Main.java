package tp2.src.Vista;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import tp2.src.Model.MonitorSystem.*;

public class Main extends Application {
    private Stage stage;
    private MonitorSystem monitorSystem;
    private Engine engine;
    private TicketsDealer ticketsDealer;
    private TicketTranslator tickeySysyemG3Traslator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("TDD Monitoreo de Tickets");
        //Application.setUserAgentStylesheet(getClass().getResource("css/main.css").toExternalForm());
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

        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
        this.ticketsDealer = new TicketsDealer(engine);
        this.tickeySysyemG3Traslator = new TicketSystemG3Translator(ticketsDealer);


        Admin admin = new Admin("U1", this.monitorSystem);
        this.monitorSystem.addUser(admin);
        admin.addDashboard(new Dashboard("D1"));

        VistaAdmin vistaAdmin = new VistaAdmin(this,monitorSystem);
        final Group group = new Group(vistaAdmin);
        setearScene(group);
        //stage.setFullScreen(true);
    }

    public Stage getStage(){
        return this.stage;
    }


}
