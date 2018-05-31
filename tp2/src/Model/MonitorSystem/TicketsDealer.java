package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;

public class TicketsDealer {
    private Engine engine;

    public TicketsDealer(Engine engine) {
        this.engine = engine;
    }

    public void updateDashboards(Ticket ticketModified) throws RuleNotFoundException {
        this.engine.updateQueries(ticketModified);
    }

    public void updateTicket(Ticket ticketModified) throws RuleNotFoundException {
        this.updateDashboards(ticketModified);
    }
}
