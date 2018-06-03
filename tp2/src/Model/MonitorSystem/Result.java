package tp2.src.Model.MonitorSystem;

import java.time.LocalDateTime;

public class Result {
    public float value;
    public  LocalDateTime dateTimeRecorded;

    public Result(float value) {
        this.value = value;
        this.dateTimeRecorded = LocalDateTime.now();
    }


}
