package com.aivruu.addon.thebridge.impl;

import com.aivruu.addon.thebridge.enums.Result;
import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import org.jetbrains.annotations.NotNull;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.io.IOException;
import java.nio.file.Path;

public class SimpleConfManagerModelImpl implements ConfManagerModel {
	private final ConfigurationHelper<ConfModel> confHelper;
	
	private ConfModel config;
	
	public SimpleConfManagerModelImpl(final @NotNull Path dataFolder) {
		confHelper = new ConfigurationHelper<>(dataFolder, "config.yml", SnakeYamlConfigurationFactory.create(
			ConfModel.class,
			new ConfigurationOptions.Builder()
			.sorter(new AnnotationBasedSorter())
			.build(),
			new SnakeYamlOptions.Builder()
			.commentMode(CommentMode.alternativeWriter())
			.build()
		));
	}
	
	@Override
	public @NotNull Result load() {
		try {
			config = confHelper.reloadConfigData();
		} catch (final IOException | InvalidConfigException exception) {
			exception.printStackTrace();
			return Result.NO_CONFIG_LOAD;
		}
		return Result.SUCCESS;
	}
	
	@Override
	public @NotNull ConfModel config() {
		return config;
	}
}
