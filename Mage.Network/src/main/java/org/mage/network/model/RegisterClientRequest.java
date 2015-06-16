package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class RegisterClientRequest extends ServerRequest {
    private Connection connection;
    private MageVersion version;
    
    public RegisterClientRequest(Connection connection, MageVersion version) {
        this.connection = connection;
        this.version = version;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        boolean result = server.registerClient(connection, ctx.channel().id().asLongText(), version, host);
        if (result) {
            ctx.writeAndFlush(new ClientRegisteredMessage(server.getServerState())).addListener(WriteListener.getInstance());
        }
        else {
            ctx.writeAndFlush(new ClientRegisteredMessage(new ServerState())).addListener(WriteListener.getInstance());
            server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.ValidationError);
        }
    }
    
}
