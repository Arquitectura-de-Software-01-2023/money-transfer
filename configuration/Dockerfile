FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine

HEALTHCHECK --interval=10s --timeout=3s --start-period=15s --retries=10 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:$CONFIGURATION_PORT/actuator/health || exit 1

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
	
ENTRYPOINT [ "java", "-cp", "app:app/lib/*", "ucb.arch.moneytransfer.configuration.ConfigurationApplicationKt" ]