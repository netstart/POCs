  mvn clean install wildfly:deploy
  
  Adocionar no menu federation, vai precisar dizer onde está o arquivo .properties, no campo virá preenchido com:
  ${jboss.server.config.dir}/example-users.properties
  por padrão fica em: .../keycloak-9.0.3/standalone/configuration
  
  