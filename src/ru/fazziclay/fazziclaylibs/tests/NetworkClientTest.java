package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.network.BaseClient;
import ru.fazziclay.fazziclaylibs.network.BaseConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NetworkClientTest {
    public static boolean isRun = true;
    public static List<BaseClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        int i = 0;
        while (i < 10000) {
            BaseClient client = new BaseClient("localhost", 4003, 0, new TestClientConnectionHandler());
            client.start();
            clients.add(client);
            i++;
        }

        System.out.println("All clients connected!");

        BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
        while (isRun) {
            String command;
            String[] commandArgs;
            try {
                commandArgs = commandReader.readLine().split(" ");
                command = commandArgs[0];
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (command.equals("close")) {
                if (commandArgs.length >= 2 && commandArgs[1].equals("all")) {
                    while (!clients.isEmpty()) {
                        clients.get(0).close();
                        clients.remove(clients.get(0));
                    }
                    break;
                } else {
                    System.out.println("Usage: close all");
                }
            } else {
                System.out.println("Unknown Command");
            }
        }
    }

    public static class TestClientConnectionHandler extends BaseConnectionHandler {

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
            isRun = false;
        }

        @Override
        public void onPreDisconnected(BaseClient client) {

        }

        @Override
        public void onConnected(BaseClient client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
            client.send("Hello!!! myList:\n * one\\1\n * two\\\\2");
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
