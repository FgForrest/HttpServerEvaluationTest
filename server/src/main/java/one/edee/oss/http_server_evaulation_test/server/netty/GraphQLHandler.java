package one.edee.oss.http_server_evaulation_test.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class GraphQLHandler implements PathHandler {

    private static final GraphQLManager graphQLManager = new GraphQLManager();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void channelRead(ChannelHandlerContext ctx, HttpObject msg) {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }

        FullHttpRequest request = (FullHttpRequest) msg;
        HttpResponse response;

        if (!request.uri().equals("/graphql")) {
            response = createResponse(ctx, request, NOT_FOUND, "".getBytes(StandardCharsets.UTF_8));
        } else if (!request.method().equals(HttpMethod.POST)) {
            response = createResponse(ctx, request, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8));
        } else if (!(request instanceof HttpContent)) {
            response = createResponse(ctx, request, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8));
        } else {
            try {
                // translate request
                byte[] body = new byte[request.content().readableBytes()];
                request.content().readBytes(body);
                final GraphQLRequest graphQLRequest = objectMapper.readValue(body, GraphQLRequest.class);

                // execute request
                final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

                // send response
                final byte[] json = objectMapper.writeValueAsBytes(graphQLResponse);
                response = createResponse(ctx, request, OK, json);
            } catch (JsonProcessingException e) {
                response = createResponse(ctx, request, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                response = createResponse(ctx, request, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8));
            }
        }

        ChannelFuture f = ctx.write(response);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    private HttpResponse createResponse(ChannelHandlerContext ctx, HttpRequest request, HttpResponseStatus status, byte[] body) {
        final ByteBuf bodyBuffer = ctx.alloc().buffer(body.length);
        bodyBuffer.writeBytes(body);
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.protocolVersion(),
                status,
                bodyBuffer
        );
        response.headers()
                .set(CONTENT_TYPE, APPLICATION_JSON)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());
        // Tell the client we're going to close the connection.
        response.headers().set(CONNECTION, CLOSE);
        return response;
    }
}
