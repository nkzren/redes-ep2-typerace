package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.UUID;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    private String id = UUID.randomUUID().toString();

    private static int MAX_RETRY = 5;

    private void openConnection(){
        this.addHeader("clientId", id);
        this.connect();
    }

    public String getId(){
        return this.id;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conexão com o servidor aberta: \n" +
                "+ Status: "+handshakedata.getHttpStatus()+"\n" +
                "+ Mensagem: "+handshakedata.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Fechando conexão com o servidor: \n" +
                "+ Servidor terminou conexão: "+remote +"\n" +
                "+ Razão: "+reason+"\n");
        if(code == 405){
            id = UUID.randomUUID().toString();
            openConnection();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Erro na conexão com o servidor: \n" +
                "+ Exceção: "+ex.getMessage()+"\n");
    }
}
