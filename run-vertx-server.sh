#/bin/bash
taskset -c 0-5 java -server -jar vertx_server/target/servers.jar
