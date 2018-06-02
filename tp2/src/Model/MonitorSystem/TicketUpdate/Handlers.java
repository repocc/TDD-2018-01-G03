package tp2.src.Model.MonitorSystem.TicketUpdate;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;
import tp2.src.Model.MonitorSystem.TicketsDealer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


public class Handlers {

    public static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + 9000 + "</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
    }}


    public static class TicketUpdatedG3Handler implements HttpHandler {
        TicketsDealer ticketsDealer;
        private TicketSystemG3Translator G3Translate;

        public TicketUpdatedG3Handler(TicketsDealer ticketsDealer) {
            this.ticketsDealer = ticketsDealer;
            G3Translate = new TicketSystemG3Translator();
        }

        public String getBody(HttpExchange he){
            String ticket = "";
            byte[] buf = new byte[200];
            try (InputStream is = he.getRequestBody()) {
                while (is.read(buf) != -1) ;
                ticket = new String( buf, Charset.forName("UTF-8") );
            } catch (IOException e) {
                System.out.println("Can not read RequestBody");
                e.printStackTrace();
            }
            int iend = ticket.indexOf("=");
            ticket = ticket.substring(0, iend);
            System.out.println("Ticket arrived: " + ticket + "...");
            return ticket;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            String ticket = getBody(he);
            JSONObject ticketJson = new JSONObject();
            ticketJson.put("state", ticket);
            try {
                System.out.println("Ticket sent to Dealear...");
                ticketsDealer.updateTicket(G3Translate.translateTicket(ticketJson));
            } catch (RuleNotFoundException e) {
                e.printStackTrace();
            }

            String response = "TICKET UPDATED: " + ticket;
            he.sendResponseHeaders(201, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
}

