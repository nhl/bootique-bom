package com.nhl.bootique.bom.mvc;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.bom.mvc.r1.MustacheResource;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.mvc.MvcModule;
import com.nhl.bootique.mvc.mustache.MvcMustacheModule;

public class MustacheApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		bootique.modules(JettyModule.class, JerseyModule.class, MvcModule.class, MvcMustacheModule.class).modules(this);
	}

	@Override
	public void configure(Binder binder) {
		JerseyModule.contributeResources(binder).addBinding().to(MustacheResource.class);
	}

}
