package zxf.socket.nioserver.apps.nadachat;


import zxf.socket.nioserver.BufferFactory;
import zxf.socket.nioserver.impl.DumbBufferFactory;
import zxf.socket.nioserver.impl.InputHandlerFactory;
import zxf.socket.nioserver.impl.NioDispatcher;
import zxf.socket.nioserver.impl.StandardAcceptor;

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
        acceptor.newThread();
    }
}
