#!/bin/bash 
clear
YELLOW='\033[1;33m'
SEPARATOR="\n${YELLOW}---------------------------------- \n${NC}"
rm -rf MyServer.jks
rm -rf MyClient.jks
rm -rf MyServer.cer
rm -rf MyClient.cer

#-genkeypair is the same -genkey. -genkeypair is preferred going foward
#-storepass Keystore password
#-keypass Key password
printf "${SEPARATOR}Create a JKS file for Server with below command on your shell or command prompt\n"
keytool -genkeypair -alias MyServer -keyalg RSA -validity 1825 -keystore "MyServer.jks" -storetype JKS -dname "CN=myserver.com,OU=My Company Name,O=My Organization,L=My Location,ST=My State, C=My Country Short Code" -keypass password -storepass password

printf "${SEPARATOR}Generate public certificate\n"
# Now we need a certificate file that can be distributed as a public certificate to clients. 
# Run below command to extract that certificate. 
# It will ask for the password which you have supplied above while creating JKS
keytool -exportcert -alias MyServer -keystore MyServer.jks -file MyServer.cer -storepass password

printf "${SEPARATOR}For client\n"
keytool -genkey -alias MyClient -keyalg RSA -validity 1825 -keystore MyClient.jks -storetype JKS -dname "CN=client.com,OU=Client Company,O=Client,L=CLient Location,ST=Client State,C=Client Country Short Code" -keypass password -storepass password
keytool -exportcert -alias MyClient -keystore MyClient.jks -file MyClient.cer -storepass password

printf "${SEPARATOR}Add Server certificate to client truststore\n"
keytool -importcert -alias MyServer -keystore MyClient.jks -file MyServer.cer -storepass password -noprompt

printf "${SEPARATOR}Add client certificate to server truststore\n"
keytool -importcert -alias MyClient -keystore MyServer.jks -file MyClientPublic.cer -storepass password -noprompt

printf "${SEPARATOR}Delete a certificate from MyClient.jks. Password is from Server or Client?\n"
keytool -delete -alias MyClient -keystore MyClient.jks -storepass password
