package tp2.src.Vista;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;

public class Main extends Application {
    private Stage stage;
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
        setearScene(group);
        stage.show();

    }
    private void setearScene(Group group){
        Scene scene = new Scene(group,400, 600);
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


}
