package com.nhl.bootique.bom.jersey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nhl.bootique.command.CommandOutcome;

public class JerseyAppIT {

	private JerseyApp app;
	private ExecutorService executor;

	@Before
	public void before() {
		this.app = new JerseyApp();
		this.executor = Executors.newSingleThreadExecutor();
	}

	@After
	public void after() throws InterruptedException {
		executor.shutdownNow();
		executor.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Test
	public void testRun_NoCommand() {
		CommandOutcome outcome = app.run();
		assertEquals(0, outcome.getExitCode());

		String help = app.getStdout();

		assertTrue(help.contains("--server"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun_Help() {
		CommandOutcome outcome = app.run("--help");
		assertEquals(0, outcome.getExitCode());

		String help = app.getStdout();

		assertTrue(help.contains("--server"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun() throws InterruptedException, ExecutionException, TimeoutException {

		// since Jetty main thread blocks, run the server in a separate
		// thread...
		Future<CommandOutcome> result = executor.submit(() -> {
			return app.run("--config=src/test/resources/com/nhl/bootique/bom/jersey/test.yml", "--server");
		});

		// wait for Jetty to start and run some web requests...
		Thread.sleep(2000);
		WebTarget base = ClientBuilder.newClient().target("http://localhost:12009/");

		// added as a part of a package
		Response r1 = base.path("/jt/jr/1").request().get();
		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		String expected1 = "bootique-jersey-resource1:--config=src/test/resources/com/nhl/bootique/bom/jersey/test.yml,--server";
		assertEquals(expected1, r1.readEntity(String.class));
		
		// added as individual resource
		Response r2 = base.path("/jt/jr/2").request().get();
		assertEquals(Status.OK.getStatusCode(), r2.getStatus());
		String expected2 = "bootique-jersey-resource2:--config=src/test/resources/com/nhl/bootique/bom/jersey/test.yml,--server";
		assertEquals(expected2, r2.readEntity(String.class));

		// since we exited via interrupt, the result of the --server command
		// will look like a failure
		executor.shutdownNow();
		CommandOutcome outcome = result.get(3, TimeUnit.SECONDS);
		assertEquals(1, outcome.getExitCode());
	}

}
