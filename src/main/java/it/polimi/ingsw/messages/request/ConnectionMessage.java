package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;

public class ConnectionMessage implements Message {
    private final String ip;
    private final int port;

    public ConnectionMessage(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ConnectionMessage{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
