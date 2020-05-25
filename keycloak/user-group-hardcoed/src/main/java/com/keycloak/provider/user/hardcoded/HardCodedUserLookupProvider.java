package com.keycloak.provider.user.hardcoded;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.user.UserLookupProvider;

public class HardCodedUserLookupProvider implements UserLookupProvider {

	protected KeycloakSession session;
	protected ComponentModel model;

	public HardCodedUserLookupProvider(KeycloakSession session, ComponentModel model) {
		this.session = session;
		this.model = model;
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		String username = new StorageId(id).getExternalId();
		return getUserByUsername(username, realm);
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		if ("cknp".equalsIgnoreCase(username)) {
			HardCodedUserModel userModel = new HardCodedUserModel(this.session, realm, this.model, username);
			return userModel;
		}

		return null;
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		return null;
	}

}
