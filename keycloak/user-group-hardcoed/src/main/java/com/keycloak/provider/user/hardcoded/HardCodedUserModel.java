package com.keycloak.provider.user.hardcoded;

import java.util.HashSet;
import java.util.Set;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class HardCodedUserModel extends AbstractUserAdapterFederatedStorage {
	private String username;

	public HardCodedUserModel(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel,
			String username) {
		super(session, realm, storageProviderModel);
		this.setUsername(username);
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Set<GroupModel> getGroups() {
//		for (GroupModel groupModel : realm.getGroups()) {
//			System.out.println("getGroups --- GroupID: " + groupModel.getId());
//			System.out.println("getGroups GroupName: " + groupModel.getName());
//
//			if ("LiquidacaoGrupo".equalsIgnoreCase(groupModel.getName())) {
//				return new HashSet<GroupModel>(Arrays.asList(groupModel));
//			}
//		}

		return new HashSet<GroupModel>(realm.getGroups());
	}

}
