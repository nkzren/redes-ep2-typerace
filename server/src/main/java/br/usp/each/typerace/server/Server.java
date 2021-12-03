package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    private int counter = 0;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send( "Bem vindo ao servidor!" ); //This method sends a message to the new client
        broadcast( "Nova conexao de " + handshake.getResourceDescriptor() +
                "\njogardores conectados: " + (++counter) ); //This method sends a message to all clients connected
        System.out.println( "new connection to " + conn.getRemoteSocketAddress() );
        connections.put( conn.getResourceDescriptor(), conn );
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println( "jogador " + conn.getResourceDescriptor() +
                " desconectou com codigo " + code + " info adicional: " + reason );
        broadcast( "jogardores conectados: " + (--counter) );

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println( conn.getResourceDescriptor() + " enviou " + message );
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println( "um erro ocorreu em " + conn.getRemoteSocketAddress()  + ":" + ex );

    }

    @Override
    public void onStart() {
        System.out.println("Endereco: "+getAddress() );
        System.out.println( "Servidor iniciado com sucesso!" );

    }
}
