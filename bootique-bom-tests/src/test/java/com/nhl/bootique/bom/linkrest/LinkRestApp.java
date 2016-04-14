package com.nhl.bootique.bom.linkrest;

import static org.mockito.Mockito.mock;

import java.util.function.Consumer;

import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.linkrest.r1.LrResource1;
import com.nhl.bootique.cayenne.CayenneModule;
import com.nhl.bootique.jdbc.DataSourceFactory;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.jetty.test.junit.JettyTestFactory;
import com.nhl.bootique.linkrest.LinkRestModule;
import com.nhl.link.rest.meta.LrEntityBuilder;

public class LinkRestApp extends JettyTestFactory {

	@Override
	public Builder newRuntime() {

		Module cayenne = CayenneModule.builder().noConfig().build();
		Module jdbc = b -> b.bind(DataSourceFactory.class).toInstance(mock(DataSourceFactory.class));

		Consumer<Bootique> config = (bootique) -> {
			bootique.modules(JerseyModule.class, LinkRestModule.class).modules(cayenne, jdbc, (binder) -> {
				LinkRestModule.contributeExtraEntities(binder).addBinding()
						.toInstance(LrEntityBuilder.build(ITEntity.class));
				JerseyModule.contributeResources(binder).addBinding().to(LrResource1.class);
			});
		};

		return super.newRuntime().configurator(config);
	}

}
