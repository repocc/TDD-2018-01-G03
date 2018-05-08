public  class Main {



    public static void main(String[] args) {

        conexion conexion = new conexion();
        //conexion.getAllRules();


        //1ro deberia pasarle las rules . La rule es un json de tipo :
        //{nombre : "email-count", parametros :"",condicion :"current spam}
//        String url ="http://localhost:3000/example/api/setRules";
//        Rule rule = new Rule()
//        conexion.postRequest(rule,url);


        //2do . Inicializar los contadores con las rules
        String url = "http://localhost:3000/example/api/initialize-counters";
        conexion.getRequest(url);
//
////        String url1 = "http://localhost:3000/example/api/getState";
//        conexion.getRequest(url1);

        // String input = "{\"nombre\":receive-counter,\"parametro\":\" \",\"condicion\":\" current spam\"}";
        // String input = "{\"condicion\":\"true\",\"parametro\":\"\",\"nombre\":\"lalal-sssss\"}";
        String url1 = "http://localhost:3000/example/api/getState";


        conexion.getRequest(url1);
//
        String input = "{\"nombre\":\"ticketUno\",\"estado\":\"ready\"}";

        String url2 = "http://localhost:3000/example/api/processTicket";
        for (int i=0; i<20 ; i++) {

            conexion.postRequest(input, url2);
        }
       conexion.getRequest(url1);



    }

}