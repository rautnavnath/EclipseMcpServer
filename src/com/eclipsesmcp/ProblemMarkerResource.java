package com.eclipsesmcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.sdk.McpContext;
import io.modelcontextprotocol.sdk.McpServerFeatures;
import io.modelcontextprotocol.sdk.McpSyncServerExchange;
import io.modelcontextprotocol.sdk.dsl.McpSchema;
import io.modelcontextprotocol.sdk.dsl.McpSchema.ReadResourceRequest;
import io.modelcontextprotocol.sdk.dsl.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.sdk.dsl.McpSchema.Resource;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import java.util.ArrayList;
import java.util.List;

public class ProblemMarkerResource implements McpServerFeatures.SyncResourceSpecification {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Resource getResource() {
        return new Resource("eclipse://markers/problems", "Problem Markers", "Provides a list of problem markers in the workspace", "application/json", null);
    }

    @Override
    public ReadResourceResult read(McpSyncServerExchange exchange, ReadResourceRequest request) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        List<ProblemMarker> markers = new ArrayList<>();
        try {
            IMarker[] problems = root.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
            for (IMarker marker : problems) {
                markers.add(new ProblemMarker(
                        marker.getAttribute(IMarker.MESSAGE, ""),
                        marker.getResource().getFullPath().toString(),
                        marker.getAttribute(IMarker.LINE_NUMBER, -1),
                        getSeverity(marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO))
                ));
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }

        try {
            return new ReadResourceResult(objectMapper.writeValueAsString(markers));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ReadResourceResult("[]");
        }
    }

    private String getSeverity(int severity) {
        switch (severity) {
            case IMarker.SEVERITY_ERROR:
                return "Error";
            case IMarker.SEVERITY_WARNING:
                return "Warning";
            case IMarker.SEVERITY_INFO:
                return "Info";
            default:
                return "Unknown";
        }
    }
}
