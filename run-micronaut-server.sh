#/bin/bash
taskset -c 0-5 java -server -jar micronaut_server/target/http_server_evaulation_test_micronaut_server-1.0-SNAPSHOT.jar