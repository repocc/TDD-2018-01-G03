package tp2.src.Model.MonitorSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private List<TicketState> states;

    public Ticket(TicketState initialState){
        this.states = new ArrayList<TicketState>();
        this.states.add(initialState);
    }

    public void changeState(TicketState newState){
        this.getActualState().close();
        states.add(newState);
    }

    public TicketState getActualState() {
        return this.states.get(this.states.size() - 1);
    }

    public boolean isOpen(){
        return (this.getActualState().isOpen());
    }

    public LocalDateTime initialDateTime(){
        return (this.states.get(0).startDateTime());
    }

    public LocalDateTime endDateTime(){
        return this.getActualState().endDateTime();
    }

    public boolean isClosed() {
        return this.getActualState().isClosed();
    }

    public void remove(){
        this.changeState(new TicketState("REMOVED"));
    }
}