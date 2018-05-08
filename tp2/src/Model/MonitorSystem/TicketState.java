package tp2.src.Model.MonitorSystem;

import java.time.LocalDateTime;


public class TicketState {
    private String tittle;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public TicketState(String tittle){
        this.tittle = tittle;
        this.startDateTime = LocalDateTime.now();
    }

    public boolean isClosed() {
        return (tittle == "CLOSED");
    }

    public LocalDateTime startDateTime() {
        return this.startDateTime;
    }

    public LocalDateTime endDateTime(){
        return this.endDateTime;
    }

    public String getTittle() {
        return this.tittle;
    }

    public boolean isOpen() {
        return (tittle != "CLOSED");
    }

    public void close(){
        this.endDateTime = LocalDateTime.now();
    }
}
