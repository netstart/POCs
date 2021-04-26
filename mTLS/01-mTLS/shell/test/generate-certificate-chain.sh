#!/bin/bash

rm *.jks
rm *.pem

echo "========================================================"
echo "Creating fake third-party chain invalid other-ca-client "
echo "========================================================"

echo "===> generate private keys (ca-other-client)"
keytool -v -genkeypair -alias ca-other-client -dname "cn=Local-client Network - CA" -validity 73200 -keyalg RSA -keysize 2048 -ext bc:c -keystore ca-other-client.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12

echo "===> generate certificate-other-client"

keytool -v -exportcert -rfc -keystore ca-other-client.jks -alias ca-other-client -storepass materaspi > other-ca-client.pem

echo "========================================================"
echo "Creating fake third-party chain root-server ca-server   "
echo "========================================================"

echo "===> generate private keys (for root-server and ca-server)"
keytool -v -genkeypair -alias root-server -dname "cn=Local-server Network - ROOT" -validity 73200 -keyalg RSA -keysize 2048 -ext bc:c -keystore root-server.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12
keytool -v -genkeypair -alias ca-server -dname "cn=Local-server Network - CA" -validity 73200 -keyalg RSA -keysize 2048 -ext bc:c -keystore ca-server.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12

echo "===> generate root-server certificate-server"

keytool -exportcert -rfc -keystore root-server.jks -alias root-server -storepass materaspi > root-server.pem

echo "===> generate a certificate for ca signed by root-server (root-server -> ca)"

keytool -keystore ca-server.jks -storepass materaspi -certreq -alias ca-server | keytool -keystore root-server.jks -storepass materaspi -gencert -alias root-server -ext bc=0 -validity 73200 -rfc > ca-server.pem

echo "===> import ca cert chain into ca-server.jks"

cat root-server.pem ca-server.pem | keytool -v -keystore ca-server.jks -storepass materaspi -importcert -alias ca-server -noprompt

echo "========================================================"
echo "Creating fake third-party chain root-client ca-client   "
echo "========================================================"

echo "===> generate private keys (for root-client and ca-client)"
keytool -v -genkeypair -alias root-client -dname "cn=Local-client Network - ROOT" -validity 73200 -keyalg RSA -keysize 2048 -ext bc:c -keystore root-client.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12
keytool -v -genkeypair -alias ca-client -dname "cn=Local-client Network - CA" -validity 73200 -keyalg RSA -keysize 2048 -ext bc:c -keystore ca-client.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12

echo "===> generate root-client certificate-client"

keytool -exportcert -rfc -keystore root-client.jks -alias root-client -storepass materaspi > root-client.pem

echo "===> generate a certificate for ca signed by root-client (root-client -> ca)"

keytool -keystore ca-client.jks -storepass materaspi -certreq -alias ca-client | keytool -keystore root-client.jks -storepass materaspi -gencert -alias root-client -ext bc=0 -validity 73200 -rfc > ca-client.pem

echo "===> import ca cert chain into ca-client.jks"

cat root-client.pem ca-client.pem | keytool -v -keystore ca-client.jks -storepass materaspi -importcert -alias ca-client -noprompt

echo  "========================================================================"
echo  "Fake third-party chain generated. Now generating server-keystore.jks ..."
echo  "========================================================================"

echo "===> generate private keys (for server)"

keytool -v -genkeypair -alias server -dname cn=server -validity 73200 -keyalg RSA -keysize 2048 -keystore server-keystore.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12

echo "===> generate a certificate-server for server signed by ca-server (ca-server -> server)"

keytool -v -keystore server-keystore.jks -storepass materaspi -certreq -alias server | keytool -v -keystore ca-server.jks -storepass materaspi -gencert -alias ca-server -ext ku:c=dig,keyEnc -ext "san=dns:server" -ext eku=sa,ca -validity 73200 -rfc > server.pem

echo "===> import server cert chain into server-keystore.jks"
cat root-server.pem ca-server.pem server.pem | keytool -v -keystore server-keystore.jks -storepass materaspi -importcert -alias server -noprompt

echo  "**********************************************************************"
echo  "* ATENÇÃO:                                                           *"
echo  "* Após gerar o arquivo server-keystore deve-se utilizar o Keystore   *"
echo  "* Explorer para excluir o Certificado de CA e de ROOT da cadeia com  *"
echo  "* o recurso: Edit Certificate Chain > Remove Certificate             *"
echo  "**********************************************************************"

echo  "========================================================================"
echo  "Fake third-party chain generated. Now generating server-keystore.jks ..."
echo  "========================================================================"

echo "===> generate private keys (for server)"

