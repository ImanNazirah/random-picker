#!/bin/bash
# $1 = spinner style (e.g., basic, style)
# $2 = output alignment (e.g., horizontal, vertical)
JAR=$(ls target/random-picker-*.jar | sort | tail -n 1)
java -jar "$JAR" "$1" "$2"
