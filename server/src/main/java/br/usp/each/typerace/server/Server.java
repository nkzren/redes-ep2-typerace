package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override //Quando alguem entra no jogo
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // TODO: Implementar
        String newPlayer = conn.getResourceDescriptor();;
        connections.put(newPlayer, conn);
        this.connections.forEach((id,conns) -> conns.send(newPlayer+" se juntou ao jogo"));
        //Testar depois se o id é valido

       
    }

    @Override //Pede pra sair do jogo
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // TODO: Implementar
        connections.remove(conn.getResourceDescriptor());
        if(!conn.isClosed()) conn.close(code, reason);
    }

    @Override //Quando mandam qualquer coisa para o servidor, provavelmente, as respostas
    public void onMessage(WebSocket conn, String message) {
        // TODO: Implementar
        if(message.equalsIgnoreCase("start")){
            //começa o jogo
        }
        else if(message.equalsIgnoreCase("stop")){
            //termina o jogo pra todo mundo
        }
        else{
            //verifica se a palavra está no banco de palavras possiveis
        }
    }

    @Override //Caso aconteça algo
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override //Quando o server começar
    public void onStart() {
        // TODO: Implementar
    }
}
