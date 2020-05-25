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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
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

	public static final String UNSET_PASSWORD = "123";

	protected KeycloakSession session;
	protected Properties properties;
	protected ComponentModel model;
// map of loaded users in this transaction
	protected Map<String, UserModel> loadedUsers = new HashMap<>();

	public PropertyFileUserStorageProvider(KeycloakSession session, ComponentModel model, Properties properties) {
		System.out.println("== Construtor PropertyFileUserStorageProvider ==");
		this.session = session;
		this.model = model;
		this.properties = properties;
	}

	// UserLookupProvider methods

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		System.out.println("== getUserByUsername(String username, RealmModel realm) ");
		UserModel adapter = loadedUsers.get(username);
		if (adapter == null) {
			String password = properties.getProperty(username);
			if (password != null) {
				adapter = createAdapter(realm, username);
				loadedUsers.put(username, adapter);
			}
		}
		return adapter;
	}

	protected UserModel createAdapter(RealmModel realm, String username) {
		System.out.println("== createAdapter(RealmModel realm, String username) ");
		AbstractUserAdapterFederatedStorage abstractUser = new AbstractUserAdapterFederatedStorage(session, realm,
				model) {
			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public void setUsername(String username) {

			}
			
			@Override
		    public Set<GroupModel> getGroups() {
//				for (GroupModel groupModel : realm.getGroups()) {
//					System.out.println("getGroups --- GroupID: " + groupModel.getId());
//					System.out.println("getGroups GroupName: " + groupModel.getName());
//
//					if ("LiquidacaoGrupo".equalsIgnoreCase(groupModel.getName())) {
//						return new HashSet<GroupModel>(Arrays.asList(groupModel));
//					}
//				}
				
		    	return new HashSet<GroupModel>(realm.getGroups());
		    }
		};

		return abstractUser;
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
		String password = properties.getProperty(user.getUsername());
		return credentialType.equals(CredentialModel.PASSWORD) && password != null;
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
		System.out.println("isValid -> UserModel:" + toPrintUserModel(user));
		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel))
			return false;

		UserCredentialModel cred = (UserCredentialModel) input;
		String password = properties.getProperty(user.getUsername());
		if (password == null || UNSET_PASSWORD.equals(password))
			return false;
		return password.equals(cred.getValue());
	}

	private String toPrintUserModel(UserModel user) {
		StringBuilder sb = new StringBuilder();
		sb.append("email:");
		sb.append(user.getEmail());

		sb.append("userName:");
		sb.append(user.getUsername());

		sb.append("id:");
		sb.append(user.getId());

		user.getRealmRoleMappings();
		user.getRoleMappings();
		user.getGroups();

		return sb.toString();
	}

	private static final Set<String> disableableTypes = new HashSet<>();

	static {
		disableableTypes.add(CredentialModel.PASSWORD);
	}

	@Override
	public void close() {
		System.out.println("== close()");

	}
}
