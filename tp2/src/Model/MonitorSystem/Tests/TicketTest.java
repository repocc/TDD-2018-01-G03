package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.Ticket;
import tp2.src.Model.MonitorSystem.TicketState;

import java.time.LocalDateTime;

public class TicketTest extends TestCase {

    private Ticket ticket;

    public void setUp() throws Exception {
        super.setUp();
        TicketState initialState = new TicketState("OPEN");
        this.ticket = new Ticket(initialState);
    }

    public void testGetActualState() {
        assertSame(ticket.getActualState().getTittle(),"OPEN");
    }

    public void testChangeState(){
        assertTrue(ticket.getActualState().getTittle() == "OPEN");
        TicketState endState = new TicketState("CLOSED");
        ticket.changeState(endState);
        assertTrue(ticket.getActualState().getTittle() == "CLOSED");
    }

    public void testIsOpen(){
        assertTrue(ticket.isOpen());
        TicketState endState = new TicketState("CLOSED");
        ticket.changeState(endState);
        assertFalse(ticket.isOpen());
    }

    public void testIsClose(){
        assertFalse(ticket.isClosed());
        TicketState endState = new TicketState("CLOSED");
        ticket.changeState(endState);
        assertTrue(ticket.isClosed());
    }

    public void testInitialDateTime(){
        assertTrue(ticket.initialDateTime().isBefore(LocalDateTime.now()));
    }

    public void testEndDateTime() {
        TicketState endState = new TicketState("CLOSED");
        ticket.changeState(endState);
        assertTrue(ticket.endDateTime().isBefore(LocalDateTime.now()));
        assertTrue(ticket.endDateTime().isAfter(ticket.initialDateTime()));
    }
}