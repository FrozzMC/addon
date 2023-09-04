package com.aivruu.addon.thebridge;

import com.aivruu.addon.thebridge.cmds.MainCommand;
import com.aivruu.addon.thebridge.cmds.completer.MainCommandTabCompleter;
import com.aivruu.addon.thebridge.enums.Result;
import com.aivruu.addon.thebridge.impl.ScoreboardManagerModelImpl;
import com.aivruu.addon.thebridge.impl.SimpleConfManagerModelImpl;
import com.aivruu.addon.thebridge.listener.GameScoreboardListener;
import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.utils.LoggerUtils;
import com.google.common.base.Preconditions;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScoreboardAddonPlugin extends JavaPlugin {
	private ConfManagerModel confManagerModel;
	private ScoreboardManagerModel scoreboardManagerModel;
	
	@Override
	public void onLoad() {
		// Add the repositories for the dependencies.
		LoggerUtils.info("Adding repositories for download dependencies...");
		LibraryHandler.addMavenCentral();
		LibraryHandler.addJitPack();
		
		// Load the required libraries by the plugin.
		LoggerUtils.info("Downloading required libraries for the plugin.");
		LibraryHandler.loadLibraries(
			"com{}github{}VelexNetwork:iridium-color-api:1.2.0",
			"fr{}mrmicky:fastboard:2.0.0",
			String.format("org{}spongepowered:configurate-gson:%s", Constants.CONFIGURATION_LIBRARY_RELEASE)
		);
		LoggerUtils.info("Loading configuration components...");
		// Load the components for the configuration before load the files.
		confManagerModel = new SimpleConfManagerModelImpl(getDataFolder().toPath());
		if (confManagerModel.load() == Result.NO_CONFIG_LOAD) {
			LoggerUtils.error("Could not load configuration correctly.");
			setEnabled(false);
			return;
		}
		
		scoreboardManagerModel = new ScoreboardManagerModelImpl(this, confManagerModel.config());
	}
	
	@Override
	public void onEnable() {
		scoreboardManagerModel.start();
		
		LoggerUtils.info("Registering plugin listener...");
		getServer().getPluginManager()
			.registerEvents(new GameScoreboardListener(scoreboardManagerModel), this);
		
		LoggerUtils.info("Registering plugin command...");
		final PluginCommand command = Preconditions.checkNotNull(getCommand("thebridgeaddon"));
		command.setExecutor(new MainCommand(confManagerModel, scoreboardManagerModel));
		command.setTabCompleter(new MainCommandTabCompleter());
		
		LoggerUtils.info(
			"The plugin has been enabled successful!",
			"Running on version " + Constants.VERSION
		);
	}
	
	@Override
	public void onDisable() {
		LoggerUtils.info("The plugin is being disabled...");

		// The ScoreboardManagerModel reference not been deleted yet?
		if (scoreboardManagerModel != null) {
			LoggerUtils.info("Stopping all plugin running tasks.");
			scoreboardManagerModel.stop();
		}
	}
}
