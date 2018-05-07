package tp2.src.Model.TicketSystem;

import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Model.MonitorSystem.Ticket;
import tp2.src.Model.MonitorSystem.TicketState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Project {
    private String name;
    private List<Ticket> tickets;
    private List<Query> queries;
    private HashMap<String,List<TicketState>> ticketsFlow;

    public Project(String name) {
        this.name = name;
        this.tickets = new ArrayList<Ticket>();
        this.queries = new ArrayList<Query>();
        this.ticketsFlow = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public void setNewTicketFlow(String ticketFlowName, List<TicketState> ticketFlow) {
        this.ticketsFlow.put(ticketFlowName, ticketFlow);
    }
    public void defineNewQuery(Query query){
        this.queries.add(query);
    }
}
