# syntax=docker/dockerfile:1.2
FROM bellsoft/liberica-openjdk-alpine AS build
#FROM container-registry.oracle.com/graalvm/graalvm-community:latest AS build
LABEL authors="user"
WORKDIR /workspace/app

COPY . /workspace/app
RUN chmod +x gradlew
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
#RUN --mount=type=cache,target=/root/.gradle ./gradlew nativeCompile
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM bellsoft/liberica-openjre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.sinor.stomp.StompApplication"]
