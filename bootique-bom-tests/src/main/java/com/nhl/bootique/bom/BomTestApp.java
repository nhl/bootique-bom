package com.nhl.bootique.bom;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.nhl.bootique.Bootique;
import com.nhl.bootique.command.CommandOutcome;
import com.nhl.bootique.log.BootLogger;
import com.nhl.bootique.log.DefaultBootLogger;

/**
 * A base app class for BOM integration tests.
 */
public abstract class BomTestApp {

	private ByteArrayOutputStream stdout;
	private ByteArrayOutputStream stderr;

	public BomTestApp() {
		stdout = new ByteArrayOutputStream();
		stderr = new ByteArrayOutputStream();
	}

	public CommandOutcome run(String... args) {
		Bootique bootique = Bootique.app(args).bootLogger(createBootLogger());
		configure(bootique);
		return bootique.runtime().getRunner().run();
	}

	protected abstract void configure(Bootique bootique);

	protected BootLogger createBootLogger() {
		return new DefaultBootLogger(true, new TracingPrintStream(stdout), new TracingPrintStream(stderr));
	}

	public String getStdout() {
		return new String(stdout.toByteArray(), Charset.forName("UTF-8"));
	}

	public String getStderr() {
		return new String(stderr.toByteArray(), Charset.forName("UTF-8"));
	}
}
