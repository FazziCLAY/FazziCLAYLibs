package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.network.Client;
import ru.fazziclay.fazziclaylibs.network.ConnectionHandler;
import ru.fazziclay.fazziclaylibs.network.Server;

import java.io.IOException;

public class NetworkServerTest {
    public static void main(String[] args) throws InterruptedException {
        LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        TestServer server = new TestServer(4005, new TestServerConnectionHandler());
        server.start();

        Thread.sleep(1000);
        while (!server.isClosed());
    }

    public static class TestServer extends Server {
        public TestServer(int port, ConnectionHandler connectionHandler) {
            super(port, connectionHandler);
        }

        @Override
        public void onError(IOException exception) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.error(exception);
        }

        @Override
        public void onStarted() {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        }

        @Override
        public void onPreClosed() {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        }
    }

    public static class TestServerConnectionHandler extends ConnectionHandler {

        @Override
        public void onPacketReceive(Client client, String data) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
            logger.info("data="+data.toString());
        }

        @Override
        public void onDisconnected(Client client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
        }

        @Override
        public void onConnected(Client client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
        }

        @Override
        public void onException(Client client, Exception throwable) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.error(throwable);
        }
    }
}
