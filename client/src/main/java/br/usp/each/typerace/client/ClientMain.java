package br.usp.each.typerace.client;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) {
        System.out.println("Iniciando cliente: " + idCliente);
        // TODO: Implementar
        client.connect();
        String str;
        Scanner sc = new Scanner(System.in);

        //manda msg pro servidor e o servidor printa no console
        while(!client.isClosed()){
            str = sc.next();
            if(str.toLowerCase(Locale.ROOT).equals("exit")) break;
            client.send(str);
        }
        client.close();

    }

    public static void main(String[] args) {
        /*
           FIXME: Remover essas strings fixas
           Como podemos fazer para que o cliente receba um parâmetro indicando a qual servidor
           ele deve se conectar e o seu ID?
        */
        String removeMe = "ws://localhost:8080/idCliente";
        String removeMe2 = "idCliente";

        try {
            URI uri = new URI(removeMe);
            WebSocketClient client = new Client(uri);

            ClientMain main = new ClientMain(client);

            main.init(removeMe2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
