package tp2.src.Vista.Controller2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.QueriesdButton;
import tp2.src.Vista.Vista2.ViewDashboard;

import java.io.IOException;
import java.util.List;

public class AdminStageController extends UserController {
    public Button addDashboardButton;
    private Admin admin;
    @FXML
    private VBox queriesList;
    @FXML
    private Button saveQueries;

    private List<QueriesdButton> queriesdButtons;



    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        //this.dashboardButtons = new ArrayList<DashboardButton>();
        //this.queriesdButtons = new ArrayList<QueriesdButton>();
        admin = (Admin) main.actualUser;
        dashboardSelected = null;
        getDashboards();

    }


    public void getDashboards() {

        if(main.actualUser.getViewDashboards().size() > 0){
            showDashboards(main.actualUser.getViewDashboards());
        }
    }

    @Override
    public void showDashboards(List<Dashboard> dashboards) {
        listDashboard.getChildren().clear();
        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            button.prefWidth(200);
            button.setMinWidth(200);
            button.setMnemonicParsing(false);
            button.setStyle("-fx-background-color: #d3d3d3;");
            button.setOnAction(new ShowDashboard(this, dashboard));
            //DashboardButton dashboardButton = new DashboardButton(button, dashboard);
            listDashboard.getChildren().add(button);
            //addButtonToPane(button, dashboardsPane);
        }


    }

    public void selectDashboard(Dashboard dashboard){
        this.dashboardSelected = dashboard;
        this.setDashboardPage(new ViewDashboard(this,dashboard));
    }



    public void addQuery() throws Exception {
        if(this.dashboardSelected != null) {
            System.out.println("hola");
            final Stage dialog = new Stage();
            dialog.setTitle("Edit Queries");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(main.stage);
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("editQueries.fxml"), null, new JavaFXBuilderFactory());
            Parent page = (Parent) loader.load();
            dialog.setScene(new Scene(page, 500, 500));
            dialog.show();
            saveQueries.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    //modificar las queries
                    reloadDashboardView();
                    dialog.close();
                }
            });
        }

    }

    public void reloadDashboardView(){

    }


    public void newDashboard() throws IOException {

        final Stage dialog = new Stage();
        dialog.setTitle("Add Dashboard");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(main.stage);
        Button add = new Button("Add");
        final TextField input = new TextField("Dashboard Name");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(input,0,0);
        grid.add(add,0,1);
        dialog.setScene(new Scene(grid));
        dialog.show();

        add.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String dashboardName = input.getText();
                Dashboard dashboard = new Dashboard(dashboardName);
                admin.addDashboard(dashboard);
                getDashboards();
                selectDashboard(dashboard);
                dialog.close();
            }
        });




    }



    public void addButtonToPane(Button button, Pane pane){
        pane.getChildren().add(button);
    }

    public void setDashboardPage(ViewDashboard viewDashboard){
        this.dashboard.getChildren().add(viewDashboard);
    }

    public double getDashboardWidth() {
        return this.dashboard.getWidth();
    }

    public double getDashboardHeight() {
        return this.dashboard.getWidth();
    }
    @Override
    public void logout()  {
        try {
            this.main.showInitialStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

