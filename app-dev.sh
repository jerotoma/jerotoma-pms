#!/bin/sh

source ./common.sh

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

	mvnInstall;
	copyFiles;
	
	echo '......................................................';
	echo "${GREEN}Performing : $ACTION action           ${RESET}";
	echo '......................................................';


	if [[ "$ACTION" != "startDocker" && "$ACTION" != "run" && "$ACTION" != "start" && "$ACTION" != "stop" && "$ACTION" != "" ]]; then
		echo "${RED}Invalide command, acceptable commands are ${GREEN}startDocker, run, start, stop ${RESET}";
		exit;
	fi

	if [[ "$ACTION" = "" ]]; then
		ACTION="run"
	fi
	
	if [[ "$ACTION" = "startDocker" ]]; then
		use_docker=true
	else
		use_docker=false
	fi

	if [[ $use_docker == true ]]; then
		docker-compose up --build
	else		
		(cd ./tomcat && ./bin/catalina.sh jpda ${ACTION})
	fi	
}
ACTION=$@;
startTomcat;