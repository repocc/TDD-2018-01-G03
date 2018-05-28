package tp2.src.Vista;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import tp2.src.Controlador.LoginAdmin;

public class VistaInicial extends StackPane {

    private Main mainApp;
    private ImageView logo;

    public VistaInicial(Main mainApp){
        this.mainApp = mainApp;
        this.setId("vistaInicial");
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        HBox contenedorDeTitulo = new HBox();
        HBox contenedorDeBotones = new HBox();

        VBox vbox = new VBox();
        //vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        Text title = new Text("LOGIN AS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        vbox.getChildren().add(title);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinHeight(100);

        Image imgLogo = new Image("tp2/src/Vista/inicio.png");
        logo = new ImageView(imgLogo);

        logo.getStyleClass().add("imgLogo");
        logo.setFitWidth(400);
        logo.setPreserveRatio(true);

        contenedorDeTitulo.getChildren().add(logo);
        Button botonAdmin = new Button("Administrador");
        botonAdmin.setId("admin");
        botonAdmin.setAlignment(Pos.CENTER_LEFT);
        botonAdmin.setOnAction(new LoginAdmin(mainApp));

        Button botonAsoc = new Button("Asociado");
        botonAsoc.setId("asoc");
        botonAsoc.setAlignment(Pos.CENTER_RIGHT);
        //botonSalir.setOnAction(new SalirJuego(mainApp));
        contenedorDeBotones.setSpacing(100);
        contenedorDeBotones.setAlignment(Pos.CENTER);
        contenedorDeBotones.getChildren().addAll(botonAdmin, botonAsoc);
        centerBox.getChildren().addAll(contenedorDeTitulo,vbox,contenedorDeBotones);
        centerBox.setId("centerBox");

        this.getChildren().add(centerBox);

    }
}
