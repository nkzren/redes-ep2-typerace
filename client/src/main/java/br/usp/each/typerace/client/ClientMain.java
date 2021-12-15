package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) {
        System.out.println("Iniciando cliente: " + idCliente);
        client.addHeader("clientId", idCliente);
        client.connect();
    }

    public static void main(String[] args) {
        /*
           FIXME: Remover essas strings fixas
           Como podemos fazer para que o cliente receba um parâmetro indicando a qual servidor
           ele deve se conectar e o seu ID?
        */
        String removeMe = "ws://localhost:8080";
        String removeMe2 = "dada";
        Scanner sc = new Scanner(System.in);

        try {
            Client client = new Client(new URI(removeMe));

            ClientMain main = new ClientMain(client);

            main.init(removeMe2);

            String clientInput = "";
            while(!clientInput.equals("iniciar") && !client.playing){
                System.out.println("Quando quiser, digite \"iniciar\" e tecle enter para começar o jogo");
                clientInput = sc.nextLine();
            }
            System.out.println(clientInput);
            client.send(clientInput);
            String clientTry = "";
            client.playing = true;
            System.out.println("Digite as palavras:");
            while(client.playing){
                clientTry = sc.nextLine();
                client.send(removeMe2+"/"+clientTry);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        sc.close();
    }
}
