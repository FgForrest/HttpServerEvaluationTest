#/bin/bash
taskset -c 0-5 java -server -jar javalin_server/target/servers.jar
