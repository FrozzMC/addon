package com.aivruu.addon.thebridge.utils;

import com.aivruu.addon.thebridge.ScoreboardAddonPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {
	private static final Logger LOGGER = ScoreboardAddonPlugin.getPlugin(ScoreboardAddonPlugin.class).getLogger();
	
	public static void info(final String @NotNull... logs) {
		for (final String message : logs) {
			LOGGER.log(Level.INFO, message);
		}
	}
	
	public static void warn(final String @NotNull... logs) {
		for (final String message : logs) {
			LOGGER.log(Level.WARNING, message);
		}
	}
	
	public static void error(final String @NotNull... logs) {
		for (final String message : logs) {
			LOGGER.log(Level.SEVERE, message);
		}
	}
}
