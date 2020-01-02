package com.duskitty.enderpillars;

import com.duskitty.enderpillars.init.*;

import net.fabricmc.api.ModInitializer;


public class Reference implements ModInitializer{
	public static final String MODID = "enderpillars";

	@Override
	public void onInitialize() {
		BlockInit.Loader();
		EntityInit.Loader();
		ItemInit.Loader();
	}

}