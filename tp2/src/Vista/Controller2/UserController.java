package tp2.src.Vista.Controller2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.ViewDashboard;

import java.io.IOException;
import java.util.List;

public abstract class UserController extends Controller {

    //public Button logoutButton;
    public Dashboard dashboardSelected;
    @FXML
    public Label dashboardTittle;
    @FXML
    public VBox listDashboard;
    @FXML
    public Pane dashboard;

    @FXML
    public VBox queriesSelectedList;

    @FXML
    public Label queryName;
    @FXML
    public Label queryValue;
    public LineChart lineChart;
    protected String querySelected;



    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        main.ticketsDealer.setUserController(this);
    }

    public abstract double getDashboardWidth();

    public abstract double getDashboardHeight();

    public abstract void selectDashboard(Dashboard dashboard);

    public void showDashboards(List<Dashboard> dashboards) {
        listDashboard.getChildren().clear();
        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            button.setPrefSize(207,47);
            button.setMnemonicParsing(false);
            button.setStyle("-fx-background-color: #d3d3d3;");
            button.setOnAction(new ShowDashboard(this,dashboard));
            listDashboard.getChildren().add(button);
        }

    }

    public void logout()  {
        try {
            this.main.showInitialStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDashboardPage(ViewDashboard viewDashboard){
        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(viewDashboard);
    }

    public void updateDashboardQueriesValues(){
        this.setDashboardPage(new ViewDashboard(this,this.dashboardSelected));
    }



    public void updateView() {

        Platform.runLater(
                () -> {
                    this.dashboardTittle.setText("ENTREE");
                    this.updateQuerySelected();
                }
        );
    }

    public void updateQuerySelected(){
        if(querySelected != null) {
            Query query = this.dashboardSelected.getQuery(querySelected);
            queryName.setText(querySelected);
            Float nbr = query.getLastResult();
            queryValue.setText(String.valueOf(nbr));
        }
    }

    public void updateQuery(String nameQuery) {
        Query query = this.dashboardSelected.getQuery(nameQuery);
        querySelected =query.getName();
        queryName.setText(query.getName());
        Float nbr = query.getLastResult();
        queryValue.setText(String.valueOf(nbr));
    }
}
