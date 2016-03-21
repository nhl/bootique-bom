package com.nhl.bootique.bom.mvc;

import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.bom.jersey.r1.Resource1;
import com.nhl.bootique.bom.mvc.r1.MustacheResource;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.mvc.MvcModule;
import com.nhl.bootique.mvc.mustache.MvcMustacheModule;

public class MustacheApp extends BomTestApp {

	@Override
	protected void configure(Bootique bootique) {
		Module jersey = JerseyModule.builder().packageRoot(Resource1.class).resource(MustacheResource.class).build();
		bootique.modules(JettyModule.class, MvcModule.class, MvcMustacheModule.class).modules(jersey);
	}

}
