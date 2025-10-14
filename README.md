# proguard-with-maven-example

[![GitHub Action](https://github.com/jinahya/proguard-with-maven-example/workflows/Java%20CI/badge.svg)](https://github.com/jinahya/proguard-with-maven-example/actions?workflow=Java+CI)

How to ProGuard with Apache Maven.

## Building inside a container

e.g.

```shell
sh ./docker.maven.sh 3-amazoncorretto-8 clean package
ls -l target
```

```shell
sh ./docker.maven.sh 3-amazoncorretto-11 clean package
ls -l target
```

```shell
sh ./docker.maven.sh 3-amazoncorretto-17 clean package
ls -l target
```

```shell
sh ./docker.maven.sh 3-amazoncorretto-21 clean package
ls -l target
```

```shell
sh ./docker.maven.sh 3-amazoncorretto-25 clean package
ls -l target
```
