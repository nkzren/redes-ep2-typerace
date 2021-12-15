package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server extends WebSocketServer {
    private boolean playing;
    private final Map<String, WebSocket> connections; 
    private final Map<String/*clientId*/, Set<String>/*palavras não acertadas*/ > mapaPalavras;
    private String lista = "batata,feijao,arroz";

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
        mapaPalavras = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if(!playing){
            connections.put(handshake.getFieldValue("clientId"), conn);
            System.out.println(handshake.getFieldValue("clientId"));
            broadcast(String.valueOf(connections.size()));
        }

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // TODO: Implementar
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println();
        
        switch(message){
            case "iniciar":
                playing = true;
                String[] palavras = lista.split(",");
                Set<String> palavrasSet = new HashSet<>();
                for(String palavra: palavras){
                    palavrasSet.add(palavra);
                }
                Iterator<String> clientes = connections.keySet().iterator();
                while(clientes.hasNext()){
                    mapaPalavras.put(clientes.next(), new HashSet<>(palavrasSet));
                }
                broadcast(lista);
            break;
            default:
                String[] values = message.split("/");
                if(playing){
                    System.out.println(values[0]+"/"+values[1]);
                    if(lista.contains(values[1])){
                        Set<String> palavrasClient = mapaPalavras.get(values[0]);
                        palavrasClient.remove(values[1]);
                        if(palavrasClient.size() == 0){
                            System.out.println("terminou");
                            broadcast("terminou");
                        }
                    }
                }
            break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        // TODO: Implementar
    }
}
