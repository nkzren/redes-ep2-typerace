package br.usp.each.typerace.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientMainTest {

    @Mock
    Client clientMock;

    ClientMain subject;

    @BeforeEach
    public void setup() {
        this.clientMock = mock(Client.class);
        subject = new ClientMain(clientMock);
    }

    @Test
    void deveSeConectarAUmServidor() {
        subject.init("testId");

        verify(clientMock).connect();
    }
}