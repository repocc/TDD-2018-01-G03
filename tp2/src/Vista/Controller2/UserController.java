package tp2.src.Vista.Controller2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Model.MonitorSystem.Result;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.ViewDashboard;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class UserController extends Controller {


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
    @FXML
    public LineChart<String, Float> lineChart;
    protected String querySelected;
    public HashMap<String,CheckBox> queriesCheckBox;


    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        main.ticketsDealer.setUserController(this);
    }


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
                    this.updateQuerySelected();
                }
        );
    }

    public void updateQuerySelected(){
        if(querySelected != null){
            updateQuery(querySelected);
        }
    }

    public void updateQuery(String nameQuery) {
        Query query = this.dashboardSelected.getQuery(nameQuery);
        querySelected =query.getName();
        queryName.setText(query.getName());
        Float nbr = query.getLastResult();
        queryValue.setText(String.valueOf(nbr));
        updateChart(query);
    }

    private void updateChart(Query query) {
        lineChart.getData().clear();
        this.lineChart.getXAxis().setLabel("TimeLine");
        this.lineChart.getYAxis().setLabel("Query Value");

        lineChart.setTitle(query.getName());

        XYChart.Series series = new XYChart.Series();
        series.setName(query.getName());

        for (Result result: query.getResults()) {
            series.getData().add(new XYChart.Data(String.valueOf(result.dateTimeRecorded.getMinute()), result.value));
        }

        lineChart.getData().add(series);

    }

    public void selectDashboard(Dashboard dashboard){
        this.dashboardTittle.setText(dashboard.getName());
        queriesSelectedList.getChildren().clear();
        deselectedAllQueries();
        querySelected = null;
        queryName.setText("");
        queryValue.setText("");
        for(int i=0;i<dashboard.getQueries().size();i++){
            String name = dashboard.getQueries().get(i).getName();
            if(i==0){this.querySelected = name;}
            Query query = dashboard.getQueries().get(i);
            queriesCheckBox.get(name).setSelected(true);
            Button queryButton = new Button(name);
            queryButton.setPrefSize(200,31);
            queriesSelectedList.getChildren().add(queryButton);
            queryButton.setOnAction(new QueryController(this,query.getName()));

        }
        this.dashboardSelected = dashboard;
        this.updateQuerySelected();
    }

    public void updateCheckBoxList(){
        queriesCheckBox = new HashMap<String,CheckBox>();
        for(int i=0;i<main.queries.size();i++){
            String name = main.queries.get(i).getName();
            CheckBox checkbox = new CheckBox(name);
            checkbox.setMinWidth(500);
            checkbox.setStyle("-fx-background-color:  #ffebcd");
            queriesCheckBox.put(name,checkbox);
        }
    }

    public void deselectedAllQueries(){
        for(int i=0;i<main.queries.size();i++) {
            queriesCheckBox.get(main.queries.get(i).getName()).setSelected(false);
        }
    }

    public void getDashboards() {

        if(main.actualUser.getViewDashboards().size() > 0){
            showDashboards(main.actualUser.getViewDashboards());
        }
    }

    public double getDashboardWidth() {
        return this.dashboard.getWidth();
    }

    public double getDashboardHeight() {
        return this.dashboard.getWidth();
    }
}
