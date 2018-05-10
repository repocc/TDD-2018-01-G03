package tp2.src.Model.MonitorSystem;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conector {

    public Conector() {
    }

    public String getRequest(String urlGet) {

        String output = "";
        try {

            URL url = new URL(urlGet);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            System.out.println("Output from Server ... \n");  String op = "";
            while ((op = br.readLine()) != null) {
                output = output + op;
                System.out.println(op);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return output;
    }

    public String postRequest(JSONObject stringJson, String urlPost) {
        String output = "";

        try {

            URL url = new URL(urlPost);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = stringJson.toString();
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            System.out.println("Output from Server ...");
            String op = "";
            while ((op = br.readLine()) != null) {
                System.out.println(op);
                output = output + op;
            }
            System.out.println("\n");
            conn.disconnect();
            return output;

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return output;

    }

    public void initializeProcessor(){
        this.getRequest("http://localhost:3000/example/api/processor-initialization");
    }

    public String setRule(JSONObject rule) {
        return this.postRequest(rule, "http://localhost:3000/example/api/setRule");
    }

    public String proccessTicket(JSONObject ticket){
        return this.postRequest(ticket, "http://localhost:3000/example/api/processTicket");
    }

    public String getLastState(){
        return this.getRequest("http://localhost:3000/example/api/getState");
    }
    public String getLastSignal(){
        return this.getRequest("http://localhost:3000/example/api/getLastSignal");
    }

    public float getCounterValue(JSONObject counterName) {
        return Float.parseFloat(this.postRequest(counterName, "http://localhost:3000/example/api/counterValue"));
    }

    public String calculateLastSignal() {
        return this.getRequest("http://localhost:3000/example/api/calculateLastSignal");
    }

    public float getRuleValue(JSONObject ruleName) {
        String value = this.postRequest(ruleName, "http://localhost:3000/example/api/ruleValue");

        if (value.contains("/")) {
            String[] rat = value.split("/");
            return Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
        } else {
            return Float.parseFloat(value);
        }
    }
}
