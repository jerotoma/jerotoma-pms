#8.5.61-jdk8-corretto 10-jdk8-corretto
FROM tomcat:9.0.41-jdk8-corretto

#You can find the values for these variables inside .env file
#Host machine directories paths
ARG LOCAL_RESOURCE_DIR=./tomcat/jerotoma_sms/resources
ARG LOCAL_TARGET_DIR=./JSMSServer/JSMSApplication/target
ARG LOCAL_LOGS_DIR=./tomcat/logs


#Container directories paths
ARG USER_LOCAL_DIR=/usr/local
ARG WORKING_DIR=${USER_LOCAL_DIR}/app
ARG CATALINA_HOME=${USER_LOCAL_DIR}/tomcat
ARG CATALINA_BASE=${USER_LOCAL_DIR}/app
ARG CATALINA_TMPDIR=${USER_LOCAL_DIR}/tomcat/temp
ARG JRE_HOME=/usr
ARG APP_HOME_DIR=${CATALINA_BASE}/jerotoma_sms
ARG JSMS_SERVER_DIR=${APP_HOME_DIR}/JSMSServer

WORKDIR ${WORKING_DIR}

#Add tomcat environment variables
ENV CATALINA_HOME=${CATALINA_HOME}
ENV CATALINA_BASE=${CATALINA_BASE}
ENV CATALINA_TMPDIR=${CATALINA_TMPDIR}
ENV JRE_HOME=${JRE_HOME}
ENV CLASSPATH=${CATALINA_HOME}/bin/bootstrap.jar:${CATALINA_HOME}/bin/tomcat-juli.jar

#Add tomcat jpda debugging environmental variables
#ENV JPDA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"

# create mount point for volume with application
RUN mkdir -p ${JSMS_SERVER_DIR}/lib
RUN mkdir -p ${JSMS_SERVER_DIR}/classes

#Copy files
COPY tomcat .
COPY nginx ${WORKING_DIR}/nginx
COPY ${LOCAL_TARGET_DIR}/dependency ${JSMS_SERVER_DIR}/lib
COPY ${LOCAL_TARGET_DIR}/classes ${JSMS_SERVER_DIR}/classes
COPY ${LOCAL_RESOURCE_DIR}/index.html ${JSMS_SERVER_DIR}/classes/public

#Expose tcp port
EXPOSE 8080

CMD ["catalina.sh", "jpda", "run"]