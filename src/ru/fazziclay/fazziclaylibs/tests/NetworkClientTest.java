package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.network.Client;
import ru.fazziclay.fazziclaylibs.network.ConnectionHandler;

public class NetworkClientTest {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("localhost", 4005, new TestClientConnectionHandler());
        client.start();

        Thread.sleep(1000);
        while (!client.isClosed());
    }

    public static class TestClientConnectionHandler extends ConnectionHandler {

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
            client.send("Hello!!! myList:\n * one\\1\n * two\\\\2");
        }

        @Override
        public void onException(Client client, Exception throwable) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.error(throwable);
        }
    }
}
