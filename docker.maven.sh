#!/bin/sh
if [ $# -lt 3 ]; then
  echo "Usage: $0 <tag> <phases...>, e.g. $0 3-jdk-11-openj9 clean package"
  echo "See https://hub.docker.com/_/maven for available tags"
  exit 1
fi
groupId=$(mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout)
artifactId=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)
version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
tag="$1"
shift
docker run -it --rm \
  --name $artifactId \
  -v "$HOME/.m2":/root/.m2 \
  -v "$(pwd)":/usr/src/$groupId/$artifactId/$version \
  -w /usr/src/$groupId/$artifactId/$version \
  maven:"$tag" mvn "$@"
