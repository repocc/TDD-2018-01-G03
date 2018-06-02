package tp2.src.Vista.Controller2;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Vista2.DashboardButton;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.QueriesdButton;
import tp2.src.Vista.Vista2.ViewDashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminStageController extends UserController {
    public Button addDashboardButton;
    private Admin admin;
    private List<DashboardButton> dashboardButtons;
    private List<QueriesdButton> queriesdButtons;



    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        this.dashboardButtons = new ArrayList<DashboardButton>();
        this.queriesdButtons = new ArrayList<QueriesdButton>();
        admin = (Admin) main.actualUser;
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
            button.setOnAction(new ShowDashboard(this));
            //DashboardButton dashboardButton = new DashboardButton(button, dashboard);
            listDashboard.getChildren().add(button);
            //addButtonToPane(button, dashboardsPane);
        }

    }



    public void showQueries(Dashboard dashboard) {

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
                dialog.close();
            }
        });




    }
/*
    public void addDashboard(String dashboardName){
        Dashboard dashboard = new Dashboard(dashboardName);
        admin.addDashboard(dashboard);
        Button button = new Button(dashboardName);
        button.setFocusTraversable(false);
        button.setPrefWidth(addDashboardButton.getPrefWidth());
        button.setPrefHeight(addDashboardButton.getPrefHeight());
        button.setStyle(addDashboardButton.getStyle());

        DashboardButton dashboardButton = new DashboardButton(button, dashboard);
        dashboardButtons.add(dashboardButton);
        addButtonToPane(button, dashboardsPane);

    }*/




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

