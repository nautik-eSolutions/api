
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY pom.xml ./
COPY mvnw* ./
COPY .mvn .mvn

RUN mvn dependency:go-offline -B || true


COPY src ./src

RUN mvn clean package -DskipTests -B

FROM maven:3.9-eclipse-temurin-17-alpine AS development

WORKDIR /app

COPY pom.xml ./
COPY mvnw* ./
COPY .mvn .mvn

RUN mvn dependency:go-offline -B || true

COPY src ./src

EXPOSE 8085

EXPOSE 5005

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]


FROM eclipse-temurin:17-jre-alpine AS production

LABEL maintainer="nautik@nautiks.app" \
      version="1.0" \
      description="Nautik Spring Boot API - Admin"

RUN apk add --no-cache dumb-init

RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

WORKDIR /app

COPY --from=builder --chown=spring:spring /build/target/*.jar app.jar

USER spring

EXPOSE 8085

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8085/actuator/health || exit 1

ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:+UseG1GC \
    -XX:+UseStringDeduplication \
    -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["dumb-init", "--"]

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]