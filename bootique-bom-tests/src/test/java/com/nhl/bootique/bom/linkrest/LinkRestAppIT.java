package com.nhl.bootique.bom.linkrest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Rule;
import org.junit.Test;

import com.nhl.bootique.command.CommandOutcome;
import com.nhl.bootique.test.BQDaemonTestRuntime;

public class LinkRestAppIT {

	@Rule
	public LinkRestApp app = new LinkRestApp();

	@Test
	public void testRun_NoCommand() {
		BQDaemonTestRuntime runtime = app.newRuntime().startupAndWaitCheck().start();

		CommandOutcome outcome = runtime.getOutcome().get();
		assertEquals(0, outcome.getExitCode());

		String help = runtime.getStdout();

		assertTrue(help.contains("--server"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun_Help() {
		BQDaemonTestRuntime runtime = app.newRuntime().startupAndWaitCheck().start("--help");

		CommandOutcome outcome = runtime.getOutcome().get();
		assertEquals(0, outcome.getExitCode());

		String help = runtime.getStdout();

		assertTrue(help.contains("--server"));
		assertTrue(help.contains("--help"));
		assertTrue(help.contains("--config"));
	}

	@Test
	public void testRun() throws InterruptedException, ExecutionException, TimeoutException {

		app.newRuntime().startServer("--config=src/test/resources/com/nhl/bootique/bom/linkrest/test.yml");

		WebTarget base = ClientBuilder.newClient().target("http://localhost:12011/");

		// added as a part of a package
		Response r1 = base.path("/lr/lrservlet/lr1").request().get();
		assertEquals(Status.OK.getStatusCode(), r1.getStatus());
		String expected1 = "{\"data\":[{\"id\":5,\"name\":\"name5\"},{\"id\":6,\"name\":\"name6\"}],\"total\":2}";
		assertEquals(expected1, r1.readEntity(String.class));
	}

}
