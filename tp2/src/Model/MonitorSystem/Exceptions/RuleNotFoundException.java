package tp2.src.Model.MonitorSystem.Exceptions;

public class RuleNotFoundException extends NotFoundException {
    public RuleNotFoundException(String ruleName) {
        System.out.println("There is not any rule called: " + ruleName);
    }
}
