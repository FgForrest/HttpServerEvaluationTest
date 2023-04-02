#/bin/bash
taskset -c 0-5 java -server -jar spring_boot_mvc_server/target/spring-boot-mvc-server.jar