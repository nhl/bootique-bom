package com.nhl.bootique.bom.jetty;

import java.util.Collections;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.bom.BomTestApp;
import com.nhl.bootique.jetty.JettyModule;
import com.nhl.bootique.jetty.MappedFilter;
import com.nhl.bootique.jetty.MappedServlet;

public class JettyApp extends BomTestApp implements Module {

	@Override
	protected void configure(Bootique bootique) {
		bootique.module(JettyModule.class).module(this);
	}

	@Override
	public void configure(Binder binder) {

		Set<String> urlPatterns = Collections.singleton("/*");
		Servlet servlet = new BomServlet();
		JettyModule.contributeMappedServlets(binder).addBinding().toInstance(new MappedServlet(servlet, urlPatterns));

		Filter filter = new BomFilter();
		JettyModule.contributeMappedFilters(binder).addBinding().toInstance(new MappedFilter(filter, urlPatterns, 0));
	}
}
