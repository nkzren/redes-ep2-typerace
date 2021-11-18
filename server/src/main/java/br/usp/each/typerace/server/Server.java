package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {
    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // TODO: Implementar
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // TODO: Implementar
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // TODO: Implementar
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        // TODO: Implementar
    }

    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");

        WebSocketServer server = new Server(8080);
        server.start();
    }

}
