#/bin/bash
taskset -c 0-5 java -server -jar netty_server/target/servers.jar
