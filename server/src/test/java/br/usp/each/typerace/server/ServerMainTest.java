package br.usp.each.typerace.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServerMainTest {

    Server serverMock;

    ServerMain subject;

    @BeforeEach
    void setup() {
        this.serverMock = mock(Server.class);
        subject = new ServerMain(this.serverMock);
    }

    @Test
    void deveIniciarUmServidor() {
        subject.init();

        verify(serverMock).start();
    }
}