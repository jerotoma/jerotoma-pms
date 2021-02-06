#!/bin/sh

function copyFiles() { 		
	
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
}

copyFiles;