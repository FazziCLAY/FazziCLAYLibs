package ru.fazziclay.fazziclaylibs.network;

public abstract class BaseConnectionHandler {
    public abstract void onException(BaseClient client, Exception exception);
    public abstract void onPreConnected(BaseClient client);
    public abstract void onConnected(BaseClient client);
    public abstract void onPacketReceive(BaseClient client, String data);
    public abstract void onPreDisconnected(BaseClient client);
    public abstract void onDisconnected(BaseClient client);
}
