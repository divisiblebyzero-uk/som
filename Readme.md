# Settle-o-matic

## Building

1. Run a maven build in the root (mvn package)
2. Docker compose up

This should start a network consisting of:
* Zookeeper
* One Kafka broker
* One instance of client-broker (listening on port 8080)
* One instance of settlement-manager (listening on port 8081)
* One instance of the admin GUI (listening on port 8888)
