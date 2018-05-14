package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;

public class TicketSystemG3Translator implements TicketTranslator {
    private TicketsDealer ticketsDealer;
    public TicketSystemG3Translator(TicketsDealer ticketsDealer) {
        this.ticketsDealer = ticketsDealer;
    }

    @Override
    public void addTicket(Ticket ticket) {
        try {
            this.ticketsDealer.addTicket(ticket);
        } catch (RuleNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ticketUpdate(Ticket ticket) {
        try {
            this.ticketsDealer.updateTicket(ticket);
        } catch (RuleNotFoundException e) {
            e.printStackTrace();
        }
    }
}
