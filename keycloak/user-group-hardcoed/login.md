==== Métodos chamados na primeira autenticação

17:56:45,591 INFO  [stdout] (default task-1) == Construtor PropertyFileUserStorageProvider ==
17:56:45,591 INFO  [stdout] (default task-1) == getUserByUsername(String username, RealmModel realm) 
17:56:45,591 INFO  [stdout] (default task-1) == createAdapter(RealmModel realm, String username) 
17:56:45,608 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType)
17:56:45,609 INFO  [stdout] (default task-1) == isValid(RealmModel realm, UserModel user, CredentialInput input) 
17:56:45,609 INFO  [stdout] (default task-1) == getUserById(String id, RealmModel realm) 
17:56:45,610 INFO  [stdout] (default task-1) == getUserByUsername(String username, RealmModel realm) 
17:56:45,668 INFO  [stdout] (default task-1) == getUserById(String id, RealmModel realm) 
17:56:45,669 INFO  [stdout] (default task-1) == getUserByUsername(String username, RealmModel realm) 
17:56:45,672 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType) 
17:56:45,681 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType)
17:56:45,686 INFO  [stdout] (default task-1) == getUserById(String id, RealmModel realm) 
17:56:45,687 INFO  [stdout] (default task-1) == getUserByUsername(String username, RealmModel realm) 
17:56:45,689 INFO  [stdout] (default task-1) == getUserById(String id, RealmModel realm) 
17:56:45,689 INFO  [stdout] (default task-1) == getUserByUsername(String username, RealmModel realm) 
17:56:45,907 INFO  [stdout] (default task-1) == close()



==== Métodos chamados nos próximas autenticações

18:02:57,274 INFO  [stdout] (default task-1) == Construtor PropertyFileUserStorageProvider ==
18:02:57,275 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType)
18:02:57,275 INFO  [stdout] (default task-1) == isValid(RealmModel realm, UserModel user, CredentialInput input) 
18:02:57,276 INFO  [stdout] (default task-1) isValid -> UserModel:email:nulluserName:claytonid:f:26980576-746e-4185-8ee6-c693b1449c01:clayton
18:02:57,276 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType)
18:02:57,277 INFO  [stdout] (default task-1) == supportsCredentialType(String credentialType)
18:02:57,287 INFO  [stdout] (default task-1) == close()
