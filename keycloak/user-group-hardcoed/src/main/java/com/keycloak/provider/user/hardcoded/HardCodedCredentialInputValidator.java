package com.keycloak.provider.user.hardcoded;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;

public class HardCodedCredentialInputValidator implements CredentialInputValidator {

	protected KeycloakSession session;
	protected ComponentModel model;

	public static final String DEFAUL_PASSWORD = "123";

	public HardCodedCredentialInputValidator(KeycloakSession session, ComponentModel model) {
		this.session = session;
		this.model = model;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
//		credentialType.equals(CredentialModel.PASSWORD);

		return credentialType.equals(PasswordCredentialModel.TYPE);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return this.supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}

		UserCredentialModel cred = (UserCredentialModel) input;

		return DEFAUL_PASSWORD.equals(cred.getValue());
	}

}
