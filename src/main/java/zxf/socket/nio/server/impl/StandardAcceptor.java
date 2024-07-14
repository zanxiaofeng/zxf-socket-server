package zxf.socket.nio.server.impl;

import zxf.socket.nio.server.Dispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StandardAcceptor {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean running = true;
    private final ServerSocketChannel listenSocket;
    private final Dispatcher dispatcher;
    private final InputHandlerFactory inputHandlerFactory;

    public StandardAcceptor(ServerSocketChannel listenSocket, Dispatcher dispatcher, InputHandlerFactory inputHandlerFactory) {
        this.listenSocket = listenSocket;
        this.dispatcher = dispatcher;
        this.inputHandlerFactory = inputHandlerFactory;
    }

    public StandardAcceptor(SocketAddress socketAddress, Dispatcher dispatcher, InputHandlerFactory inputHandlerFactory) throws IOException {
        this(ServerSocketChannel.open().bind(socketAddress), dispatcher, inputHandlerFactory);
    }

    public StandardAcceptor(int port, Dispatcher dispatcher, InputHandlerFactory inputHandlerFactory) throws IOException {
        this(new InetSocketAddress(port), dispatcher, inputHandlerFactory);
    }

    public synchronized Thread startup() {
        Thread thread = new Thread(this::acceptLoop);
        threads.add(thread);
        thread.start();
        return thread;
    }

    public synchronized void shutdown() {
        running = false;

        for (Iterator it = threads.iterator(); it.hasNext(); ) {
            Thread thread = (Thread) it.next();
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }

        for (Iterator it = threads.iterator(); it.hasNext(); ) {
            Thread thread = (Thread) it.next();

            try {
                thread.join();
            } catch (InterruptedException e) {
                // nothing
            }

            it.remove();
        }

        try {
            listenSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Caught an exception shutting down", e);
        }
    }

    private void acceptLoop() {
        while (running) {
            try {
                SocketChannel client = listenSocket.accept();
                if (client == null) {
                    continue;
                }

                dispatcher.registerChannel(client, inputHandlerFactory.newHandler());

            } catch (ClosedByInterruptException e) {
                logger.fine("ServerSocketChannel closed by interrupt: " + e);
                return;

            } catch (ClosedChannelException e) {
                logger.log(Level.SEVERE, "Exiting, serverSocketChannel is closed: " + e, e);
                return;

            } catch (Throwable t) {
                logger.log(Level.SEVERE, "Exiting, Unexpected Throwable doing accept: " + t, t);

                try {
                    listenSocket.close();
                } catch (Throwable e1) {
                    /* nothing */
                }

                return;
            }
        }
    }
}
