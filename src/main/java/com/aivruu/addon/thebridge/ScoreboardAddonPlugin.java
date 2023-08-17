package com.aivruu.addon.thebridge;

import com.aivruu.addon.thebridge.cmds.MainCommand;
import com.aivruu.addon.thebridge.impl.ScoreboardManagerModelImpl;
import com.aivruu.addon.thebridge.impl.SimpleConfManagerModelImpl;
import com.aivruu.addon.thebridge.listener.GameScoreboardListener;
import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import com.aivruu.addon.thebridge.utils.LoggerUtils;
import com.google.common.base.Preconditions;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

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
		
		// Load the required libraries by the plugin.
		LibraryHandler.addCustomRepo("https://nexus.iridiumdevelopment.net/repositories/maven-releases/");
		LibraryHandler.loadLibraries(
			"fr{}mrmicky:fastboard:2.0.0",
			"org{}yaml:snakeyaml:1.33",
			"space{}arim{}dazzleconf:dazzleconf-core:1.3.0-M2",
			"space{}arim{}dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2",
			"com{}iridium:IridiumColorAPI:1.0.6"
		);
		
		// Load the components for the configuration before load the files.
		loadConfigurationComponents();
		if (!confManager.wasLoaded()) {
			LoggerUtils.error("Could not load configuration correctly.");
			setEnabled(false);
			return;
		}
		
		scoreboardManager = new ScoreboardManagerModelImpl(confManager.config(), getServer().getScheduler());
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new GameScoreboardListener(scoreboardManager), plugin);
		
		Preconditions.checkNotNull(getCommand("thebridgeaddon")).setExecutor(new MainCommand(confManager, scoreboardManager));
	}
	
	@Override
	public void onDisable() {
		if (scoreboardManager != null) {
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
	
	private void loadConfigurationComponents() {
		// Set default options for the YAML file.
		final SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
			.commentMode(CommentMode.fullComments())
			.build();
		// Set default options for the configuration.
		final ConfigurationOptions buildOptions = new ConfigurationOptions.Builder()
			.sorter(new AnnotationBasedSorter())
			.build();
		// Creates a ConfigurationFactory object for the configuration model (ConfModel).
		final ConfigurationFactory<ConfModel> confFactory = SnakeYamlConfigurationFactory.create(
			ConfModel.class,
			buildOptions,
			yamlOptions
		);
		
		// Initializes ConfManager using the parameters given.
		confManager = new SimpleConfManagerModelImpl(new ConfigurationHelper<>(getDataFolder().toPath(), "config.yml", confFactory));
	}
}
