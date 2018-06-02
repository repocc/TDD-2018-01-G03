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
import tp2.src.Controlador.LoginAssoc;

public class VistaInicial extends StackPane {

    private Main mainApp;
    private ImageView logo;

    public VistaInicial(Main mainApp){
        this.mainApp = mainApp;
        this.setId("vistaInicial");
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20);
        HBox contenedorDeBotones = new HBox();

        Text title = new Text("LOGIN AS");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35));

        Image imgLogo = new Image("tp2/src/Vista/inicio.png");
        logo = new ImageView(imgLogo);
        logo.getStyleClass().add("imgLogo");
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);

        Button botonAdmin = new Button("Administrador");
        botonAdmin.setMinSize(200,100);
        botonAdmin.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        botonAdmin.setId("admin");
        botonAdmin.setAlignment(Pos.CENTER);
        botonAdmin.setOnAction(new LoginAdmin(mainApp));

        Button botonAsoc = new Button("Asociado");
        botonAsoc.setMinSize(200,100);
        botonAsoc.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        botonAsoc.setId("asoc");
        botonAsoc.setAlignment(Pos.CENTER);
        botonAsoc.setOnAction(new LoginAssoc(mainApp));

        contenedorDeBotones.setSpacing(100);
        contenedorDeBotones.setAlignment(Pos.CENTER);
        contenedorDeBotones.getChildren().addAll(botonAdmin, botonAsoc);

        centerBox.getChildren().addAll(logo,title,contenedorDeBotones);
        centerBox.setId("centerBox");

        this.getChildren().add(centerBox);

    }
}
