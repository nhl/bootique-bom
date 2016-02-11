package com.nhl.bootique.bom.jersey;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.bom.jersey.r1.Resource1;
import com.nhl.bootique.bom.jersey.r2.Resource2;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.jetty.JettyModule;

public class JerseyApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		Module jersey = JerseyModule.builder().packageRoot(Resource1.class).resource(Resource2.class).build();
		bootique.modules(this, jersey).module(JettyModule.class);
	}

	@Override
	public void configure(Binder binder) {
		JerseyModule.contributeFeatures(binder).addBinding().to(JerseyAppFeature.class);
	}
}
