package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import br.usp.each.typerace.Jogador;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;
    private Map<String, Jogador> Jogadores;
    private boolean GameNow = false;


    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    private String configuraId(String uri){
        String urisplit[] = uri.split("="); 
        return urisplit[1];
    }

    @Override //Quando alguem entra no jogo
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // TODO: Implementar
        String newPlayer = configuraId(conn.getResourceDescriptor());
        connections.put(newPlayer, conn);
        Jogador novo = new Jogador(newPlayer, new ArrayList<>());
        Jogadores.put(newPlayer, novo);
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
