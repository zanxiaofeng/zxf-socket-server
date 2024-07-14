/*
 * Copyright (c) 2004-2006 Ronsoft Technologies (http://ronsoft.com)
 * Contact Ron Hitchens (ron@ronsoft.com) with questions about this code.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * The use of the Apache License does not indicate that this project is
 * affiliated with the Apache Software Foundation.
 */

package zxf.socket.nio.server.apps.echo;

import zxf.socket.nio.server.BufferFactory;
import zxf.socket.nio.server.impl.DumbBufferFactory;
import zxf.socket.nio.server.impl.GenericInputHandlerFactory;
import zxf.socket.nio.server.impl.NioDispatcher;
import zxf.socket.nio.server.impl.StandardAcceptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        Executor executor = Executors.newCachedThreadPool();
        BufferFactory bufFactory = new DumbBufferFactory(1024);
        NioDispatcher dispatcher = new NioDispatcher(executor, bufFactory);
        StandardAcceptor acceptor = new StandardAcceptor(1234, dispatcher,
                new GenericInputHandlerFactory(EchoHandler.class));

        dispatcher.start();
        acceptor.startup();
    }
}
