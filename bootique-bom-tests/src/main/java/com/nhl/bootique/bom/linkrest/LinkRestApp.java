package com.nhl.bootique.bom.linkrest;

import static org.mockito.Mockito.mock;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.bom.linkrest.r1.LrResource1;
import com.nhl.bootique.cayenne.CayenneModule;
import com.nhl.bootique.jdbc.DataSourceFactory;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.linkrest.LinkRestModule;
import com.nhl.link.rest.meta.LrEntityBuilder;

public class LinkRestApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		Module jersey = new JerseyModule().packageRoot(LrResource1.class);
		Module cayenne = new CayenneModule().noConfig();
		Module jdbcModuleReplacement = b -> b.bind(DataSourceFactory.class).toInstance(mock(DataSourceFactory.class));

		bootique.modules(this, jersey, cayenne, jdbcModuleReplacement).modules(JettyModule.class, LinkRestModule.class);
	}

	@Override
	public void configure(Binder binder) {
		LinkRestModule.contributeExtraEntities(binder).addBinding().toInstance(LrEntityBuilder.build(ITEntity.class));
	}
}
