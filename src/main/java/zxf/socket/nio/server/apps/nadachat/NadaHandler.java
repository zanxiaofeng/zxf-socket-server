package zxf.socket.nio.server.apps.nadachat;

import zxf.socket.nio.server.ChannelFacade;
import zxf.socket.nio.server.InputHandler;
import zxf.socket.nio.server.InputQueue;

import java.nio.ByteBuffer;

public class NadaHandler implements InputHandler {
    private final NadaProtocol protocol;

    public NadaHandler(NadaProtocol protocol) {
        this.protocol = protocol;
    }

    public ByteBuffer nextMessage(ChannelFacade channelFacade) {
        InputQueue inputQueue = channelFacade.inputQueue();
        int nlPos = inputQueue.indexOf((byte) '\n');

        if (nlPos == -1) return null;

        if ((nlPos == 1) && (inputQueue.indexOf((byte) '\r') == 0)) {
            inputQueue.discardBytes(2);    // eat CR/NL by itself
            return null;
        }

        return (inputQueue.dequeueBytes(nlPos + 1));
    }

    public void handleInput(ByteBuffer message, ChannelFacade channelFacade) {
        protocol.handleMessage(channelFacade, message);
    }

    public void starting(ChannelFacade channelFacade) {
        System.out.println("NadaHandler: starting");
    }

    public void started(ChannelFacade channelFacade) {
        protocol.newUser(channelFacade);
    }

    public void stopping(ChannelFacade channelFacade) {
        System.out.println("NadaHandler: stopping");
    }

    public void stopped(ChannelFacade channelFacade) {
        protocol.endUser(channelFacade);
    }
}
