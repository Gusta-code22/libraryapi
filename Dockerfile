# build
FROM maven:3.9.10-amazoncorretto-21-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests


#run
FROM amazoncorretto:21.0.5

WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_PASSWORD=''
ENV DATASOURCE_USERNAME=''
ENV GOOGLE_CLIENTID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_id'
ENV GITHUB_CLIENT_SECRET='client_id'
ENV GITHUB_CLIENTID='client_id'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar
