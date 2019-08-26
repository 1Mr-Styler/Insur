#!/usr/bin/env bash

export JAVA_HOME=/root/.sdkman/candidates/java/current

echo -e "~~~~~~~~~~~~~~~~~~~~~~~~~\t Running OCR Server\n"
./gradlew bootRun
