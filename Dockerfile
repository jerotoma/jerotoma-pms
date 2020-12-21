FROM maven AS build_backend

WORKDIR /usr/src/build

COPY JSMSServer .

RUN mvn package

#FROM node:12 AS build_frontend

FROM tomcat:jdk8-corretto

ARG WORKING_DIR=/usr/src/app
ARG CATALINA_HOME=/usr/local/tomcat
ARG CATALINA_BASE=${WORKING_DIR}
ARG CATALINA_TMPDIR=/usr/local/tomcat/temp
ARG JRE_HOME=/usr

WORKDIR ${WORKING_DIR}

ENV CATALINA_HOME=${CATALINA_HOME}
ENV CATALINA_BASE=${CATALINA_BASE}
ENV CATALINA_TMPDIR=${CATALINA_TMPDIR}
ENV JRE_HOME=${JRE_HOME}
ENV CLASSPATH=${CATALINA_HOME}/bin/bootstrap.jar:${CATALINA_HOME}/bin/tomcat-juli.jar

COPY tomcat .
COPY --from=build_backend /usr/src/build/JSMSApplication/target/dependency ${CATALINA_BASE}/jerotoma_sms/app/lib
COPY --from=build_backend /usr/src/build/JSMSApplication/target/classes ${CATALINA_BASE}/jerotoma_sms/app/classes
COPY  tomcat/jerotoma_sms/resources/index.html ${CATALINA_BASE}/jerotoma_sms/app/classes/public

EXPOSE 8080

CMD ["catalina.sh", "run"]