keytool -v -genkeypair -alias server -dname cn=server -validity 73200 -keyalg RSA -keysize 2048 -keystore server-keystore-tls.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype PKCS12

echo "===> generate a certificate-server for server signed by ca-server (ca-server -> server-tls)"

keytool -v -keystore server-keystore-tls.jks -storepass materaspi -certreq -alias server | keytool -v -keystore ca-server.jks -storepass materaspi -gencert -alias ca-server -ext ku:c=dig,keyEnc -ext "san=dns:localhost,dns:spi-clearing-stub-service,dns:spi-stub-apis-ingress" -ext eku=sa,ca -validity 73200 -rfc > server-tls.pem

echo "===> import server cert chain into server-keystore.jks"
cat root-server.pem ca-server.pem server-tls.pem | keytool -v -keystore server-keystore-tls.jks -storepass materaspi -importcert -alias server -noprompt

echo  "**********************************************************************"
echo  "* ATENÇÃO:                                                           *"
echo  "* Após gerar o arquivo server-keystore-tls deve-se utilizar o Keystore   *"
echo  "* Explorer para excluir o Certificado de CA e de ROOT da cadeia com  *"
echo  "* o recurso: Edit Certificate Chain > Remove Certificate             *"
echo  "**********************************************************************"


echo  "========================================================================"
echo  "Fake third-party chain generated. Now generating client-keystore.jks ..."
echo  "========================================================================"

echo "===> generate private keys (for client)"

keytool -v -genkeypair -alias client -dname cn=client -validity 73200 -keyalg RSA -keysize 2048 -keystore client-keystore.jks -keypass materaspi -storepass materaspi -sigalg SHA256withRSA -storetype JKS -deststoretype pkcs12

echo "===> generate a certificate-client for client signed by ca-client (ca-client -> client)"

keytool -v -keystore client-keystore.jks -storepass materaspi -certreq -alias client | keytool -v -keystore ca-client.jks -storepass materaspi -gencert -alias ca-client -validity 73200 -rfc > client.pem

echo "===> import client cert chain into client-keystore.jks"
cat client.pem | keytool -v -keystore client-keystore.jks -storepass materaspi -importcert -alias client -noprompt

echo  "**********************************************************************"
echo  "* ATENÇÃO:                                                           *"
echo  "* Após gerar o arquivo client-keystore deve-se utilizar o Keystore   *"
echo  "* Explorer para excluir o Certificado de CA da cadeia com o recurso: *"
echo  "* Edit Certificate Chain > Remove Certificate                        *"
echo  "**********************************************************************"

echo "========================================================"
echo "Keystore generated. Now generating server-truststore ..."
echo "========================================================"

echo "import server cert chain into server-truststore.jks"

keytool -v -keystore server-truststore.jks -storepass materaspi -importcert -alias client -file ca-client.pem -noprompt

echo "========================================================"
echo "Keystore generated. Now generating client-truststore ..."
echo "========================================================"

echo "===> import client cert chain into client-truststore.jks"

keytool -v -keystore client-truststore.jks -storepass materaspi -importcert -alias server -file server.pem -noprompt

echo "========================================================"
echo "Keystore generated. Now generating client-truststore ..."
echo "========================================================"

echo "===> import client cert chain into client-truststore.jks"

keytool -v -keystore client-truststore-tls.jks -storepass materaspi -importcert -alias server -file server-tls.pem -noprompt

echo "=============================================================="
echo "Keystore generated. Now generating other-server-truststore ..."
echo "=============================================================="

echo "import server cert chain into other-server-truststore.jks"

keytool -v -keystore other-server-truststore.jks -storepass materaspi -importcert -alias other-client -file other-ca-client.pem -noprompt

echo "=============================================================================="
echo "Keystore generated. Now generating clearing-messages-client-server-certs   ..."
echo "this keystore will be used to digital signature validation tests           ..."
echo "=============================================================================="

echo "import server cert into clearing-messages-client-server-certs.jks"

keytool -v -keystore clearing-messages-client-server-certs.jks -storepass materaspi -importcert -alias server -file server.pem -noprompt

echo "import client cert into clearing-messages-client-server-certs.jks"

keytool -v -keystore clearing-messages-client-server-certs.jks -storepass materaspi -importcert -alias client -file client.pem -noprompt

echo "import client cert into server-clearing-messages.jks"

keytool -v -keystore server-clearing-messages.jks -storepass materaspi -importcert -alias client -file client.pem -noprompt

cp client-keystore.jks client-keystore-tls.jks
cp server-truststore.jks server-truststore-tls.jks

read -p "===> Press [Enter] key to start <==="

