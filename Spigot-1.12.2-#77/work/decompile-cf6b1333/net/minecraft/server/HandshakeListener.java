package net.minecraft.server;

public class HandshakeListener implements PacketHandshakingInListener {

    private final MinecraftServer a;
    private final NetworkManager b;

    public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.a = minecraftserver;
        this.b = networkmanager;
    }

    public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
        switch (packethandshakinginsetprotocol.a()) {
        case LOGIN:
            this.b.setProtocol(EnumProtocol.LOGIN);
            ChatMessage chatmessage;

            if (packethandshakinginsetprotocol.b() > 340) {
                chatmessage = new ChatMessage("multiplayer.disconnect.outdated_server", new Object[] { "1.12.2"});
                this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
                this.b.close(chatmessage);
            } else if (packethandshakinginsetprotocol.b() < 340) {
                chatmessage = new ChatMessage("multiplayer.disconnect.outdated_client", new Object[] { "1.12.2"});
                this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
                this.b.close(chatmessage);
            } else {
                this.b.setPacketListener(new LoginListener(this.a, this.b));
            }
            break;

        case STATUS:
            this.b.setProtocol(EnumProtocol.STATUS);
            this.b.setPacketListener(new PacketStatusListener(this.a, this.b));
            break;

        default:
            throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
        }

    }

    public void a(IChatBaseComponent ichatbasecomponent) {}
}
