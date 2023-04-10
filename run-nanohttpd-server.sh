#/bin/bash
taskset -c 0-5 java -server -jar nanohttpd_server/target/servers.jar
