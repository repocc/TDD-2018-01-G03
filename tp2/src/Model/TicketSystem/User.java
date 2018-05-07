package tp2.src.Model.TicketSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public abstract class User{
    private List<Rol> rols;
    private String name;

    public User(String name){
        this.name = name;
        this.rols = new ArrayList<Rol>();
    }

    public void addRole(Rol newRol) {
        this.rols.add(newRol);
    }

    public void removeRole(String projectName) {
        Predicate<Rol> projectPredicate = rol -> rol.getProjectName().equals(projectName);
        rols.removeIf(projectPredicate);
    }

    public void newProject(String name) {
        Project newProject = new Project(name);
        AdminRol adminProject = new AdminRol(newProject);
        this.rols.add(adminProject);
    }
}