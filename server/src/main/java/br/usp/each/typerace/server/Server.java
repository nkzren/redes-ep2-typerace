package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import br.usp.each.typerace.Jogador;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Server extends WebSocketServer {

    private Map<String, WebSocket> connections;
    private Map<String, Jogador> jogadores;
    private boolean GameNow;


    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
        this.jogadores = new HashMap<>();
        this.GameNow = false;
    }

    private String configuraId(String url){
        String urisplit[] = url.split("="); 
        return urisplit[1];
    }

    private String placar(){
        List<Integer> list = new ArrayList<Integer>();
        Map<Integer, Jogador> map = new HashMap<>();
        for(Jogador jogador : this.jogadores.values()){
            int acertos = jogador.getAcertos();
            while(map.containsKey(acertos)) acertos++;
            list.add(acertos);
            map.put(acertos, jogador);
        }
        Collections.sort(list, Collections.reverseOrder());
        String resp = "O jogo acabou, partiu ver quem ganhou";
        int placar = 1;
        for(Integer acertos : list){
            Jogador p = map.get(acertos);
            resp = resp + "\n"+placar +" - " + p.getIdJogador() +" com "+p.getAcertos()+ " acertos";
            placar++;
        }
        resp = resp + "\n O desempate é por ordem de chegada no saguão, quem entrou por ultimo, fica na frente.";
        return resp;
    }


    @Override //Quando alguem entra no jogo
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // TODO: Implementao

            String newPlayer = configuraId(conn.getResourceDescriptor());
            if(this.connections.keySet().contains(newPlayer)){
                this.connections.get(newPlayer).send("Erro, alguem tentou usar seu nick, você sera desconectado");
                onClose(this.connections.get(newPlayer), 1002, "Erro", true);
                conn.send("Erro,você tentou usar o nick de alguem, você sera desconectado");
                conn.close();
            }
            else {
                this.connections.put(newPlayer, conn);
                this.connections.forEach((ids,conns) -> conns.send(newPlayer+" se juntou ao jogo"));
                conn.send("Bem vindo ao typerace "+newPlayer+" segue uma lista de comando fresquinha:\n-> Digite start para começar um jogo.\n-> Digite stop para terminar um jogo em andamento.\n-> Digite exit para sair do saquao quando nao estiver em um jogo.\n-> Para saber quem estah no salao, digite status\n-> Se você entrou e estah tendo um jogo, aguarde até o jogo atual terminar ou digite stop para terminar o jogo e participar do proximo\n-> Se alguem tentar entrar com o seu nick, você será desconectado");
           }
        
    }

    @Override //Pede pra sair do jogo
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // TODO: Implementar
        String player = configuraId(conn.getResourceDescriptor());
        this.connections.remove(player);
        this.jogadores.remove(player);
        conn.close(code, reason);
    }

    @Override //Quando mandam qualquer coisa para o servidor
    public void onMessage(WebSocket conn, String message) {
        // TODO: Implementar
        System.out.println(message);
        try {                
        if(message.equalsIgnoreCase("start")){
            if(!this.GameNow){ 
                //começa o jogo
                System.out.println("Vamos começar o jogo");
                for(String newPlayer : this.connections.keySet()){
                Jogador novo = new Jogador(newPlayer, new HashSet<>());
                this.jogadores.put(newPlayer, novo);
                }
                Set<String> palavras = new HashSet<>();
                palavras.add("banana");
                palavras.add("cupcake");
                palavras.add("maca");
                palavras.add("pet");
                palavras.add("redes");
                palavras.add("livro");
                palavras.add("manga");
                palavras.add("sala");
                palavras.add("ceu");
                palavras.add("paralelepipedo");
                palavras.add("estetoscopio");
                palavras.add("maria");
                palavras.add("jose");
                palavras.add("caixa-d'agua");
                palavras.add("universidade");
                palavras.add("keralux");
                palavras.add("maximiliano");
                palavras.add("computadores");
                palavras.add("pacote");
                palavras.add("uganda");
                palavras.add("togo");
                palavras.add("jogo");
                palavras.add("biblioteca");
                palavras.add("implementacao");
                palavras.add("contextualizacao");
                palavras.add("abnegacao");
                palavras.add("determinacao");
                palavras.add("foco");
                palavras.add("desproporcionadamente");
                palavras.add("orrinolaringologista");
                palavras.add("caminho");

                for(Jogador player : this.jogadores.values()){
                    if(player == null) continue;
                    player.setPalavras(palavras);
                    String send = player.PalavrasMessage();
                    String id = player.getIdJogador();
                    this.connections.get(id).send("O jogo começou, se prepara que lá vem suas palavras:\n" + send);
                    System.out.println("Começou o jogo");
                }
                this.GameNow = true;
            } 
            else conn.send("O jogo já começou\n" + this.jogadores.get(configuraId(conn.getResourceDescriptor())).PalavrasMessage());
        }
        else if(message.equalsIgnoreCase("stop")){
            //termina o jogo
            if(this.GameNow){
                this.GameNow= false;
                this.connections.forEach((ids, conns) -> conns.send(placar()));
                this.jogadores.clear();
            }
            else conn.send("Não tem nenhum jogo rolando, para iniciar um jogo, digite start");
        }
        else if(message.equalsIgnoreCase("exit")){
            //Desconecta jogador
            if(this.GameNow) conn.send("Estamos no meio de um jogo, para sair, antes pare o jogo digitando stop");
            else{
                this.connections.forEach((ids,conns) -> conns.send(configuraId(conn.getResourceDescriptor()) +" saiu do jogo"));
                onClose(conn, 1000, "Jogador pediu", true);
            }
        }
        else if(message.equalsIgnoreCase("status")){
            for(String id : this.connections.keySet()) this.connections.forEach((ids,conns) -> conns.send(id+" está no jogo"));
        }
        else{
            if(this.GameNow){
                String id = configuraId(conn.getResourceDescriptor());
                Jogador jogador = this.jogadores.get(id);
                if(jogador.verificaPalavra(message.toLowerCase()))conn.send(jogador.getIdJogador() +" Acertou");
                else conn.send("Errou");
                conn.send(jogador.PalavrasMessage());
            }
            else{
                conn.send("O jogo não começou ainda, para começar um jogo, digite start. Para sair do saguão digite exit");
            }
        }
    } catch (Exception e) {
       System.out.println(e.toString());
    }
    }

    @Override //Caso aconteça algo
    public void onError(WebSocket conn, Exception ex) {
        conn.send(ex.getMessage());
    }

    @Override //Quando o server começar
    public void onStart() {
        System.out.println("Server Online na porta " + super.getPort());
    }
}
