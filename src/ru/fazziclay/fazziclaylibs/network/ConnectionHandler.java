package ru.fazziclay.fazziclaylibs.network;

public abstract class ConnectionHandler {
    public abstract void onException(Client client, Exception exception);
    public abstract void onConnected(Client client);
    public abstract void onPreConnected(Client client);
    public abstract void onPacketReceive(Client client, String data);
    public abstract void onDisconnected(Client client);
    public abstract void onPreDisconnected(Client client);
}
