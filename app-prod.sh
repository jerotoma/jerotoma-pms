#!/bin/sh

function copyFiles() { 		
	
	echo '......................................................';
	echo 'Removing files at ./tomcat/jerotoma_sms/JSMSServer';
	echo '......................................................';

   	rm -rf ./tomcat/jerotoma_sms/JSMSServer/classes/*
    rm -rf ./tomcat/jerotoma_sms/JSMSServer/lib/*
    
	echo '......................................................';
	echo 'Copying files from ./JSMSServer/JSMSApplication/target';
	echo '......................................................';
	
    cp -r ./JSMSServer/JSMSApplication/target/dependency/* ./tomcat/jerotoma_sms/JSMSServer/lib
    cp -r ./JSMSServer/JSMSApplication/target/classes/* ./tomcat/jerotoma_sms/JSMSServer/classes
	rm -rf ./tomcat/jerotoma_sms/JSMSServer/classes/public/index.html
	cp -r ./tomcat/jerotoma_sms/resources/index.html ./tomcat/jerotoma_sms/JSMSServer/classes/public
}

copyFiles;