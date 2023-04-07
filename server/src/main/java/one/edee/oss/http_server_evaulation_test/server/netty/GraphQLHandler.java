package one.edee.oss.http_server_evaulation_test.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class GraphQLHandler implements PathHandler {

    private static final GraphQLManager graphQLManager = new GraphQLManager();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void channelRead(ChannelHandlerContext ctx, HttpObject msg) {
        if (!(msg instanceof HttpRequest request)) {
            ctx.fireChannelRead(msg);
            return;
        }

        if (!request.uri().equals("/graphql")) {
            ctx.writeAndFlush(createResponse(ctx, request, NOT_FOUND, "".getBytes(StandardCharsets.UTF_8)));
        } else if (!request.method().equals(HttpMethod.POST)) {
            ctx.writeAndFlush(createResponse(ctx, request, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8)));
        } else if (!(request instanceof HttpContent)) {
            ctx.writeAndFlush(createResponse(ctx, request, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8)));
        } else if (request instanceof FullHttpRequest fullHttpRequest) {
            final byte[] body = new byte[fullHttpRequest.content().readableBytes()];
            fullHttpRequest.content().readBytes(body);
            ctx.executor().execute(() -> {
                HttpResponse response;
                try {
                    // translate request
                    final GraphQLRequest graphQLRequest = objectMapper.readValue(body, GraphQLRequest.class);

                    // execute request
                    final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

                    // send response
                    final byte[] json = objectMapper.writeValueAsBytes(graphQLResponse);
                    response = createResponse(ctx, fullHttpRequest, OK, json);
                } catch (JsonProcessingException e) {
                    response = createResponse(ctx, fullHttpRequest, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    response = createResponse(ctx, fullHttpRequest, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8));
                }
                ctx.writeAndFlush(response);
            });

        }
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
        return response;
    }
}
