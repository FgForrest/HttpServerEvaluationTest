#/bin/bash
taskset -c 0-5 java -server -jar server/target/servers.jar
