# Execute this shell on bin directory
YELLOW='\033[1;33m'
NC='\033[0m' # No Color
SEPARATOR="\n${YELLOW}---------------------------------- \n${NC}"

clear

CLIENT_KEYSTORE_DIR=../mtls-client/src/main/resources
CLIENT_KEYSTORE_INTERCEPTOR_DIR=../02-mtls-client-interceptor/src/main/resources
CLIENT_KEYSTORE_MAX_CONNECTION_DIR=../03-mtls-client-max-connection/src/main/resources
CLIENT_KEYSTORE_GZIP_DISABLE_DIR=../04-mtls-client-gzip-disable/src/main/resources
CLIENT_KEYSTORE_CONNECTION_TIME_TO_LIVE_DIR=../05-mtls-client-connection-time-to-live/src/main/resources
CLIENT_KEYSTORE_RETY_DIR=../06-mtls-client-retry/src/main/resources
CLIENT_KEYSTORE_CONNECTION_TIMEOUT_DIR=../07-mtls-client-timeout/src/main/resources
CLIENT_KEYSTORE_INTERCEPTOR_RESTTEMPLATE_DIR=../08-mtls-client-interceptor-to-resttemplate/src/main/resources
CLIENT_KEYSTORE_CONNECTION_POOL_DIR=../09-mtls-client-connection-pool/src/main/resources
SERVER_KEYSTORE_DIR=../mtls-server/src/main/resources

CLIENT_KEYSTORE=$CLIENT_KEYSTORE_DIR/client-nonprod.jks
SERVER_KEYSTORE=$SERVER_KEYSTORE_DIR/server-nonprod.jks

JAVA_CA_CERTS=$JAVA_HOME/jre/lib/security/cacerts

printf "${SEPARATOR}Remove old files previously genereted\n"
rm -rf CLIENT_KEYSTORE
rm -rf CLIENT_KEYSTORE_INTERCEPTOR
rm -rf SERVER_KEYSTORE

rm -rf client-public.cer
rm -rf server-public.cer

printf "${SEPARATOR}Generate a client and server RSA 2048 key pair\n"
keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -dname "CN=Client,OU=Client,O=PlumStep,L=San Francisco,S=CA,C=U" -keypass changeme -keystore $CLIENT_KEYSTORE -storepass changeme
keytool -genkeypair -alias server -keyalg RSA -keysize 2048 -dname "CN=Server,OU=Server,O=PlumStep,L=San Francisco,S=CA,C=U" -keypass changeme -keystore $SERVER_KEYSTORE -storepass changeme

printf "${SEPARATOR}Export public certificates for both the client and server\n"
keytool -exportcert -alias client -file client-public.cer -keystore $CLIENT_KEYSTORE -storepass changeme
keytool -exportcert -alias server -file server-public.cer -keystore $SERVER_KEYSTORE -storepass changeme

printf "${SEPARATOR}Import the client and server public certificates into each others keystore\n"
keytool -importcert -keystore $CLIENT_KEYSTORE -alias server-public-cert -file server-public.cer -storepass changeme -noprompt
keytool -importcert -keystore $SERVER_KEYSTORE -alias client-public-cert -file client-public.cer -storepass changeme -noprompt

printf "${SEPARATOR}Copy client keystore\n"
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_INTERCEPTOR_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_MAX_CONNECTION_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_GZIP_DISABLE_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_CONNECTION_TIME_TO_LIVE_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_RETY_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_CONNECTION_TIMEOUT_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_INTERCEPTOR_RESTTEMPLATE_DIR
cp $CLIENT_KEYSTORE $CLIENT_KEYSTORE_CONNECTION_POOL_DIR


