package com.keycloak.provider.user.hardcoded;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;

public class ScaUserStorageProviderFactory implements UserStorageProviderFactory<ScaUserStorageProvider> {

	public static final String PROVIDER_NAME = "SCA User HARDCODED Provider";

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return new ArrayList<ProviderConfigProperty>();
	}

	@Override
	public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel componentModel)
			throws ComponentValidationException {
	}

	@Override
	public String getId() {
		return PROVIDER_NAME;
	}

	@Override
	public ScaUserStorageProvider create(KeycloakSession session, ComponentModel componentModel) {
		return new ScaUserStorageProvider(session, componentModel);
	}
}
