package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public boolean playing = false;

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        
    }

    @Override
    public void onMessage(String message) {
        try{
            System.out.println(Integer.parseInt(message)+" jogadores conectados");
        }catch(Exception e){
            if(!playing){
                playing = true;
                System.out.println("Digite as palavras:");
                System.out.println(message.split(","));
            }
            else{
                playing = false;
                System.out.println(message);
            }
        }
        
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // TODO: Implementar
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
    }
}
