RED=`tput setaf 1`
GREEN=`tput setaf 2`
YELLOW=`tput setaf 3`
BLUE=`tput setaf 4`
WHITE=`tput setaf 7`
RESET=`tput sgr0`

function copyFiles() { 	

    echo '......................................................';
    echo "${GREEN}Removing files at ./tomcat/jerotoma_sms/JSMSServer${RESET}";
    echo '......................................................';

    rm -rf ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/*
    rm -rf ./tomcat/jerotoma_sms/JSMSServer/META-INF/*

    echo '......................................................';
    echo "${GREEN}Copying files from ./JSMSServer/JSMSApplication/target ${RESET}";
    echo '......................................................';

    cp -r ./JSMSServer/JSMSApplication/target/jerotoma_sms/* ./tomcat/jerotoma_sms/JSMSServer
    rm -rf ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/classes/public/index.html
    cp -r ./tomcat/jerotoma_sms/resources/index.html ./tomcat/jerotoma_sms/JSMSServer/WEB-INF/classes/public
}

function mvnPackage() {
    (cd ./JSMSServer && mvn clean package)
}

function mvnInstall() {
    (cd ./JSMSServer && mvn clean install)
}