package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.UUID;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }
    //O id gerado para o cliente
    private String id = UUID.randomUUID().toString();

    private static int MAX_RETRY = 5;

    //método para abrir conexão com o server, passando o seu id por um header
    private void openConnection(){
        this.addHeader("clientId", id);
        this.connect();
    }

    public String getId(){
        return this.id;
    }
    //Exibição do status HTTP e mensagem, ao abrir uma conexão com o server
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conexão com o servidor aberta: \n" +
                "+ Status: "+handshakedata.getHttpStatus()+"\n" +
                "+ Mensagem: "+handshakedata.getHttpStatusMessage());
    }
    //as mensagens exibidas são: ou de informação da quantidade de jogadores, ou da lista de palavras
    // ao início de um jogo, ou da classificação ao término de um jogo
    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
    //Exibição dos parâmetros (motivo, código, quem fechou a conexão), ao se fechar uma conexão
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Fechando conexão com o servidor: \n" +
                "+ Servidor terminou conexão: "+remote +"\n" +
                "+ Razão: "+reason+"\n");
        if(code == 405){
            id = UUID.randomUUID().toString();
            openConnection();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Erro na conexão com o servidor: \n" +
                "+ Exceção: "+ex.getMessage()+"\n");
    }
}
