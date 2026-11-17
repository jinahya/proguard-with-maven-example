# proguard-with-maven-example

[![GitHub Action](https://github.com/jinahya/proguard-with-maven-example/workflows/Java%20CI/badge.svg)](https://github.com/jinahya/proguard-with-maven-example/actions?workflow=Java+CI)

How to ProGuard with Apache Maven.

## Build

```shell
mvn clean package
```

Produces `target/<finalName>.jar` and its ProGuard-processed `target/<finalName>-proguard.jar`.

## JDK library classes

ProGuard needs the JDK classes as a library. Which ones are used depends on the JDK running Maven, selected by profiles.

| Profile                | Activation                               | Library                                                     |
|------------------------|------------------------------------------|-------------------------------------------------------------|
| `java8-`               | JDK 1.8 or older                         | `${java.home}/lib/rt.jar`, `jce.jar`, `jsse.jar`            |
| `java9+`               | `${java.home}/jmods/java.base.jmod` exists | `${java.home}/jmods/java.base.jmod`                         |
| `java9+-without-jmods` | JDK 9+ without `jmods`                   | `target/jdk/java.base.jar`, extracted from `jrt:/`          |

Some JDK distributions, such as [Eclipse Temurin](https://adoptium.net/) 24+, no longer ship the `jmods` directory
([JEP 493](https://openjdk.org/jeps/493)), and ProGuard can't read the runtime image by itself yet
([Guardsquare/proguard#473](https://github.com/Guardsquare/proguard/issues/473)).
For those, the `java9+-without-jmods` profile runs [`src/build/ExtractJdk.java`](src/build/ExtractJdk.java) in the
`prepare-package` phase, which copies `java.base` classes from the running JDK's `jrt:/` file system into a jar.

This profile can be removed once ProGuard falls back to `jrt:/` for missing jmods by itself
([Guardsquare/proguard#541](https://github.com/Guardsquare/proguard/pull/541)).

## Build with Docker

[`docker.maven.sh`](docker.maven.sh) runs Maven in an [official Maven image](https://hub.docker.com/_/maven) of the
given tag.

```shell
sh docker.maven.sh <tag> <phases...>
sh docker.maven.sh 3-eclipse-temurin-25 clean package # no jmods; uses java9+-without-jmods
sh docker.maven.sh 3-eclipse-temurin-21 clean package # with jmods; uses java9+
```

[`docker.maven.3-amazoncorretto.sh`](docker.maven.3-amazoncorretto.sh) and
[`docker.maven.3-eclipse-temurin.sh`](docker.maven.3-eclipse-temurin.sh) build with each LTS release (8, 11, 17, 21, 25)
of those distributions.

```shell
sh docker.maven.3-eclipse-temurin.sh
```
