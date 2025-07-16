package com.eclipsesmcp;

import io.modelcontextprotocol.sdk.McpSyncServer;
import io.modelcontextprotocol.sdk.transport.StdioServerTransportProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class McpServer {

    private McpSyncServer server;

    public void start() {
        StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(new ObjectMapper());
        server = McpSyncServer.builder(transportProvider)
                .serverInfo("eclipse-mcp-server", "1.0.0")
                .resources(new ProblemMarkerResource())
                .build();
        // The server will be started in a new thread to avoid blocking the UI thread.
        new Thread(() -> {
            try {
                server.listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        if (server != null) {
            try {
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
