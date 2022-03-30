package one.edee.oss.http_server_evaulation_test.server.nanohttpd;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;

public class HelloWorldHandler extends RouterNanoHTTPD.DefaultHandler {

    @Override
    public String getText() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getMimeType() {
        return MIME_PLAINTEXT;
    }

    @Override
    public Response.IStatus getStatus() {
        return Response.Status.OK;
    }

    @Override
    public Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
        String text = "hello world";
        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), text);
    }
}
