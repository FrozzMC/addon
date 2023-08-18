package com.aivruu.addon.thebridge;

import com.aivruu.addon.thebridge.cmds.MainCommand;
import com.aivruu.addon.thebridge.impl.ScoreboardManagerModelImpl;
import com.aivruu.addon.thebridge.impl.SimpleConfManagerModelImpl;
import com.aivruu.addon.thebridge.listener.GameScoreboardListener;
import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.utils.LoggerUtils;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.TheBridge;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardAddonPlugin extends JavaPlugin {
	private static ScoreboardAddonPlugin plugin;
	
	private ConfManagerModel confManager;
	private ScoreboardManagerModel scoreboardManager;
	
	/**
	 * Returns the current {@code ScoreboardAddonPlugin} instance.
	 *
	 * @return A {@code ScoreboardAddonPlugin} type instance.
	 */
	public static @NotNull ScoreboardAddonPlugin get() {
		if (plugin == null) {
			throw new IllegalStateException("Could not get ScoreboardAddonPlugin instance because is null.");
		}
		
		return plugin;
	}
	
	@Override
	public void onLoad() {
		plugin = this;
		
		// Add the repositories for the dependencies.
		LoggerUtils.info("Adding repositories for download dependencies...");
		LibraryHandler.addMavenCentral();
		LibraryHandler.addJitPack();
		
		// Load the required libraries by the plugin.
		LoggerUtils.info("Downloading required libraries for the plugin.");
		LibraryHandler.loadLibraries(
			"com{}github{}VelexNetwork:iridium-color-api:1.2.0",
			"fr{}mrmicky:fastboard:2.0.0",
			"space{}arim{}dazzleconf:dazzleconf-core:1.3.0-M2",
			"space{}arim{}dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2"
		);
		
		LoggerUtils.info("Loading configuration components...");
		// Load the components for the configuration before load the files.
		confManager = new SimpleConfManagerModelImpl(getDataFolder().toPath());
		if (!confManager.wasLoaded()) {
			LoggerUtils.error("Could not load configuration correctly.");
			setEnabled(false);
			return;
		}
		
		scoreboardManager = new ScoreboardManagerModelImpl(plugin, confManager.config(), getServer().getScheduler());
	}
	
	@Override
	public void onEnable() {
		LoggerUtils.info("Registering plugin listener...");
		getServer().getPluginManager()
			.registerEvents(new GameScoreboardListener(JavaPlugin.getPlugin(TheBridge.class), scoreboardManager), plugin);
		
		LoggerUtils.info("Registering plugin command...");
		Preconditions.checkNotNull(getCommand("thebridgeaddon")).setExecutor(new MainCommand(confManager, scoreboardManager));
	
		LoggerUtils.info(
			"The plugin has been enabled successful!",
			String.format("Running on version %s", Constants.VERSION)
		);
	}
	
	@Override
	public void onDisable() {
		LoggerUtils.info("The plugin is being disabled...");
		if (scoreboardManager != null) {
			LoggerUtils.info("Clearing memory used by the plugin...");
			scoreboardManager.clear();
			scoreboardManager = null;
		}
		
		if (confManager != null) {
			confManager = null;
		}
		
		if (plugin != null) {
			plugin = null;
		}
	}
}
