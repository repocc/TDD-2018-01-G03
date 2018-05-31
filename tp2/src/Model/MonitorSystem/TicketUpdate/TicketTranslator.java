package tp2.src.Model.MonitorSystem.TicketUpdate;


import org.json.simple.JSONObject;
import tp2.src.Model.MonitorSystem.Ticket;

public interface  TicketTranslator {

    public Ticket translateTicket(JSONObject ticket);

}
