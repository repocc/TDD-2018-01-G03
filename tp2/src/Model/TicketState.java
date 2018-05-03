package tp2.src.Model;

import java.time.LocalDateTime;


public class TicketState {
    private String tittle;
    private LocalDateTime start;
    private LocalDateTime end;

    public TicketState(String tittle){
        this.tittle = tittle;
        this.start = LocalDateTime.now();
    }

}
