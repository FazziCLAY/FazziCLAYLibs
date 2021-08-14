package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.network.BaseClient;
import ru.fazziclay.fazziclaylibs.network.BaseConnectionHandler;
import ru.fazziclay.fazziclaylibs.network.BaseServer;

import java.io.IOException;
import java.net.Socket;

public class NetworkServerTest {
    public static void main(String[] args) throws InterruptedException {
        LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        TestServer server = new TestServer(4005, 0, 0);
        server.start();

        Thread.sleep(1000);
        while (!server.isClosed());
    }

    public static class TestServer extends BaseServer {

        @Override
        public void close() {
            try {
                super.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public TestServer(int port, int soTimeOut, int backlog) {
            super(port, soTimeOut, backlog);
        }

        @Override
        public void onException(Exception exception) {

        }

        @Override
        public void onPreStarted() {

        }

        @Override
        public void onStarted(int port) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        }

        @Override
        public void onConnected(Socket socket) {

        }

        @Override
        public void onPreClosed() {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
        }

        @Override
        public void onClosed() {

        }
    }

    public static class TestServerConnectionHandler extends BaseConnectionHandler {

        @Override
        public void onPacketReceive(BaseClient client, String data) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
            logger.info("data="+data.toString());
        }

        @Override
        public void onDisconnected(BaseClient client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
        }

        @Override
        public void onPreDisconnected(BaseClient client) {

        }

        @Override
        public void onConnected(BaseClient client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
        }

        @Override
        public void onPreConnected(BaseClient client) {

        }

        @Override
        public void onException(BaseClient client, Exception throwable) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.error(throwable);
        }
    }
}
