package zxf.socket.nioserver.impl;

import zxf.socket.nioserver.InputHandler;

public interface InputHandlerFactory {
    InputHandler newHandler() throws IllegalAccessException, InstantiationException;
}
