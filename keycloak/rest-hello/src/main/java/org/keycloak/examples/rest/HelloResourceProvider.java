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

package org.keycloak.examples.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.examples.rest.movie.ServicesInterface;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class HelloResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public HelloResourceProvider(KeycloakSession session) {
		this.session = session;
	}

	@Override
	public Object getResource() {
		return this;
	}

	@GET
	@Produces("text/plain; charset=utf-8")
	public String get() {
		String path = "http://www.mocky.io/v2/5185415ba171ea3a00704eed"; // https://www.mocky.io/v2/5185415ba171ea3a00704eed
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(path);
		Response response = target.request().get();
		String exampleRequest01 = response.readEntity(String.class);
		System.out.println("=== exampleRequest01 " + exampleRequest01);
		response.close();

		ServicesInterface proxy = (ServicesInterface) target.proxy(ServicesInterface.class);
		System.out.println("=== exampleRequest02" + proxy.get());

		String name = session.getContext().getRealm().getDisplayName();
		if (name == null) {
			name = session.getContext().getRealm().getName();
		}
		return "Hello " + name + " " + exampleRequest01;
	}

	@Override
	public void close() {
	}

}
