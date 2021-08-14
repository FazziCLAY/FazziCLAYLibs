package ru.fazziclay.fazziclaylibs.network;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    protected String host;
    protected int port;
    protected int soTimeOut = 0;
    protected ConnectionHandler connectionHandler;

    protected Server server = null;
    protected Socket socket = null;
    protected BufferedReader inputStream;
    protected PrintWriter outputStream;

    public Client(String host, int port, int soTimeOut, ConnectionHandler connectionHandler) {
        this.host = host;
        this.port = port;
        this.soTimeOut = soTimeOut;
        this.connectionHandler = connectionHandler;
    }

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.connectionHandler = server.getConnectionHandler();
    }

    public void close() {
        if (server != null) server.getConnectionList().remove(this);
        connectionHandler.onPreDisconnected(this);
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception ignored) {}
        connectionHandler.onDisconnected(this);
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void send(String str) {
        outputStream.write(str.replace("\\", "\\\\").replace("\n", "\\n") + "\n");
        outputStream.flush();
    }

    @Override
    public void run() {
        try {
            connectionHandler.onPreConnected(this);

            if (socket == null) socket = new Socket(host, port);
            socket.setSoTimeout(soTimeOut);
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            connectionHandler.onConnected(this);
        } catch (Exception e) {
            connectionHandler.onException(this, e);
            return;
        }

        while (socket.isConnected()) {
            try {
                String received = inputStream.readLine();
                if (received == null) break;
                received = received.replace("\\n", "\n").replace("\\\\", "\\");
                connectionHandler.onPacketReceive(this, received);
            } catch (IOException e) {
                if (e.getMessage().equals("Socket closed")) {
                    break;
                }
                connectionHandler.onException(this, e);
                break;
            }
        }
        close();
    }
}