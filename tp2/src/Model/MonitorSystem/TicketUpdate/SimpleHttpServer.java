package tp2.src.Model.MonitorSystem.TicketUpdate;

import com.sun.net.httpserver.HttpServer;
import tp2.src.Model.MonitorSystem.TicketsDealer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    private int port;
    private HttpServer server;

    public SimpleHttpServer(TicketsDealer ticketsDealer) {
        this.port = 9000;
        this.Start(port,ticketsDealer);
    }



    public void Start(int port, TicketsDealer ticketsDealer) {
        try {
            this.port = port;
            server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("\nHTTP Server started at " + port + "...\n\n");
            server.createContext("/ticketUpdateG3", new Handlers.TicketUpdatedG3Handler(ticketsDealer));
            server.createContext("/serverOk", new Handlers.RootHandler());

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        server.stop(0);
        System.out.println("server stopped");
    }
}
