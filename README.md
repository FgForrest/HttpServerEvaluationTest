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

After that run servers in parallel:
```
./run-servers.sh
./run-spring-boot-mvc-server.sh
./run-spring-boot-web-flux-server.sh
./run-quarkus-server.sh
./run-micronaut-server.sh
```

Finally, run JMH benchmarks, e.g.:
```
java -jar performance_tests/target/benchmarks.jar -wi 1 -i 5 -f 1 -rf json -rff results.json
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

Servers are implemented in Maven module `server`.
Each server has `server runner` class which is responsible for starting and stopping server and mapping handlers
to endpoints.
There are implementations of `Hello world` endpoint and GraphQL API endpoint to simulate simple routing.
Each server has assigned specific port on which it will start, starting with `8081`.
This is to enable parallel automatic testing of all servers.

For running there is class `one.edee.oss.http_server_evaulation_test.RunServers` which starts and stops all servers
which simple command, each in its own daemon thread if needed.
Therefore, to run servers just run:
```
./run-servers.sh
```

There are, however, some exception where some servers cannot be easily run embedded in custom workflow.
Those are:

- Spring Boot MVC
- Spring Boot WebFlux
- Quarkus
- Micronaut

These servers have their own build and run workflows, so they must be build and run separately using respective scripts.
Spring servers inherit code from main `server` module and only modify build flow, but Quarkus and Micronaut
have whole implementations in their module as they are not simple to run cross module.
On top of that, Micronaut has its own Micronaut parent, and therefore it must be build separately. 

## Benchmarks

For benchmarking there is Maven module `performance_tests`.
Each server has its own test where HTTP Client calls the API with request mentioned above.
The tests are built using JMH framework and measure throughput and latency in maximum possible threads.
After servers are running, benchmarks can be run by:
```
java -jar performance_tests/target/benchmarks.jar -wi 1 -i 5 -f 1 -rf json -rff results.json
```
which runs single warmup iteration and 5 normal iterations and saves the results to JSON file. 


Benchmark                                                                Mode  Cnt      Score      Error  Units
ServersThroughputBenchmark.graphQLApiEchoQuery_JavalinServer            thrpt    5  34623.964 ± 5057.805  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_MicroHTTPServer          thrpt    5  22230.464 ±  956.355  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_MicronautServer          thrpt    5  25814.941 ± 2012.660  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_NanoHTTPDServer          thrpt    5  15684.153 ±  612.101  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_NettyServer              thrpt    5  21673.317 ± 1990.044  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_QuarkusServer            thrpt    5   2605.821 ±   41.621  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_SpringBootMVCServer      thrpt    5  22684.260 ± 2558.271  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_SpringBootWebFluxServer  thrpt    2  24422.497             ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_UndertowServer           thrpt    5  33227.297 ± 1095.060  ops/s
ServersThroughputBenchmark.graphQLApiEchoQuery_VertXServer              thrpt    5  12905.428 ±  508.852  ops/s
ServersLatencyBenchmark.graphQLApiEchoQuery_JavalinServer                avgt    5    367.870 ±   26.427  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_MicroHTTPServer              avgt    5    557.401 ±   11.557  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_MicronautServer              avgt    5    463.472 ±   31.472  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_NanoHTTPDServer              avgt    5    781.200 ±   34.176  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_NettyServer                  avgt    5    554.845 ±   14.841  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_QuarkusServer                avgt    5   4611.304 ±   46.224  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_SpringBootMVCServer          avgt    5    525.140 ±   31.049  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_SpringBootWebFluxServer      avgt    3    505.663 ±  254.379  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_UndertowServer               avgt    5    382.429 ±   19.616  us/op
ServersLatencyBenchmark.graphQLApiEchoQuery_VertXServer                  avgt    5    948.595 ±   33.010  us/op

Caused by: java.util.MissingResourceException: Can't find bundle for base name i18n.Parsing, locale en_US
at java.base@17.0.5/java.util.ResourceBundle.throwMissingResourceException(ResourceBundle.java:2045)
at java.base@17.0.5/java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1683)
at java.base@17.0.5/java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1586)
at java.base@17.0.5/java.util.ResourceBundle.getBundle(ResourceBundle.java:1280)
at graphql.i18n.I18n.<init>(I18n.java:46)
at graphql.i18n.I18n.i18n(I18n.java:57)
at graphql.parser.ParserEnvironment$Builder.build(ParserEnvironment.java:73)
at graphql.schema.idl.SchemaParser.parseImpl(SchemaParser.java:121)
at graphql.schema.idl.SchemaParser.parseImpl(SchemaParser.java:113)
at graphql.schema.idl.SchemaParser.parse(SchemaParser.java:108)
at one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider.buildSchema(GraphQLProvider.java:45)
at one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider.<init>(GraphQLProvider.java:36)
at one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager.<init>(GraphQLManager.java:16)
