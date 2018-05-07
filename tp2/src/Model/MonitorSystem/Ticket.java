package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    public float ID;
    private TicketState state;
    private List<TicketState> previousStates;
    private List<Comment> comments;
    private String description;

    public Ticket(float ID, TicketState initialState){
        this.ID = ID;
        this.state = initialState;
        this.comments = new ArrayList<Comment>();
        this.previousStates = new ArrayList<TicketState>();
    }
    public void addComment(String userName, String text){
        this.comments.add(new Comment(userName, text));
    }
    public void changeState(TicketState newState){
        this.state = newState;
        previousStates.add(newState);
    }
    public boolean equals(float ID) {
        return (ID == this.ID);
    }

}