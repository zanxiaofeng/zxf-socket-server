package zxf.socket.nio.server.apps.nadachat;


import zxf.socket.nio.server.BufferFactory;
import zxf.socket.nio.server.impl.DumbBufferFactory;
import zxf.socket.nio.server.impl.NioDispatcher;
import zxf.socket.nio.server.impl.StandardAcceptor;
import zxf.socket.nio.server.impl.InputHandlerFactory;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NadaServer {

    public static void main(String[] args) throws IOException {
        Executor executor = Executors.newCachedThreadPool();
        BufferFactory bufFactory = new DumbBufferFactory(1024);
        NioDispatcher dispatcher = new NioDispatcher(executor, bufFactory);
        InputHandlerFactory factory = new NadaProtocol();
        StandardAcceptor acceptor = new StandardAcceptor(1234, dispatcher, factory);

        dispatcher.start();
        acceptor.startup();
    }
}
