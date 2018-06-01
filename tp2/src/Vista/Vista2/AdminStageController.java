package tp2.src.Vista.Vista2;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class AdminStageController extends Controller{



    public FlowPane leftPane;

    @FXML
    FlowPane rightPane;

    @FXML
    LineChart lineChart;

    @FXML
    FlowPane downPane;


    public void initialize(){

        List<String> list = new ArrayList<>();
        list.add("DASHBOARD 1");
        list.add("DASHBOARD 2");
        list.add("DASHBOARD 3");
        for (String dashboardName : list) {
            Button button = new Button(dashboardName);
            leftPane.getChildren().add(button);

        }

    }

}
