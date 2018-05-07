package tp2.src.Model.MonitorSystem;

import java.util.List;

public class TicketsDealer {
    private List<Ticket> tickets;

    protected Ticket getTicket(float ID){
        for (Ticket t : this.tickets) {
            if(t.equals(ID)){
                return t;
            }
        }
        return null;
    }

}
