package zxf.socket.nio.server.impl;

import zxf.socket.nio.server.InputHandler;

public interface InputHandlerFactory {
    InputHandler newHandler() throws IllegalAccessException, InstantiationException;
}
