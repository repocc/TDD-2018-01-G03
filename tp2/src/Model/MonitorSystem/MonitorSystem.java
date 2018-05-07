package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.List;

public class MonitorSystem {
    private List<User> users;
    private Engine engine;

    public MonitorSystem(Engine engine) {
        this.engine = engine;
        this.users = new ArrayList<User>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
