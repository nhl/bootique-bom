package com.nhl.bootique.bom.jetty;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.BQCoreModule;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.jdbc.JdbcModule;

public class JettyApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		bootique.module(JdbcModule.class).override(BQCoreModule.class).with(this);
	}

	@Override
	public void configure(Binder binder) {
		// TODO Auto-generated method stub

	}
}
