package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;
import tp2.src.Controller.UserController;
import tp2.src.Controller.ViewObserver;

public class TicketsDealer {
    private Engine engine;
    private ViewObserver viewObserver;

    public TicketsDealer(Engine engine, ViewObserver viewObserver) {
        this.viewObserver = viewObserver;
        this.engine = engine;
        new SimpleHttpServer(this);
    }

    public void updateDashboards(Ticket ticketModified) throws RuleNotFoundException {
        System.out.println("Actualizacion de Queries...\n");
        this.engine.updateQueries(ticketModified);
        this.viewObserver.updateView();
    }

    public void updateTicket(Ticket ticketModified) throws RuleNotFoundException {
        this.updateDashboards(ticketModified);
    }

    public void setUserController(UserController userController){
        this.viewObserver.setUserController(userController);
    }
}
