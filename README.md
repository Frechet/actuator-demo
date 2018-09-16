#Spring Boot Actuator Demo

Demo project to show how use Spring Boot Actuator to create custom jmx statistic about Http.
In this example counts number of requests to uri.

## Stack

* Jdk 1.8
* Maven 3.3.9
* Spring Boot 1.5.16

## Deploy

1. ``mvn clean install``
1. ``java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1617 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar actuator-demo.jar``

# Demo

1. Connect to jmx server use any client (for example, jConsole).
1. Open web browser for address ``localhost:8080/hello`` and refresh some times.
1. See statistic change MBean ``TraceCountResource`` in package ``man.frechet.demo.actuator.jmxresource``.
