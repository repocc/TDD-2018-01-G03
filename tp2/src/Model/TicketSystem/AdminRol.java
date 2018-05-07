package tp2.src.Model.TicketSystem;

public class AdminRol extends Rol {
    public AdminRol(Project project) {
        super(project);
    }
    public void associateUser(User user){
        AssociatedRol newAssociated = new AssociatedRol(this.project);
        user.addRole(newAssociated);
    }
    public void newAdmin(User user){
        AdminRol newAdmin = new AdminRol(this.project);
        user.addRole(newAdmin);
    }
    public void defineQuery(Query newQuery){
        this.project.defineNewQuery(newQuery);
    }

}
