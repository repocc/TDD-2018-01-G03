package tp2.src.Vista.Vista2;

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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminStageController extends UserController{
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

        if(admin.getOwnDashboards() != null){
            showDashboards(admin.getOwnDashboards());
        }
    }

    @Override
    public void showDashboards(List<Dashboard> dashboards) {

        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            DashboardButton dashboardButton = new DashboardButton(button, dashboard);
            dashboardButtons.add(dashboardButton);
            addButtonToPane(button, dashboardsPane);
        }

    }


    @Override
    public void showQueries(Dashboard dashboard) {

    }

    public void newDashboard() throws IOException {

        final Stage dialog = new Stage();
        dialog.setTitle("Add Dashboard");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(main.stage);
        Button add = new Button("Add");
        final TextField input = new TextField("Please Enter Dashboard Name");

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
                addDashboard(dashboardName);
                dialog.close();
            }
        });




    }

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

    }



    public void showDashboard(ActionEvent event) {
        Button button = (Button) event.getSource();
        String data = (String) button.getText();


    }

    private void showDashboardsButtons(List<DashboardButton> dashboardButtons) {
    }


    public void addButtonToPane(Button button, Pane pane){
        pane.getChildren().add(button);
    }


}
