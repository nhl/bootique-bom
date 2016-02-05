package com.nhl.bootique.bom.logback;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.nhl.bootique.BQCoreModule;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.annotation.DefaultCommand;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.command.Command;
import com.nhl.bootique.logback.LogbackModule;

public class LogbackApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		bootique.module(LogbackModule.class).override(BQCoreModule.class).with(this);
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(Command.class).annotatedWith(DefaultCommand.class).to(LogbackTestCommand.class).in(Singleton.class);
	}
}
