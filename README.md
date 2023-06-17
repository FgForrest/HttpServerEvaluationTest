# HttpServerEvaluationTest

This repository contains simple proof of concept work that evaluates different HTTP server implementations, 
their usability as a HTTP GraphQL/REST/gRPC API server, memory footprint and performance characteristics.

Benchmark consists of simple echo GraphQL API and implementations of individual HTTP servers serving the GraphQL API.
Tested servers are:

- [microhttp](https://github.com/ebarlas/microhttp)
- [NanoHTTPD](https://github.com/NanoHttpd/nanohttpd)
- [Netty](https://github.com/netty/netty)
- [Spring Boot MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web)
- [Spring Boot WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#spring-webflux)
- [Vert.x](https://github.com/eclipse-vertx/vert.x)
- [Quarkus Native](https://quarkus.io/)
- [Quarkus JVM](https://quarkus.io/)
- [Undertow](https://github.com/undertow-io/undertow)
- [Micronaut](https://micronaut.io/)
- [Javalin](https://github.com/tipsy/javalin)

## Run tests

Build from root using:
```
mvn clean install -Dnative
```
and separately build Micronaut server:
```
cd micronaut_server
mvn clean package
```

If you want native version of Quarkus build with `native` parameter`:
```
mvn clean install -Dnative
```

After that run servers separately by following shell scripts:

```
./run-javalin-server.sh
./run-microhttp-server.sh
./run-micronaut-server.sh
./run-nanohttpd-server.sh
./run-netty-server.sh
./run-quarkus-server.sh
./run-quarkus-native-server.sh
./run-spring-boot-mvc-server.sh
./run-spring-boot-web-flux-server.sh
./run-undertow-server.sh
./run-vertx-server.sh
```

Finally, run JMH benchmarks for each of the server, e.g.:

```
java -jar performance_tests/target/benchmarks.jar .*Javalin.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*MicroHTTP.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Micronaut.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*NanoHTTPD.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Netty.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Quarkus.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Quarkus.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*MVC.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Flux.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Undertow.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*VertX.* -wi 1 -i 5 -f 1 -rf json -rff results.json
```

## GraphQL API

API is simple echo query which returns passed argument.
Request:
```graphql
query Echo {
    echo(message: "hello") {
        message
    }
}
```
and response:
```json
{
  "data": {
    "echo": {
      "message": "hello"
    }
  }
}
```

It has its own Maven module `graphql` where is simple implementation of GraphQL API for easier implementation of servers.

## HTTP Servers

Servers are implemented in separate Maven modules.
Each server has `server runner` class which is responsible for starting and stopping server and mapping handlers
to endpoints.
There are implementations of `Hello world` endpoint and GraphQL API endpoint to simulate simple routing.
Each server has assigned specific port on which it will start, starting with `8081`.

Each server is started by specific command:

```
./run-javalin-server.sh
./run-microhttp-server.sh
./run-micronaut-server.sh
./run-nanohttpd-server.sh
./run-netty-server.sh
./run-quarkus-server.sh
./run-quarkus-native-server.sh
./run-spring-boot-mvc-server.sh
./run-spring-boot-web-flux-server.sh
./run-undertow-server.sh
./run-vertx-server.sh
```

## Benchmarks

For benchmarking there is Maven module `performance_tests`.
Each server has its own test where HTTP Client calls the API with request mentioned above.
The tests are built using JMH framework and measure throughput and latency in maximum possible threads.
After the server is running, benchmarks can be run by:

```
java -jar performance_tests/target/benchmarks.jar .*Javalin.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*MicroHTTP.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Micronaut.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*NanoHTTPD.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Netty.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Quarkus.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Quarkus.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*MVC.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Flux.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*Undertow.* -wi 1 -i 5 -f 1 -rf json -rff results.json
java -jar performance_tests/target/benchmarks.jar .*VertX.* -wi 1 -i 5 -f 1 -rf json -rff results.json
```

which runs single warmup iteration and 5 normal iterations and saves the results to JSON file.