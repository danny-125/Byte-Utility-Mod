package me.danny125.byteutilitymod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.danny125.byteutilitymod.Initialize;

public class ByteUtilityMod implements ModInitializer {
	public static final String MOD_ID = "byte-utility-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//create instance so that you can reference things in this class
	public static ByteUtilityMod INSTANCE = new ByteUtilityMod();

	public static String MOD_VERSION = "0.1";

	@Override
	public void onInitialize() {
		boolean init = Initialize.InitializeMod();
		if(init){
			LOGGER.info("Successfully loaded Byte Utility Mod!");
		}else{
			LOGGER.error("Failed to load Byte Utility Mod...");
		}
	}
}