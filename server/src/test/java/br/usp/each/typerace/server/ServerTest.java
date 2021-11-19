package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ServerTest {

    @Mock
    private Map<String, WebSocket> connections;

    @Mock
    private WebSocket mockConnection;

    @InjectMocks
    private Server subject;

    @BeforeEach
    public void setup() {
        connections = new HashMap<>();
        subject = new Server(8080, this.connections);
    }

    @Test
    public void deveArmazenarConexoesAbertas() {
        ClientHandshake mockHandshake = mock(ClientHandshake.class);

        subject.onOpen(mockConnection, mockHandshake);

        assertEquals(1, connections.size());
        verify(mockConnection, times(1)).getResourceDescriptor();
    }

    @Test
    public void deveRemoverConexoesFechadas() {
        connections.put("test", mockConnection);

        subject.onClose(mockConnection, 0, "Algum motivo", true);

        assertEquals(0, connections.size());
        verify(mockConnection, times(1)).getResourceDescriptor();
    }

    @Test
    public void deveTratarExcecoesDoServidor() {
        Exception exception = new Exception("Nao deve ser lancada");

        try {
            subject.onError(mockConnection, exception);
        } catch (Exception e) {
            fail(e);
        }
    }

}