package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;

    private static final String server = "ws://localhost:8080";

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) {
        System.out.println("Iniciando cliente...");
        client.addHeader("clientId", idCliente);
        client.connect();
    }

    public static void main(String[] args) {

        String line = "";
        Scanner sc = new Scanner(System.in);

        try{
            Client client = new Client(new URI(server));

            ClientMain main = new ClientMain(client);

            main.init(client.getId());

            System.out.println("Digite 'fechar' para sair...");

            while(!line.equals("fechar")){
                if(line != null && line.trim() != ""){
                    client.send(client.getId()+"$#"+line);
                }
                line = sc.nextLine();
            }

        } catch(Exception e){
            System.out.println("Erro no cliente, tente novamente mais tarde...");
        }
        
        sc.close();
    }
}
