package com.eclipsesmcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.sdk.McpClient;
import io.modelcontextprotocol.sdk.McpContext;
import io.modelcontextprotocol.sdk.McpSyncClient;
import io.modelcontextprotocol.sdk.dsl.McpSchema;
import io.modelcontextprotocol.sdk.transport.StdioClientTransportProvider;

import java.util.concurrent.ExecutionException;

public class TestClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StdioClientTransportProvider transportProvider = new StdioClientTransportProvider(new ObjectMapper());
        McpSyncClient client = McpClient.sync(transportProvider).build();
        client.initialize().get();
        McpSchema.ReadResourceResult result = client.readResource("eclipse://markers/problems").get();
        System.out.println(result.getContent());
        client.close();
    }
}
