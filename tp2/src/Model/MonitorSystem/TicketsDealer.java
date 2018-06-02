package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;
import tp2.src.Model.MonitorSystem.TicketUpdate.SimpleHttpServer;

public class TicketsDealer {
    private Engine engine;

    public TicketsDealer(Engine engine) {
        this.engine = engine;
        new SimpleHttpServer(this);
    }

    public void updateDashboards(Ticket ticketModified) throws RuleNotFoundException {
        System.out.println("Actualizacion de Queries...\n");
        this.engine.updateQueries(ticketModified);
    }

    public void updateTicket(Ticket ticketModified) throws RuleNotFoundException {
        this.updateDashboards(ticketModified);
    }
}
