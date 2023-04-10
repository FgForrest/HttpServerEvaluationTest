#/bin/bash
taskset -c 0-5 java -server -jar microhttp_server/target/servers.jar
