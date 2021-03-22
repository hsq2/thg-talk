FROM artifactory.io.thehut.local:5000/centos:7.20201115.0000
RUN yum install -y java-11-openjdk-headless && yum clean all
WORKDIR /app/
COPY ./target/THGTalk-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "THGTalk-0.0.1-SNAPSHOT.jar"]
