package tp2.src.Model.MonitorSystem;

import java.util.Iterator;
import java.util.List;

public class TicketsDealer {
    private List<Ticket> tickets;
    private Engine engine;

    public TicketsDealer(Engine engine) {
        this.engine = engine;
    }

    public void updateDashboards() {
        this.engine.updateQueries(tickets);
    }

    public void updateTicket(Ticket ticket) {
        //TODO: update ticket
        this.updateDashboards();
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        this.updateDashboards();
    }

    public void removeTicket(Ticket ticket) {
        Iterator<Ticket> it = this.tickets.iterator();
        while (it.hasNext()) {
            if(it.next().equals(ticket.ID)){
                it.remove();
                break;
            }
        }
    }
}