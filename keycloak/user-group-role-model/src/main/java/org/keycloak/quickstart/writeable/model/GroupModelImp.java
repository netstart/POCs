package org.keycloak.quickstart.writeable.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;

public class GroupModelImp implements GroupModel {

	private static final String GROUP_NAME_R2D2 = "GROUP_NAME_R2D2";
	private static final String GROUP_ID_R2D2 = "GROUP_ID_R2D2";

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
		return GROUP_ID_R2D2;
	}

	@Override
	public String getName() {
		return GROUP_NAME_R2D2;
	}

	@Override
	public void setName(String name) {
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
	public GroupModel getParent() {
		return null;
	}

	@Override
	public String getParentId() {
		return null;
	}

	@Override
	public Set<GroupModel> getSubGroups() {
		return null;
	}

	@Override
	public void setParent(GroupModel group) {
	}

	@Override
	public void addChild(GroupModel subGroup) {
	}

	@Override
	public void removeChild(GroupModel subGroup) {
	}

}
