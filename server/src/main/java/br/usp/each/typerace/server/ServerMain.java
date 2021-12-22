package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;

import java.util.HashMap;

public class ServerMain {

    private WebSocketServer server;

    public ServerMain(WebSocketServer server) {
        this.server = server;
    }

    public void init() {
        System.out.println("Iniciando servidor...");
        server.start();
    }

    public static void main(String[] args) {

        WebSocketServer server = new Server(Integer.parseInt(args[0]), new HashMap<>());//Pega a porta do servidor através de argumento

        ServerMain main = new ServerMain(server);

        main.init();       
    }

}
