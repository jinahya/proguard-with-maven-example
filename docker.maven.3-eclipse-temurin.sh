#!/bin/sh
sh ./docker.maven.sh 3-eclipse-temurin-8 clean package
sh ./docker.maven.sh 3-eclipse-temurin-11 clean package
sh ./docker.maven.sh 3-eclipse-temurin-17 clean package
sh ./docker.maven.sh 3-eclipse-temurin-21 clean package
# https://github.com/adoptium/adoptium-support/issues/1271
#sh ./docker.maven.sh 3-eclipse-temurin-25 clean package
