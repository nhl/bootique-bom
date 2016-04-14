package com.nhl.bootique.bom.jetty;

import java.util.function.Consumer;

import com.nhl.bootique.Bootique;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.jetty.test.junit.JettyTestFactory;

public class JettyApp extends JettyTestFactory {

	@Override
	public Builder newRuntime() {

		Consumer<Bootique> config = (bootique) -> {
			bootique.module((binder) -> {
				JettyModule.contributeServlets(binder).addBinding().to(BomServlet.class);
				JettyModule.contributeFilters(binder).addBinding().to(BomFilter.class);
			});
		};

		return super.newRuntime().configurator(config);
	}
}
