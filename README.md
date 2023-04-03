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