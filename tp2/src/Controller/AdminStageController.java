package tp2.src.Controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Rule;
import tp2.src.View.Main;

import java.io.IOException;

public class  AdminStageController extends UserController {
    public Button addDashboardButton;
    private Admin admin;

    @Override
    public void setMain(Main main) {
        super.setMain(main);
        updateCheckBoxList();
        admin = (Admin) main.actualUser;
        dashboardSelected = null;
        getDashboards();

    }



    public void addQuery() throws Exception {
        if(this.dashboardSelected != null) {

            final Stage dialog = new Stage();
            dialog.setTitle("Edit Queries");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(main.stage);
            dialog.setWidth(500);
            dialog.setHeight(225);
            dialog.setResizable(false);

            SplitPane sPane = new SplitPane();
            sPane.setStyle("-fx-background-color: white;");
            sPane.setDividerPosition(2,0);
            sPane.setPrefSize(200,200);
            sPane.setOrientation(Orientation.VERTICAL);

            VBox queriesList = new VBox();
            queriesList.setPrefWidth(200);
            queriesList.setPrefHeight(200);
            for(Rule rule : main.monitorSystem.getRules()){
                CheckBox checkbox = queriesCheckBox.get(rule.getName());
                checkbox.setPrefWidth(200);
                checkbox.setPrefHeight(40);
                queriesList.getChildren().add(checkbox);
            }

            Button save = new Button("SAVE");
            save.setPrefSize(500,36);
            save.setStyle("-fx-background-color: white;");
            save.setTextAlignment(TextAlignment.CENTER);

            sPane.getItems().addAll(queriesList,save);
            TitledPane tPane = new TitledPane("Select Queries",sPane);
            tPane.setCollapsible(false);
            tPane.setPrefSize(200,200);
            tPane.setStyle("-fx-background-color: white;");

            dialog.setScene(new Scene(tPane));
            dialog.show();
            save.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    updateQueriesDashboard();
                    selectDashboard(dashboardSelected);
                    dialog.close();
                }
            });
        }

    }

    public void updateQueriesDashboard(){

        for(Rule rule : main.monitorSystem.getRules()){
            String name = rule.getName();

            if(this.dashboardSelected.hasQuery(name) && !queriesCheckBox.get(name).isSelected()){
                this.dashboardSelected.removeQuery(name);
            }
            if(!this.dashboardSelected.hasQuery(name) && queriesCheckBox.get(name).isSelected()){
                this.dashboardSelected.addQuery(name);
            }

        }
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

}

