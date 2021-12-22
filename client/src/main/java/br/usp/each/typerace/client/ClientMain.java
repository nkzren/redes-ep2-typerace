package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;

    private static final String server = "ws://localhost";

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    //inicialização do cliente, com passagem de seu id por um header, chamado clientId
    public void init(String idCliente) {
        System.out.println("Iniciando cliente...");
        client.addHeader("clientId", idCliente);
        client.connect();
    }
    //no método main, temos a inicialização do cliente e conexão com o server, e captura de entrada do usuário
    //ao digitar "fechar", o cliente é desconectado e encerrado
    //ao digitar "iniciar", a partida é iniciada, e os clientes recebem a lista de palavras
    //o cliente captura as entradas do usuário e as manda para o server, junto com seu id, separado por "$#"
    public static void main(String[] args) {

        String line = "";
        Scanner sc = new Scanner(System.in);

        try{
            Client client = new Client(new URI(server+":"+args[0]));

            ClientMain main = new ClientMain(client);

            main.init(client.getId());

            System.out.println("O seu id é: "+client.getId());

            System.out.println("Digite 'fechar' para sair...");

            while(!line.equals("fechar")){
                if(line != null && line.trim() != ""){
                    client.send(client.getId()+"$#"+line);
                }
                line = sc.nextLine();
            }

            if(client.isOpen())
                client.close();

        } catch(Exception e){
            System.out.println("Erro no cliente, tente novamente mais tarde...");
        }
        
        sc.close();
    }
}
