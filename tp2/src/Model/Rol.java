package tp2.src.Model;

public class Rol {
    Project project;

    public Rol(Project project){
        this.project = project;
    }
    public String getProjectName() {
        return this.project.getName();
    }
}
