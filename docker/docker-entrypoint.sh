#!/bin/bash

exec 2>&1

exec java -server $JAVA_ARGS -jar /app.jar
