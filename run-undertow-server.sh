#/bin/bash
taskset -c 0-5 java -server -jar undertow_server/target/servers.jar
