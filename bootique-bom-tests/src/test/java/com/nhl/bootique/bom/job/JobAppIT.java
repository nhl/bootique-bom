package com.nhl.bootique.bom.job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nhl.bootique.command.CommandOutcome;

public class JobAppIT {

	private JobApp app;
	private ExecutorService executor;

	@Before
	public void before() {
		this.app = new JobApp();
		this.executor = Executors.newSingleThreadExecutor();
	}

	@After
	public void after() throws InterruptedException {
		executor.shutdownNow();
		executor.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Test
	public void testRun_Help() {
		CommandOutcome outcome = app.run("--help");
		assertEquals(0, outcome.getExitCode());

		String help = app.getStdout();

		assertTrue(help.contains("--exec"));
		assertTrue(help.contains("--list"));
		assertTrue(help.contains("--schedule"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
		assertTrue(help.contains("--job"));
	}

	@Test
	public void testList() throws InterruptedException, ExecutionException, TimeoutException {
		CommandOutcome outcome = app.run("--list");
		assertEquals(0, outcome.getExitCode());

		String stdout = app.getStdout();
		assertTrue(stdout.contains("- bom"));
	}

	@Test
	public void testList_BadConfig_Ignored() throws InterruptedException, ExecutionException, TimeoutException {
		CommandOutcome outcome = app.run("--config=src/test/resources/com/nhl/bootique/bom/job/no-such.yml", "--list");
		assertEquals(0, outcome.getExitCode());

		String stdout = app.getStdout();
		assertTrue(stdout.contains("- bom"));
	}

	@Test
	public void testExec() throws InterruptedException, ExecutionException, TimeoutException {

		BomJob.lastResult = null;

		CommandOutcome outcome = app.run("--exec", "--job=bom");
		assertEquals(0, outcome.getExitCode());
		assertEquals("bom-job-finished", BomJob.lastResult);
	}

}
