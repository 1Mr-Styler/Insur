#!/usr/bin/env bash

export JAVA_HOME=/root/.sdkman/candidates/java/current

echo "~~~~~~~~~~~~~~~~~~~~~~~~~\t Running NER Server\n"
#./grailsw run --debug-jvm --port=8091
./gradlew bootRun
