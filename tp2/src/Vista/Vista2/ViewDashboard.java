package tp2.src.Vista.Vista2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Controller2.UserController;

public class ViewDashboard extends VBox {

    private UserController userController;

    public ViewDashboard(UserController userController, Dashboard dashboard) {
        this.userController = userController;

        this.setMinSize(userController.getDashboardWidth(),userController.getDashboardHeight());
        this.setSpacing(20);


        HBox total = new HBox();
        total.setSpacing(10);
        Label totalTks = new Label("Total Tickets:");
        totalTks.setFont(Font.font("Cambria", 15));
        totalTks.setAlignment(Pos.CENTER_LEFT);
        Label totalnbr = new Label("15"); //poner numero real
        totalnbr.setFont(Font.font("Cambria",FontWeight.BOLD, 15));
        totalnbr.setAlignment(Pos.CENTER_LEFT);
        total.getChildren().addAll(totalTks,totalnbr);

        HBox open = new HBox();
        open.setSpacing(10);
        Label openTks = new Label("Open:");
        openTks.setFont(Font.font("Cambria", 15));
        openTks.setAlignment(Pos.CENTER_LEFT);
        Label openNbr = new Label("10");
        openNbr.setFont(Font.font("Cambria",FontWeight.BOLD, 15));
        openNbr.setAlignment(Pos.CENTER_LEFT);
        open.getChildren().addAll(openTks,openNbr);

        HBox close = new HBox();
        open.setSpacing(10);
        Label closeTks = new Label("Total Tickets:");
        closeTks.setFont(Font.font("Cambria", 15));
        closeTks.setAlignment(Pos.CENTER_LEFT);
        Label closeNbr = new Label("5");
        closeNbr.setFont(Font.font("Cambria",FontWeight.BOLD, 15));
        closeNbr.setAlignment(Pos.CENTER_LEFT);
        close.getChildren().addAll(closeTks,closeNbr);



        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Open tickets", 90),
                        new PieChart.Data("Close tickets", 10));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Tickets Status");

        getChildren().addAll(total,open,close,chart);



    }
}
