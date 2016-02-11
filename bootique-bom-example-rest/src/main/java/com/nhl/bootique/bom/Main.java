package com.nhl.bootique.bom;

import com.nhl.bootique.Bootique;

public class Main {

	public static void main(String[] args) {
		Bootique.app(args).autoLoadModules().run();
	}
}
