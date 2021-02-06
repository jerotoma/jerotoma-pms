#!/bin/sh

function startTomcat() { 	
	#export JAVA_HOME=$(/usr/libexec/java_home -v 1.8) 
	export JAVA_HOME="/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home"
	export CATALINA_HOME=${HOME}/servers/tomcats/tomcat-9.0.41
	export CATALINA_BASE=${HOME}/projects/jerotoma/tomcat
	export CATALINA_TMPDIR=${HOME}/projects/jerotoma/tomcat/temp_dir
	#export JPDA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"

	declare JPDA_ADDRESS="8000"
	declare JPDA_TRANSPORT="dt_socket"

	export JPDA_TRANSPORT=$JPDA_TRANSPORT
	export JPDA_ADDRESS=$JPDA_ADDRESS
	
	echo '......................................................';
	echo 'Removing files at ./tomcat/jerotoma_sms/JSMSServer';
	echo '......................................................';

   	rm -rf ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/*
	rm -rf ./tomcat/jerotoma_sms/JSMSServer/META-INF/*
    
	echo '......................................................';
	echo 'Copying files from ./JSMSServer/JSMSApplication/target';
	echo '......................................................';
	
    cp -r ./JSMSServer/JSMSApplication/target/jerotoma_sms/* ./tomcat/jerotoma_sms/JSMSServer
	rm -rf ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/classes/public/index.html
	cp -r ./tomcat/jerotoma_sms/resources/index.html ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/classes/public

	(cd ${HOME}/projects/jerotoma/tomcat && ./bin/catalina.sh jpda run)
}

startTomcat;