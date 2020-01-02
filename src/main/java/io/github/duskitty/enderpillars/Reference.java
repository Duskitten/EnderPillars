package io.github.duskitty.enderpillars;


import io.github.duskitty.enderpillars.init.BlockInit;
import io.github.duskitty.enderpillars.init.EntityInit;
import io.github.duskitty.enderpillars.init.ItemInit;
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