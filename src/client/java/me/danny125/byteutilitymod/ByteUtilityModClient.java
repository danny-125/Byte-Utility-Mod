package me.danny125.byteutilitymod;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtilityModClient implements ClientModInitializer {

	public static final String MOD_ID = "byte-utility-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		Initialize.InitializeMod();
	}
}