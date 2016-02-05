package com.nhl.bootique.bom.jetty;

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

public class JettyAppIT {

	private JettyApp app;
	private ExecutorService executor;

	@Before
	public void before() {
		this.app = new JettyApp();
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

		assertTrue(help.contains("--server"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun() throws InterruptedException, ExecutionException, TimeoutException {

		// since Jetty main thread blocks, run the server in a separate
		// thread...
		Future<CommandOutcome> result = executor.submit(() -> {
			return app.run("--config=src/test/resources/com/nhl/bootique/bom/jetty/test.yml", "--server");
		});

		// wait for Jetty to start and run some web requests...
		Thread.sleep(1000);
		WebTarget base = ClientBuilder.newClient().target("http://localhost:11234/");

		Response r1 = base.path("/testc").request().get();
		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		String expected1 = String.format("bom_filter_before%nbom_servlet_query_string: null%nbom_filter_after%n");
		assertEquals(expected1, r1.readEntity(String.class));

		Response r2 = base.path("/testb").request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), r2.getStatus());

		Response r3 = base.path("/testc").queryParam("p", "v").request().get();
		assertEquals(Status.OK.getStatusCode(), r3.getStatus());
		String expected3 = String.format("bom_filter_before%nbom_servlet_query_string: p=v%nbom_filter_after%n");
		assertEquals(expected3, r3.readEntity(String.class));

		// since we exited via interrupt, the result of the --server command
		// will look like a failure
		executor.shutdownNow();
		CommandOutcome outcome = result.get(3, TimeUnit.SECONDS);
		assertEquals(1, outcome.getExitCode());
	}
}
