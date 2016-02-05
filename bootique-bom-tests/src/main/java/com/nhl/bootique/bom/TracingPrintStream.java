package com.nhl.bootique.bom;

import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TracingPrintStream extends PrintStream {

	private static final Logger LOGGER = LoggerFactory.getLogger(TracingPrintStream.class);

	public TracingPrintStream(OutputStream out) {
		super(out);
	}

	@Override
	public void println(String x) {
		LOGGER.info(x);
		super.println(x);
	}
	
	@Override
	public void println(Object x) {
		LOGGER.info(String.valueOf(x));
		super.println(x);
	}
}
