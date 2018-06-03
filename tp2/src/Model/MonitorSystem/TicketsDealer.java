package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;
import tp2.src.Controller.UserController;
import tp2.src.Controller.Observer;

public class TicketsDealer {
    private Engine engine;
    private Observer observer;

    public TicketsDealer(Engine engine, Observer observer) {
        this.observer = observer;
        this.engine = engine;
        new SimpleHttpServer(this);
    }

    public void updateDashboards(Ticket ticketModified) throws RuleNotFoundException {
        System.out.println("Actualizacion de Queries...\n");
        this.engine.updateQueries(ticketModified);
        this.observer.updateView();
    }

    public void updateTicket(Ticket ticketModified) throws RuleNotFoundException {
        this.updateDashboards(ticketModified);
    }

    public void setUserController(UserController userController){
        this.observer.setUserController(userController);
    }
}
