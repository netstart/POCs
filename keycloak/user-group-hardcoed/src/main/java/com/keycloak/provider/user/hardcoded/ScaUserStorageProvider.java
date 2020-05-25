package com.keycloak.provider.user.hardcoded;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

public class ScaUserStorageProvider implements
// Factory exige esta interface
		UserStorageProvider,

// Usado para pesquisar usuário individualmente no console web, também é usado para recupear o token, 
// principalmente no primeiro login, quando não há cache (getUserByUsername)
		UserLookupProvider,

// Precisa para pegar o token
		CredentialInputValidator {

	protected UserLookupProvider userLookupProvider;
	protected CredentialInputValidator credentialInputValidator;

	public ScaUserStorageProvider(KeycloakSession session, ComponentModel componentModel) {
		this.userLookupProvider = new HardCodedUserLookupProvider(session, componentModel);
		this.credentialInputValidator = new HardCodedCredentialInputValidator(session, componentModel);
	}

	// UserLookupProvider methods
	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		return userLookupProvider.getUserByUsername(username, realm);
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		return userLookupProvider.getUserById(id, realm);
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		return userLookupProvider.getUserByEmail(email, realm);
	}

// CredentialInputValidator methods
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return credentialInputValidator.isConfiguredFor(realm, user, credentialType);
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		return credentialInputValidator.supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		return credentialInputValidator.isValid(realm, user, input);
	}

	@Override
	public void close() {
	}
}
