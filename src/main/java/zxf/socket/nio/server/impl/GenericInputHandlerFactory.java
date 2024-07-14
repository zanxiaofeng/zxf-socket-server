package zxf.socket.nio.server.impl;

import zxf.socket.nio.server.InputHandler;

public class GenericInputHandlerFactory implements InputHandlerFactory {
    private final Class<? extends InputHandler> handlerClass;

    public GenericInputHandlerFactory(Class<? extends InputHandler> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public InputHandler newHandler() throws IllegalAccessException, InstantiationException {
        return handlerClass.newInstance();
    }
}
