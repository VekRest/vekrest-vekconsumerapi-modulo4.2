FROM amazoncorretto:17-alpine3.15
LABEL MANTAINER="Vektrom Bonilha"

WORKDIR /app

RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]