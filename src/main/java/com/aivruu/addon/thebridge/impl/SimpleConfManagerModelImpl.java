package com.aivruu.addon.thebridge.impl;

import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import com.aivruu.addon.thebridge.utils.LoggerUtils;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.io.IOException;

public class SimpleConfManagerModelImpl implements ConfManagerModel {
	private final ConfigurationHelper<ConfModel> confHelper;
	
	private ConfModel config;
	
	public SimpleConfManagerModelImpl(final @NotNull ConfigurationHelper<ConfModel> confHelper) {
		this.confHelper = Preconditions.checkNotNull(confHelper, "ConfigurationHelper object for ConfModel configuration model cannot be null.");
	}
	
	@Override
	public boolean wasLoaded() {
		try {
			config = confHelper.reloadConfigData();
		} catch (final IOException | InvalidConfigException exception) {
			exception.printStackTrace();
			config = null;
			return false;
		}
		
		return true;
	}
	
	@Override
	public @NotNull ConfModel config() {
		if (config == null) {
			throw new IllegalStateException("Configuration model has not been initialized/loaded yet.");
		}
		
		return config;
	}
}
