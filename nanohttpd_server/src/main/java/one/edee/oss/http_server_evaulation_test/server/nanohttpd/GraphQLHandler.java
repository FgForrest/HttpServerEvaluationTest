package one.edee.oss.http_server_evaulation_test.server.nanohttpd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class GraphQLHandler extends RouterNanoHTTPD.DefaultHandler {

    private static final int REQUEST_BUFFER_LEN = 512;
    private static final int MEMORY_STORE_LIMIT = 1024;

    private static final GraphQLManager graphQLManager = new GraphQLManager();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getText() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getMimeType() {
        return "application/json";
    }

    @Override
    public Response.IStatus getStatus() {
        return Response.Status.OK;
    }

    @Override
    public Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
        if (!session.getMethod().equals(NanoHTTPD.Method.POST)) {
            return newFixedLengthResponse(Response.Status.BAD_REQUEST, "Only POST method is supported", "");
        }

        String body = readBody(session);
        // translate request
        final GraphQLRequest graphQLRequest;
        try {
            graphQLRequest = objectMapper.readValue(body, GraphQLRequest.class);
        } catch (IOException e) {
            return newFixedLengthResponse(Response.Status.BAD_REQUEST, "application/json", "");
        }
        final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

        // send response
        final String json;
        try {
            json = objectMapper.writeValueAsString(graphQLResponse);
        } catch (JsonProcessingException e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "application/json", "");
        }
        return newFixedLengthResponse(getStatus(), getMimeType(), json);
    }

    private String readBody(IHTTPSession session) {
        // copied from core source code
        int rlen = 0;

        long size = getBodySize(session);
        ByteArrayOutputStream baos;
        DataOutput requestDataOutput;

        // Store the request in memory or a file, depending on size
        if (size < MEMORY_STORE_LIMIT) {
            baos = new ByteArrayOutputStream();
            requestDataOutput = new DataOutputStream(baos);
        } else {
            throw new RuntimeException("payload too large");
        }

        // Read all the body and write it to request_data_output
        byte[] buf = new byte[REQUEST_BUFFER_LEN];
        try {
            while (rlen >= 0 && size > 0) {
                rlen = session.getInputStream().read(buf, 0, (int) Math.min(size, REQUEST_BUFFER_LEN));
                size -= rlen;
                if (rlen > 0) {
                    requestDataOutput.write(buf, 0, rlen);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String postLine = baos.toString(StandardCharsets.UTF_8);
        return postLine;
    }

    private long getBodySize(IHTTPSession session) {
        if (session.getHeaders().containsKey("content-length")) {
            return Long.parseLong(session.getHeaders().get("content-length"));
        }
        return 0;
    }
}
