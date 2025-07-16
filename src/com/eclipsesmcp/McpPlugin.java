package com.eclipsesmcp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class McpPlugin implements BundleActivator {

    private McpServer server;

    @Override
    public void start(BundleContext context) throws Exception {
        server = new McpServer();
        server.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}
