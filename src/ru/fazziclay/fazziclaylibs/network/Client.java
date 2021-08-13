package ru.fazziclay.fazziclaylibs.network;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    protected String host;
    protected int port;
    protected ConnectionHandler connectionHandler;

    protected Socket socket = null;
    protected BufferedReader inputStream;
    protected PrintWriter outputStream;

    public Client(String host, int port, ConnectionHandler connectionHandler) {
        this.host = host;
        this.port = port;
        this.connectionHandler = connectionHandler;
    }

    public Client(Socket socket, ConnectionHandler connectionHandler) {
        this.socket = socket;
        this.connectionHandler = connectionHandler;
    }

    public void close() {
        connectionHandler.onDisconnected(this);
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception ignored) {}
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
            if (socket == null) socket = new Socket(host, port);
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