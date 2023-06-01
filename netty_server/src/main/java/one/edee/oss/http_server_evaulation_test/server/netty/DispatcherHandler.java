package one.edee.oss.http_server_evaulation_test.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;

public class DispatcherHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static final Map<String, PathHandler> handlers = new HashMap<>() {
		@Serial private static final long serialVersionUID = -1086557078624337728L;
		{
			put("/hello", new HelloWorldHandler());
			put("/graphql", new GraphQLHandler());
		}
	};

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
		if (msg instanceof HttpRequest req) {
			final PathHandler handler = handlers.get(req.uri());
			if (handler == null) {
				FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), NOT_FOUND);
				response.headers()
					.set(CONTENT_TYPE, APPLICATION_JSON)
					.setInt(CONTENT_LENGTH, response.content().readableBytes());
				ctx.write(response);
				return;
			}

			handler.channelRead(ctx, msg);
		}
	}
}
