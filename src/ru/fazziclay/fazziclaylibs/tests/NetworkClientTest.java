package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.network.Client;
import ru.fazziclay.fazziclaylibs.network.ConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NetworkClientTest {
    public static boolean isRun = true;
    public static List<Client> clients = new ArrayList<>();

    public static void main(String[] args) {
        int i = 0;
        while (i < 10000) {
            Client client = new Client("localhost", 4003, 0, new TestClientConnectionHandler());
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
            isRun = false;
        }

        @Override
        public void onPreDisconnected(Client client) {

        }

        @Override
        public void onConnected(Client client) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.info("client="+client.toString());
            client.send("Hello!!! myList:\n * one\\1\n * two\\\\2");
        }

        @Override
        public void onPreConnected(Client client) {

        }

        @Override
        public void onException(Client client, Exception throwable) {
            LoggerTest.TestLogger logger = new LoggerTest.TestLogger();
            logger.error(throwable);
        }
    }
}
