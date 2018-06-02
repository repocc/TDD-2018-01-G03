package tp2.src.Vista.Vista2;

import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.Model.MonitorSystem.User;

public class AssociatedView extends UserView {



    public AssociatedView(Main2 main){
        super(main);
    }

    @Override
    public User createUser(String name) {
        Associated assoc = new Associated(name, main.monitorSystem);
        main.monitorSystem.addUser(assoc);
        return assoc;

    }

    @Override
    public void setScene() {
        try {
            this.main.replaceSceneContent("AssociatedStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
