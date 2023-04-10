package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.Request;
import org.microhttp.Response;

public interface PathHandler {

    Response handle(Request request);
}
