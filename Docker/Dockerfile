FROM alpine
#JDK and JAVA Tools
COPY jdk-17.tar.gz /opt
RUN tar xf /opt/jdk-17.tar.gz
ENV JAVA_HOME /opt/jdk-17.0.1/
ENV PATH $PATH:$JAVA_HOME/bin
COPY ChaosMonkey-1.0-SNAPSHOT.jar /opt
#RUN
CMD java -jar /opt/ChaosMonkey-1.0-SNAPSHOT.jar