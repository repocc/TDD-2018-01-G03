package tp2.src.Vista.Vista2;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Model.MonitorSystem.Query;

public class CountView extends HBox {

    public CountView(Query query){

        this.setSpacing(10);
        Label totalTks = new Label(query.getName()+":");
        totalTks.setFont(Font.font("Cambria", 15));
        totalTks.setAlignment(Pos.CENTER_LEFT);
        Float nbr = query.getLastResult();
        Label totalnbr = new Label(String.valueOf(nbr));
        totalnbr.setFont(Font.font("Cambria",FontWeight.BOLD, 15));
        totalnbr.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(totalTks,totalnbr);
    }
}
