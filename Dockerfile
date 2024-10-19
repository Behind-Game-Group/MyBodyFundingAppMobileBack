#
# Create the Java img for Docker
#
# Spring profile options are in docker-compose.yml file
#

FROM maven:3.8.3-openjdk-17
WORKDIR /myBodyFunding

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src

RUN mvn clean install
CMD mvn spring-boot:run