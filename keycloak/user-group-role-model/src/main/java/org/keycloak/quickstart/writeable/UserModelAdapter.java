package org.keycloak.quickstart.writeable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class UserModelAdapter {
	protected KeycloakSession session;
	protected ComponentModel model;

	public UserModelAdapter(KeycloakSession session, ComponentModel model) {
		this.session = session;
		this.model = model;
	}

	public UserModel createUserModelAdapter(RealmModel realm, String username) {
		System.out.println("== createAdapter(RealmModel realm, String username) ");
		return new AbstractUserAdapterFederatedStorage(session, realm, model) {
			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public void setUsername(String username) {

			}
		};
	}
	
	public UserModel createUserModel(RealmModel realm, String username) {
		return new UserModel() {

			@Override
			public Set<RoleModel> getRealmRoleMappings() {
				return null;
			}

			@Override
			public Set<RoleModel> getClientRoleMappings(ClientModel app) {
				return null;
			}

			@Override
			public boolean hasRole(RoleModel role) {
				return false;
			}

			@Override
			public void grantRole(RoleModel role) {
			}

			@Override
			public Set<RoleModel> getRoleMappings() {
				return null;
			}

			@Override
			public void deleteRoleMapping(RoleModel role) {
			}

			@Override
			public String getId() {
				return null;
			}

			@Override
			public String getUsername() {
				return null;
			}

			@Override
			public void setUsername(String username) {
			}

			@Override
			public Long getCreatedTimestamp() {
				return null;
			}

			@Override
			public void setCreatedTimestamp(Long timestamp) {
			}

			@Override
			public boolean isEnabled() {
				return false;
			}

			@Override
			public void setEnabled(boolean enabled) {
			}

			@Override
			public void setSingleAttribute(String name, String value) {
			}

			@Override
			public void setAttribute(String name, List<String> values) {
			}

			@Override
			public void removeAttribute(String name) {
			}

			@Override
			public String getFirstAttribute(String name) {
				return null;
			}

			@Override
			public List<String> getAttribute(String name) {
				return null;
			}

			@Override
			public Map<String, List<String>> getAttributes() {
				return null;
			}

			@Override
			public Set<String> getRequiredActions() {
				return null;
			}

			@Override
			public void addRequiredAction(String action) {
			}

			@Override
			public void removeRequiredAction(String action) {
			}

			@Override
			public void addRequiredAction(RequiredAction action) {
			}

			@Override
			public void removeRequiredAction(RequiredAction action) {
			}

			@Override
			public String getFirstName() {
				return null;
			}

			@Override
			public void setFirstName(String firstName) {
			}

			@Override
			public String getLastName() {
				return null;
			}

			@Override
			public void setLastName(String lastName) {
			}

			@Override
			public String getEmail() {
				return null;
			}

			@Override
			public void setEmail(String email) {
			}

			@Override
			public boolean isEmailVerified() {
				return false;
			}

			@Override
			public void setEmailVerified(boolean verified) {
			}

			@Override
			public Set<GroupModel> getGroups() {
				return null;
			}

			@Override
			public void joinGroup(GroupModel group) {
			}

			@Override
			public void leaveGroup(GroupModel group) {
			}

			@Override
			public boolean isMemberOf(GroupModel group) {
				return false;
			}

			@Override
			public String getFederationLink() {
				return null;
			}

			@Override
			public void setFederationLink(String link) {
			}

			@Override
			public String getServiceAccountClientLink() {
				return null;
			}

			@Override
			public void setServiceAccountClientLink(String clientInternalId) {
			}
			
		};
	}
}
