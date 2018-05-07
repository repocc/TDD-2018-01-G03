package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Engine {
    private List<Dashboard> dashboards;

    public Engine() {
        this.dashboards = new ArrayList<Dashboard>();
    }
    public void updateQueries(List<Ticket> tickets){
        Iterator<Dashboard> it = this.dashboards.iterator();
        while (it.hasNext()) {

            Iterator<Query> it2 = it.next().getQueries().iterator();
            while (it2.hasNext()) {
                Query q =  it2.next();
                q.getRule();
                /* TODO: comunicacion con clojure
                   Clorjure -> rule, tickets
                   q.updateResult(api(rule, tickets));

                 */
                break;
            }
            break;
        }
    }
}
