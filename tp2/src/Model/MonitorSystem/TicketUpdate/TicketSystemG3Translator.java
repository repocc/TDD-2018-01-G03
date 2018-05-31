package tp2.src.Model.MonitorSystem.TicketUpdate;

import org.json.simple.JSONObject;
import tp2.src.Model.MonitorSystem.Ticket;
import tp2.src.Model.MonitorSystem.TicketState;

public class TicketSystemG3Translator implements TicketTranslator {

    @Override
    public Ticket translateTicket(JSONObject ticket) {
        TicketState state = new TicketState(ticket.get("state").toString());
        return new Ticket(state);
    }
}
