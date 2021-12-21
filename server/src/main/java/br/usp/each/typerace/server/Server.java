package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class Server extends WebSocketServer {
    private boolean playing;
    private Map<String, WebSocket> connections;
    private Map<String/*clientId*/, Player> mapaPalavras;
    private String[] lista = {"batata","feijão","arroz","dale","pão","mesa","celular","computador","livro","documento","controle","joelheira","carteira","mouse","desodorante","pedra","tecla","sacola","tomada","janela","tela"};
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
        if(playing){
            conn.send("Jogo já iniciado...");
            conn.close();
        } else{
            String uuid = handshake.getFieldValue("clientId");
            if(connections.containsKey(uuid)){
                conn.close(405, "ID já usado");
            } else{
                connections.put(uuid, conn);
                System.out.println("Cliente conectado com uuid ["+uuid+"]");
                broadcast("Número de jogadores conectados no momento: "+connections.size());
                broadcast("Digite iniciar para começar o jogo...");
            }
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Set<String> ids = connections.keySet().stream().filter(x -> conn.equals(connections.get(x))).collect(Collectors.toSet());
        ids.forEach((x) -> {
            connections.remove(x);
            mapaPalavras.remove(x);
        });
        System.out.println("Conexão com o web socket ["+conn.toString()+"] fechada:\n" +
                        "+ Fechamento causado pelo client: "+remote+"\n" +
                        "+ Razão: "+reason+"\n"+
                        "+ Código: "+code);
        if(connections.size() == 0){
            finishGame(false);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        String sender = message.substring(0, message.indexOf("$#"));
        String trueMessage = message.substring(message.indexOf("$#")+2);
        System.out.println("Servidor recebeu uma mensagem com corpo ["+trueMessage+"] do cliente ["+sender+"]");

        if(playing){
            processGameMessage(trueMessage, sender);
        } else{
            if(trueMessage.equals("iniciar")){
                initGame();
            } else{
                broadcast("Digite iniciar para começar o jogo...");
            }
        }
    }
    
    public void initGame(){
        playing = true;
        Set<String> palavrasSet = montaListaDePalavras(lista);
        Iterator<String> clientes = connections.keySet().iterator();
        while(clientes.hasNext()){
            String clientId = clientes.next();
            mapaPalavras.put(clientId, new Player(palavrasSet, clientId));
        }
        cronometra(true);
        broadcast("Lista de palavras:"+listaDePalavras(palavrasSet));
    }

    public Set montaListaDePalavras(String[] palavras){
        Set<String> palavrasSet = new HashSet<>();
        int random = 0;
        while(palavrasSet.size() != 5){
            random = new Random().nextInt(lista.length);
            palavrasSet.add(lista[random]);
        }
        return palavrasSet;
    }

    public String listaDePalavras(Set<String> palavras){
        String resultado = "";
        for (String a : palavras){
            resultado += "\n"+a;
        }
        return resultado+"\n";
    }

    public void processGameMessage(String message, String clientId){
        Player playerSentWord = mapaPalavras.get(clientId);
        if (playerSentWord.contabilizeNewWord(message)) {
            finishGame(true);
        }
    }

    private void finishGame(boolean playerFinished){
        System.out.println("Jogo finalizado...");
        cronometra(false);
        this.time = 0;
        this.playing = false;
        Player.restartLastWordTimeClassification();
        if(playerFinished){
            TreeSet<Player> classificacao = new TreeSet<>(mapaPalavras.values());
            String classificacaoStr = "PARTIDA FINALIZADA \n Tempo de duração: " + min + " : " + sec + "\n";
            int countClassificacao = 1;
            for(Player player : classificacao.descendingSet()){
                classificacaoStr += countClassificacao + "º -" + player.toString()+"\n";
                countClassificacao++;
            }
            System.out.println(classificacaoStr);
            broadcast(classificacaoStr);
            broadcast("Digite iniciar para começar o jogo...");
        }
        cronometro = new Timer();
        mapaPalavras = new HashMap<>();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if(conn != null){
            System.out.print("Erro ao efetuar conexão com o web socket ["+conn+"]:\n" +
                    "+ Conexão está aberta: "+conn.isOpen()+"\n" +
                    "+ Exception: "+ex.getMessage()+"\n");
            ex.printStackTrace();
        } else{
            System.out.print("Erro ao executar código do Server:\n" +
                    "+ Exception: "+ex.getMessage()+"\n");
            ex.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        System.out.println("Nenhum erro na inicialização encontrado");
        System.out.println("Servidor inicializado corretamente na porta: "+this.getPort());
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
