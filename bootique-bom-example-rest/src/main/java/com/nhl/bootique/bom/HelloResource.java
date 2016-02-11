package com.nhl.bootique.bom;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;
import com.nhl.bootique.annotation.Args;

@Path("/")
public class HelloResource {
	
	@Inject
	@Args
	private String[] args;

	@GET
	public String hello() {
		
		String allArgs = Arrays.asList(args).stream().collect(joining(" "));
		return "Hello, world! The app was started with the following arguments: " + allArgs;
	}
}
