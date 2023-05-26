#/bin/bash
taskset -c 0-5 java -server -jar quarkus_server/target/quarkus-app/quarkus-run.jar