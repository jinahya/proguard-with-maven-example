#!/bin/sh
sh ./docker.maven.sh 3-amazoncorretto-8 clean package
sh ./docker.maven.sh 3-amazoncorretto-11 clean package
sh ./docker.maven.sh 3-amazoncorretto-17 clean package
sh ./docker.maven.sh 3-amazoncorretto-21 clean package
sh ./docker.maven.sh 3-amazoncorretto-25 clean package
