package ru.fazziclay.fazziclaylibs.tests;

import ru.fazziclay.fazziclaylibs.ByteUtils;
import ru.fazziclay.fazziclaylibs.network.BaseClient;
import ru.fazziclay.fazziclaylibs.network.BaseConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class NetworkClientTest {
    public static boolean isRun = true;

    public static void main(String[] args) {
        BaseClient client = new BaseClient("localhost", 4003, 0, new TestClientConnectionHandler());
        client.start();

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
                try {
                    client.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } else {
                System.out.println("Unknown Command");
            }
        }
    }

    public static class TestClientConnectionHandler extends BaseConnectionHandler {

        @Override
        public void onException(BaseClient client, Exception exception) {
            TestLogger LOGGER = new TestLogger();
            LOGGER.error(exception);
            LOGGER.done();
        }

        @Override
        public void onPreConnected(BaseClient client) {
            TestLogger LOGGER = new TestLogger();
            LOGGER.done();
        }

        @Override
        public void onConnected(BaseClient client) {
            TestLogger LOGGER = new TestLogger();
            //client.send(new String(ByteUtils.getBytes(ServerPacket.PACKET_HANDSHAKE.getPacketId())) + UUID.randomUUID());
            //client.send(new String(ByteUtils.getBytes(ServerPacket.PACKET_TEST.getPacketId())) + "hello!");
            LOGGER.done();
        }

        @Override
        public void onPacketReceive(BaseClient client, String data) {
            TestLogger LOGGER = new TestLogger();
            LOGGER.info("data: "+data);
            LOGGER.done();
        }

        @Override
        public void onPreDisconnected(BaseClient client) {
            TestLogger LOGGER = new TestLogger();
            LOGGER.done();
        }

        @Override
        public void onDisconnected(BaseClient client) {
            TestLogger LOGGER = new TestLogger();
            LOGGER.done();
        }
    }

    public static enum ServerPacket {
        PACKET_DISCONNECTED((short) 1),
        PACKET_HANDSHAKE((short) 2),
        PACKET_LOGS_UPLOADING((short) 3),
        PACKET_LOGS_UPLOADING_SUCCESSES((short) 4),
        PACKET_LOGS_UPLOADING_ERROR((short) 5),
        PACKET_LOGS_CLEARING_REQUEST((short) 6),
        PACKET_UNKNOWN_FROM_SERVER_PACKET((short) 7),
        PACKET_ERROR_CAUSED_CLIENT((short) 8),
        PACKET_TEST((short) 9);

        short packetId = 0;
        ServerPacket(short packetId) {
            this.packetId = packetId;
        }

        public short getPacketId() {
            return packetId;
        }
    }

}
