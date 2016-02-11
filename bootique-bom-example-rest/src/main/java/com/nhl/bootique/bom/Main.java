package com.nhl.bootique.bom;

import com.nhl.bootique.Bootique;
import com.nhl.bootique.jersey.JerseyModule;

public class Main {

	public static void main(String[] args) {
		JerseyModule jersey = JerseyModule.builder().packageRoot(HelloResource.class).build();
		Bootique.app(args).module(jersey).autoLoadModules().run();
	}
}
