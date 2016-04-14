package com.nhl.bootique.bom;

import com.nhl.bootique.Bootique;
import com.nhl.bootique.jersey.JerseyModule;

public class Application {

	public static void main(String[] args) {
		Bootique.app(args)
				.module((binder) -> JerseyModule.contributeResources(binder).addBinding().to(HelloResource.class))
				.autoLoadModules().run();
	}
}
