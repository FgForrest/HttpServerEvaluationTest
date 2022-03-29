package one.edee.oss.http_server_evaulation_test.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;
import org.microhttp.Header;
import org.microhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class GraphQLHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final GraphQLManager graphQLManager;
    private final ObjectMapper objectMapper;

    public GraphQLHandler() {
        this.graphQLManager = new GraphQLManager();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }

        FullHttpRequest request = (FullHttpRequest) msg;
        HttpResponse response;

        if (!request.uri().equals("/graphql")) {
            response = createResponse(request, NOT_FOUND, "".getBytes(StandardCharsets.UTF_8));
        } else if (!request.method().equals(HttpMethod.POST)) {
            response = createResponse(request, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8));
        } else if (!(request instanceof HttpContent)) {
            response = createResponse(request, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8));
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
                response = createResponse(request, OK, json);
            } catch (JsonProcessingException e) {
                response = createResponse(request, INTERNAL_SERVER_ERROR, "".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                response = createResponse(request, BAD_REQUEST, "".getBytes(StandardCharsets.UTF_8));
            }
        }

        ChannelFuture f = ctx.write(response);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private HttpResponse createResponse(HttpRequest request, HttpResponseStatus status, byte[] body) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                request.protocolVersion(),
                status,
                Unpooled.wrappedBuffer(body)
        );
        response.headers()
                .set(CONTENT_TYPE, APPLICATION_JSON)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());
        // Tell the client we're going to close the connection.
        response.headers().set(CONNECTION, CLOSE);
        return response;
    }
}
