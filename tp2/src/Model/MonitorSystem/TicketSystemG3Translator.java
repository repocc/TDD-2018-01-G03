package tp2.src.Model.MonitorSystem;

public class TicketSystemG3Translator implements TicketTranslator {
    private TicketsDealer ticketsDealer;
    public TicketSystemG3Translator(TicketsDealer ticketsDealer) {
        this.ticketsDealer = ticketsDealer;
    }

    @Override
    public void addTicket(Ticket ticket) {
        this.ticketsDealer.addTicket(ticket);
    }

    @Override
    public void removeTicket(float ID) {
        this.ticketsDealer.removeTicket(ID);
    }

    public void ticketUpdate() {
        this.ticketsDealer.updateTicket();
    }
}
