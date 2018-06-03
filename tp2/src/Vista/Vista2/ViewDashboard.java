package tp2.src.Vista.Vista2;

import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Vista.Controller2.UserController;

public class ViewDashboard extends VBox {

    private UserController userController;

    public ViewDashboard(UserController userController, Dashboard dashboard) {
        this.userController = userController;

        this.setMinSize(userController.getDashboardWidth(),userController.getDashboardHeight());
        this.setSpacing(20);
        getChildren().clear();
        for(Query query: dashboard.getQueries()){
            getChildren().add(new CountView(query));
        }


        //ObservableList<PieChart.Data> pieChartData =
        //        FXCollections.observableArrayList(
        //                new PieChart.Data("Open tickets", 90),
        //                new PieChart.Data("Close tickets", 10));

        //final PieChart chart = new PieChart(pieChartData);
        //chart.setTitle("Tickets Status");

        //getChildren().addAll(total,open,close,chart);



    }
}
