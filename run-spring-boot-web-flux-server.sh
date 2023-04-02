#/bin/bash
taskset -c 0-5 java -server -jar spring_boot_web_flux_server/target/spring-boot-web-flux-server.jar