package ru.fazziclay.fazziclaylibs.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Server extends Thread {
    ServerSocket serverSocket;

    int port;
    int soTimeOut;
    int backlog;
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
    public abstract void onPreStarted();
    public abstract void onStarted(int port);
    public abstract void onPreClosed();
    public abstract void onClosed();
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public Server(int port, ConnectionHandler connectionHandler, int soTimeOut, int backlog) {
        this.port = port;
        this.connectionHandler = connectionHandler;
        this.connectionList = new ArrayList<>();
        this.soTimeOut = soTimeOut;
        this.backlog = backlog;
    }

    public void close() {
        onPreClosed();
        try {
            serverSocket.close();
        } catch (IOException ignored) {}
        onClosed();
    }

    @Override
    public void run() {
        onPreStarted();
        try {
            serverSocket = new ServerSocket(port, backlog);
            serverSocket.setSoTimeout(soTimeOut);
            onStarted(serverSocket.getLocalPort());
        } catch (IOException e) {
            onError(e);
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = this.serverSocket.accept();
                Client client = new Client(socket, this);
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
