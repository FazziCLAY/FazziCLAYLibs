package ru.fazziclay.fazziclaylibs.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public abstract class BaseServer extends Thread {
    ServerSocket serverSocket = null;

    int port = 0;
    int soTimeOut = 0;
    int backlog = 0;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public int getPort() {
        return port;
    }
    public int getSoTimeOut() {
        return soTimeOut;
    }
    public int getBacklog() {
        return backlog;
    }

    public abstract void onException(Exception exception);
    public abstract void onPreStarted();
    public abstract void onStarted(int port);
    public abstract void onConnected(Socket socket);
    public abstract void onPreClosed();
    public abstract void onClosed();
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public BaseServer(int port, int soTimeOut, int backlog) {
        this.port = port;
        this.soTimeOut = soTimeOut;
        this.backlog = backlog;
    }

    public void close() throws IOException {
        onPreClosed();
        serverSocket.close();
        onClosed();
    }

    @Override
    public void run() {
        onPreStarted();
        try {
            serverSocket = new ServerSocket(port, backlog);
            serverSocket.setSoTimeout(soTimeOut);
            onStarted(serverSocket.getLocalPort());
        } catch (Exception e) {
            onException(e);
            return;
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = this.serverSocket.accept();
                onConnected(socket);

            } catch (Exception e) {
                if (e instanceof SocketException && e.getMessage().equals("Socket closed")) {
                    break;
                }
                onException(e);
            }
        }
        try {
            close();
        } catch (Exception e) {
            onException(e);
        }
    }
}
