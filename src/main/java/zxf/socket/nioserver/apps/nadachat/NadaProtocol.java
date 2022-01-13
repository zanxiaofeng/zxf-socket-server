package zxf.socket.nioserver.apps.nadachat;

import zxf.socket.nioserver.ChannelFacade;
import zxf.socket.nioserver.InputHandler;
import zxf.socket.nioserver.impl.InputHandlerFactory;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NadaProtocol implements InputHandlerFactory {
    private Map<ChannelFacade, NadaUser> users = new ConcurrentHashMap<>();

    public InputHandler newHandler() throws IllegalAccessException, InstantiationException {
        return new NadaHandler(this);
    }

    void newUser(ChannelFacade facade) {
        NadaUser user = new NadaUser(facade);

        users.put(facade, user);
        user.send(ByteBuffer.wrap((user.getNickName() + "\n").getBytes()));
    }

    void endUser(ChannelFacade facade) {
        users.remove(facade);
    }

    public void handleMessage(ChannelFacade facade, ByteBuffer message) {
        broadcast(users.get(facade), message);
    }

    private void broadcast(NadaUser sender, ByteBuffer message) {
        synchronized (users) {
            for (NadaUser user : users.values()) {
                if (user != sender) {
                    sender.sendTo(user, message);
                }
            }
        }
    }

    private static class NadaUser {
        private final ChannelFacade facade;
        private String nickName;
        private ByteBuffer prefix = null;
        private static int counter = 1;

        public NadaUser(ChannelFacade facade) {
            this.facade = facade;
            setNickName("nick-" + counter++);
        }

        public void send(ByteBuffer message) {
            facade.outputQueue().enqueue(message.asReadOnlyBuffer());
        }

        public void sendTo(NadaUser recipient, ByteBuffer message) {
            recipient.send(prefix);
            recipient.send(message);
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;

            String prefixStr = "[" + nickName + "] ";

            prefix = ByteBuffer.wrap(prefixStr.getBytes());
        }
    }
}
