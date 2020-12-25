FROM maven AS build_backend

ARG BUILD_DIR=/usr/src/build

WORKDIR ${BUILD_DIR}

COPY JSMSServer .

RUN mvn package

#FROM node:12 AS build_frontend

FROM tomcat:jdk8-corretto

ARG LOCAL_RESOURCE_DIR=tomcat/jerotoma_sms/resources
ARG BUILD_DIR=/usr/src/build
ARG WORKING_DIR=/usr/src/app
ARG CATALINA_HOME=/usr/local/tomcat
ARG CATALINA_BASE=${WORKING_DIR}
ARG CATALINA_TMPDIR=/usr/local/tomcat/temp
ARG JRE_HOME=/usr
ARG APP_DIR=${CATALINA_BASE}/jerotoma_sms/app

WORKDIR ${WORKING_DIR}

ENV CATALINA_HOME=${CATALINA_HOME}
ENV CATALINA_BASE=${CATALINA_BASE}
ENV CATALINA_TMPDIR=${CATALINA_TMPDIR}
ENV JRE_HOME=${JRE_HOME}
ENV CLASSPATH=${CATALINA_HOME}/bin/bootstrap.jar:${CATALINA_HOME}/bin/tomcat-juli.jar

COPY tomcat .
COPY --from=build_backend ${BUILD_DIR}/JSMSApplication/target/dependency ${APP_DIR}/lib
COPY --from=build_backend ${BUILD_DIR}/JSMSApplication/target/classes ${APP_DIR}/classes
COPY  ${LOCAL_RESOURCE_DIR}/index.html ${APP_DIR}/classes/public

EXPOSE 8080

CMD ["catalina.sh", "run"]