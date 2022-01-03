package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerMain {

    private WebSocketServer server;
    private static final String PATH_TO_SETTINGS = "./src/main/resources/serverSettings.json";

    public ServerMain(WebSocketServer server) {
        this.server = server;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public void init() {
        LOGGER.info("Iniciando servidor...");
        // TODO: Implementar
    }

    public static void main(String[] args) {

        File file = new File(PATH_TO_SETTINGS);
        String content = "";

        try {
            content = FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(content);
        int port = json.getInt("port");

        LOGGER.info("the port is: "+port);

        WebSocketServer server = new Server(port, new HashMap<>());

        ServerMain main = new ServerMain(server);

        main.init();
    }

}
