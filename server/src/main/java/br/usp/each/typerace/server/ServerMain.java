package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ServerMain {

    private WebSocketServer server;

    public ServerMain(WebSocketServer server) {
        this.server = server;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public void init() {
        LOGGER.info("Iniciando servidor...");
        // TODO: Implementar
    }

    public static void main(String[] args) {
        WebSocketServer server = new Server(8080, new HashMap<>());

        ServerMain main = new ServerMain(server);

        main.init();
    }

}
