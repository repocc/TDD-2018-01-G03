package tp2.src.Model.MonitorSystem;

import java.time.LocalDateTime;

public class Result {
    private float value;
    private LocalDateTime dateTimeRecorded;

    public Result(float value) {
        this.value = value;
        this.dateTimeRecorded = LocalDateTime.now();
    }
}
