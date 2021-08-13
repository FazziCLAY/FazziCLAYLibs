package ru.fazziclay.fazziclaylibs.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Server extends Thread {
    ServerSocket serverSocket;

    int port;
    ConnectionHandler connectionHandler;
    List<Client> connectionList;

    public List<Client> getConnectionList() {
        return connectionList;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public abstract void onError(IOException exception);
    public abstract void onStarted();
    public abstract void onPreClosed();
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public Server(int port, ConnectionHandler connectionHandler) {
        this.port = port;
        this.connectionHandler = connectionHandler;
        this.connectionList = new ArrayList<>();
    }

    public void close() {
        onPreClosed();
        try {
            serverSocket.close();
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            onStarted();
        } catch (IOException e) {
            onError(e);
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = this.serverSocket.accept();
                Client client = new Client(socket, connectionHandler);
                client.start();
                connectionList.add(client);

            } catch (IOException e) {
                if (e.getMessage().equals("Socket closed")) {
                    break;
                }
                onError(e);
            }
        }
        close();
    }
}
