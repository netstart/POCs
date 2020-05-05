/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.quickstart.writeable;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.quickstart.writeable.model.UserModelImpl;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class PropertyFileUserStorageProvider implements
// Factory exige esta interface
		UserStorageProvider,

// Usado para pesquisar usuário individualmente no console web, também é usado para recupear o token, 
// principalmente no primeiro login, quando não há cache (getUserByUsername)
		UserLookupProvider,

// Precisa para pegar o token
		CredentialInputValidator {

	public static final String UNSET_PASSWORD = "#$!-UNSET-PASSWORD";

	protected KeycloakSession session;
	protected ComponentModel model;
// map of loaded users in this transaction

	public PropertyFileUserStorageProvider(KeycloakSession session, ComponentModel model) {
		System.out.println("== Construtor PropertyFileUserStorageProvider ==");
		this.session = session;
		this.model = model;
	}

	// UserLookupProvider methods

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		System.out.println("== getUserByUsername(String username, RealmModel realm) " + " username: " + username);

		if (username.equalsIgnoreCase("r2d2")) {
			return new UserModelImpl(model).createUserModel();
		}

		return null;

	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		System.out.println("== getUserById(String id, RealmModel realm) ");
		StorageId storageId = new StorageId(id);
		String username = storageId.getExternalId();
		return getUserByUsername(username, realm);
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		System.out.println("== getUserByEmail(String email, RealmModel realm) ");
		return null;
	}

// CredentialInputValidator methods

	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		System.out.println("== isConfiguredFor(RealmModel realm, UserModel user, String credentialType)");
// verificar se a credencial é password e a senha não é vazia
// return credentialType.equals(PasswordCredentialModel.TYPE) && password != null;
// return credentialType.equals(CredentialModel.PASSWORD) && password != null;
		return true;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		System.out.println("== supportsCredentialType(String credentialType) + credentialType: " + credentialType);
		return credentialType.equals(CredentialModel.PASSWORD);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		System.out.println("== isValid(RealmModel realm, UserModel user, CredentialInput input) realm:" + realm
				+ "input: " + input);

		System.out.println("isValid -> UserModel maybe null:" + user);
		if (user != null) {
			System.out.println("isValid -> UserModel not null: " + toPrint(user));
		}

		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel))
			return false;

		UserCredentialModel cred = (UserCredentialModel) input;

		if (cred.getValue() == null) {
			System.out.println("isValid will be return false");
			return false;
		}

		if (UNSET_PASSWORD.equals(cred.getValue())) {
			System.out.println("isValid will be return true because evalueted UNSET_PASSWORD");
			return true;
		}

		System.out.println("isValid will be return default true");
		return true;
	}

	@Override
	public void close() {
		System.out.println("== close()");

	}

	public String toPrint(UserModel userModel) {
		if (userModel == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("\nemail: ");
		sb.append(userModel.getEmail());

		sb.append(" userName: ");
		sb.append(userModel.getUsername());

		sb.append(" id: ");
		sb.append(userModel.getId());

//		userModel.getRealmRoleMappings();
//		userModel.getRoleMappings();
//		userModel.getGroups();

		return sb.toString();
	}

}
