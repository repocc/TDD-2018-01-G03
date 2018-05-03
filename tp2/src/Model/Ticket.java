package tp2.src.Model;
import Model.Comment;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String tittle;
    private TicketState state;
    private List<TicketState> previousStates;
    private List<Comment> comments;
    private String description;

    public Ticket(String tittle, TicketState initialState){
        this.tittle = tittle;
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
}
