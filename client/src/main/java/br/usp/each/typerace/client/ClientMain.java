package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Set;

public class ClientMain {

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) {
        System.out.println("Iniciando cliente: " + idCliente);
        // TODO: Implementar
        client.connect();
    }

    public static void main(String[] args) {
        /*
           FIXME: Remover essas strings fixas
           Como podemos fazer para que o cliente receba um parâmetro indicando a qual servidor
           ele deve se conectar e o seu ID?
        */
        Scanner sc = new Scanner(System.in);
        System.out.println("Olá. Diga a porta em que deseja se conectar");
        String porta = sc.nextLine();
        System.out.println("Agora digite seu id");
        String id = sc.nextLine();
        String uri = "ws://localhost:" +porta+"/id="+id;

        try {
            WebSocketClient client = new Client(new URI(uri));
            ClientMain main = new ClientMain(client);
            main.init(id);
        } 
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
