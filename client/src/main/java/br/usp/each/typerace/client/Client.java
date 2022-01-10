package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conectado com sucesso");
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // TODO: Implementar
        System.out.println("Conexão fechada \n Motivo:" +reason);
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
        System.out.println(ex.toString());
    }
}
