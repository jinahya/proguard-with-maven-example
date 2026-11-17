#!/bin/sh
if [ $# -lt 2 ]; then
  echo "Usage: $0 <tag> <phases...>, e.g. $0 3-eclipse-temurin-25 clean install"
  echo "See https://hub.docker.com/_/maven for available tags"
  exit 1
fi
groupId=$(mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout)
artifactId=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)
version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
tag="$1"
shift
# allocate a tty only when there is one (e.g., not in CI)
tty=""
[ -t 0 ] && [ -t 1 ] && tty="-it"
docker run $tty --rm \
  --name $artifactId \
  -v "$HOME/.m2":/root/.m2 \
  -v "$(pwd)":/usr/src/$groupId/$artifactId/$version \
  -w /usr/src/$groupId/$artifactId/$version \
  maven:"$tag" mvn "$@"
