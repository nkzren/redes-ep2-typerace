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
        // TODO: Implementar
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
