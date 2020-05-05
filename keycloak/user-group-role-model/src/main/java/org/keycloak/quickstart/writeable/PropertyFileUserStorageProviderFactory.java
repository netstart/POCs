package org.keycloak.quickstart.writeable;

import java.io.File;
import java.util.List;

import org.jboss.logging.Logger;
import org.keycloak.common.util.EnvUtil;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class PropertyFileUserStorageProviderFactory
		implements UserStorageProviderFactory<PropertyFileUserStorageProvider> {

	private static final Logger logger = Logger.getLogger(PropertyFileUserStorageProviderFactory.class);

	public static final String PROVIDER_NAME = "user-group-role-model";

	protected static final List<ProviderConfigProperty> configMetadata;

	static {
		configMetadata = ProviderConfigurationBuilder.create()
//                .property().name("path")
//                .type(ProviderConfigProperty.STRING_TYPE)
//                .label("Path")
//                .defaultValue("${jboss.server.config.dir}/example-users.properties")
//                .helpText("File path to properties file")
//                .add()
				.build();
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return configMetadata;
	}

	@Override
	public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
			throws ComponentValidationException {
		String fp = config.getConfig().getFirst("path");
		if (fp == null)
			throw new ComponentValidationException("user property file does not exist");
		fp = EnvUtil.replace(fp);
		File file = new File(fp);
		if (!file.exists()) {
			throw new ComponentValidationException("user property file does not exist");
		}
	}

	@Override
	public String getId() {
		return PROVIDER_NAME;
	}

	@Override
	public PropertyFileUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		String path = model.getConfig().getFirst("path");
		path = EnvUtil.replace(path);

		return new PropertyFileUserStorageProvider(session, model);
	}
}
