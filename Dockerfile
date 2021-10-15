FROM gradle:7.2-jdk11
COPY ./build/libs/currencyservice-0.0.1-SNAPSHOT.jar currencyservice-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","currencyservice-0.0.1-SNAPSHOT.jar"]

