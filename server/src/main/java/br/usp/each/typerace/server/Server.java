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
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class Server extends WebSocketServer {
    private boolean playing;
    private final Map<String, WebSocket> connections; 
    private final Map<String/*clientId*/, Player> mapaPalavras;
    private String lista = "batata,feijao,arroz";
    private int time = 0;
    private int min = 0;
    private int sec = 0;
    Timer cronometro = new Timer();

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
                    String clientId = clientes.next();
                    mapaPalavras.put(clientId, new Player(palavrasSet, clientId));
                }
                cronometra(true);
                broadcast(lista);
            break;
            default:
            System.out.println(message);
                String[] values = message.split("/");
                if(playing){
                    Player playerSentWord = mapaPalavras.get(values[0]);
                    if (playerSentWord.contabilizeNewWord(values[1])) {              
                        cronometra(false);
                        this.time = 0;
                        Player.restartLastWordTimeClassification();
                        TreeSet<Player> classificacao = new TreeSet<>(mapaPalavras.values());
                        String classificacaoStr = "PARTIDA FINALIZADA \n Tempo de duração: " + min + " : " + sec + "\n";
                        int countClassificacao = 1;
                        for(Player player : classificacao){
                            classificacaoStr += countClassificacao + "º -" + player.toString()+"\n";
                            countClassificacao++;
                        }
                        System.out.println(classificacaoStr);
                        broadcast(classificacaoStr);
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

    public void cronometra(boolean flag){
        if(!flag){
            cronometro.cancel();
            return;
        }
        
        TimerTask task = new TimerTask() {
            public void run(){
                time++;
                sec = time % 60;
                min = time / 60;
                System.out.println(min+":"+sec);
            }
        }; 
        cronometro.schedule(task,0,1000);
    }
}
