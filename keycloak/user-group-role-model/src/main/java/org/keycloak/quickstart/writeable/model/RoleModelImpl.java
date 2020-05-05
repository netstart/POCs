package org.keycloak.quickstart.writeable.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;

public class RoleModelImpl implements RoleModel {

	private static final String ROLE_ID = "ROLE_ID";
	private static final String ROLE_DESCRIPTION_R2D2 = "DESCRIPTION_ROLE_R2D2";
	private static final String ROLE_R2D2 = "role_r2d2";

	@Override
	public String getName() {
		return ROLE_R2D2;
	}

	@Override
	public String getDescription() {
		return ROLE_DESCRIPTION_R2D2;
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public String getId() {
		return ROLE_ID;
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public boolean isComposite() {
		return false;
	}

	@Override
	public void addCompositeRole(RoleModel role) {
		
	}

	@Override
	public void removeCompositeRole(RoleModel role) {
	}

	@Override
	public Set<RoleModel> getComposites() {
		return null;
	}

	@Override
	public boolean isClientRole() {
		return false;
	}

	@Override
	public String getContainerId() {
		return null;
	}

	@Override
	public RoleContainerModel getContainer() {
		return null;
	}

	@Override
	public boolean hasRole(RoleModel role) {
		return false;
	}

	@Override
	public void setSingleAttribute(String name, String value) {
	}

	@Override
	public void setAttribute(String name, Collection<String> values) {
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

}
