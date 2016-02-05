package com.nhl.bootique.bom.logback;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

import com.nhl.bootique.command.CommandOutcome;

public class LogbackAppIT {

	private LogbackApp app;

	@Before
	public void before() {
		this.app = new LogbackApp();
	}

	private File prepareLogFile(String name) {
		File logFile = new File(name);
		logFile.delete();
		assertFalse(logFile.exists());

		return logFile;
	}

	@Test
	public void testRun_Help() {
		CommandOutcome outcome = app.run("--help");
		assertEquals(0, outcome.getExitCode());

		String help = app.getStdout();

		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun_Debug() throws IOException {
		File logFile = prepareLogFile("target/logback/testRun_Debug.log");

		CommandOutcome outcome = app.run("--config=src/test/resources/com/nhl/bootique/bom/logback/test-debug.yml");
		assertEquals(0, outcome.getExitCode());

		assertTrue(logFile.isFile());

		String logfileContents = Files.lines(logFile.toPath()).collect(joining("\n"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-debug"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-info"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-warn"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-error"));
	}

	@Test
	public void testRun_Warn() throws IOException {
		File logFile = prepareLogFile("target/logback/testRun_Warn.log");

		CommandOutcome outcome = app.run("--config=src/test/resources/com/nhl/bootique/bom/logback/test-warn.yml");
		assertEquals(0, outcome.getExitCode());

		assertTrue(logFile.isFile());

		String logfileContents = Files.lines(logFile.toPath()).collect(joining("\n"));
		assertFalse(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-debug"));
		assertFalse(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-info"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-warn"));
		assertTrue(logfileContents.contains("c.n.b.b.l.LogbackTestCommand: logback-test-error"));
	}

}
