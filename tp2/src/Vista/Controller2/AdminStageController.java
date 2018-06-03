package tp2.src.Vista.Controller2;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Vista.Vista2.Main2;

import java.io.IOException;
import java.util.HashMap;

public class  AdminStageController extends UserController {
    public Button addDashboardButton;

    private Admin admin;

    private HashMap<String,CheckBox> queriesCheckBox;



    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        queriesCheckBox = new HashMap<String,CheckBox>();
        for(int i=0;i<main.queries.size();i++){
            String name = main.queries.get(i).getName();
            CheckBox checkbox = new CheckBox(name);
            checkbox.setMinWidth(500);
            checkbox.setStyle("-fx-background-color:  #ffebcd");
            queriesCheckBox.put(name,checkbox);
        }
        admin = (Admin) main.actualUser;
        dashboardSelected = null;
        getDashboards();

    }


    public void getDashboards() {

        if(main.actualUser.getViewDashboards().size() > 0){
            showDashboards(main.actualUser.getViewDashboards());
        }
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



    public void deselectedAllQueries(){
        for(int i=0;i<main.queries.size();i++) {
            queriesCheckBox.get(main.queries.get(i).getName()).setSelected(false);
        }
    }



    public void addQuery() throws Exception {
        if(this.dashboardSelected != null) {

            final Stage dialog = new Stage();
            dialog.setTitle("Edit Queries");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(main.stage);

            SplitPane sPane = new SplitPane();
            sPane.setDividerPosition(2,0.88);
            sPane.setPrefSize(598,373);
            sPane.setOrientation(Orientation.VERTICAL);

            VBox queriesList = new VBox();
            for(int i=0; i<main.queries.size();i++){
                CheckBox checkbox = queriesCheckBox.get(main.queries.get(i).getName());
                queriesList.getChildren().add(checkbox);
            }

            Button save = new Button("SAVE");
            save.setPrefSize(571,36);
            save.setStyle("-fx-background-color: #e9967a;");
            save.setTextAlignment(TextAlignment.CENTER);

            sPane.getItems().addAll(queriesList,save);
            TitledPane tPane = new TitledPane("Edit Queries",sPane);
            tPane.setText("Edit Queries");
            tPane.setCollapsible(false);
            tPane.setPrefSize(500,500);
            tPane.setStyle("-fx-background-color: #ffebcd;");

            TitledPane titledPane = new TitledPane("My Title", new CheckBox("OK"));
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
        //queriesCheckBox = new HashMap<String,CheckBox>();
        for(int i=0;i<main.queries.size();i++){
            String name = main.queries.get(i).getName();
            //CheckBox checkbox = queriesCheckBox.get(main.queries.get(i).getName());
            //Boolean bol = check.isSelected();
            if(this.dashboardSelected.hasQuery(name) && !queriesCheckBox.get(name).isSelected()){
                this.dashboardSelected.removeQuery(name);
            }
            if(!this.dashboardSelected.hasQuery(name) && queriesCheckBox.get(name).isSelected()){
                this.dashboardSelected.addQuery(name);
            }
            //if(this.dashboardSelected.hasQuery(name) && queriesCheckBox.get(name).isSelected()){
              //  this.dashboardSelected.enableQuery(name);
            //}
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



    public void addButtonToPane(Button button, Pane pane){
        pane.getChildren().add(button);
    }



    public double getDashboardWidth() {
        return this.dashboard.getWidth();
    }

    public double getDashboardHeight() {
        return this.dashboard.getWidth();
    }



}
