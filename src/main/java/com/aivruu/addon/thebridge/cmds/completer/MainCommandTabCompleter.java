package com.aivruu.addon.thebridge.cmds.completer;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommandTabCompleter implements TabCompleter {
	private final List<String> arguments;
	
	public MainCommandTabCompleter() {
		arguments = Lists.newArrayList("about", "reload");
	}
	
	@Override
	public @Nullable List<String> onTabComplete(
		final @NotNull CommandSender sender,
		final @NotNull Command command,
		final @NotNull String label,
		final @NotNull String[] args
	) {
		final List<String> results = new ArrayList<>();
		// Checks if the command has possible arguments.
		if (args.length < 1)  {
			return Collections.emptyList();
		}
		for (final String possibleResult : arguments) {
			// Checks if the first argument start with some value from list.
			if (!args[0].startsWith(possibleResult)) {
				continue;
			}
			results.add(possibleResult);
		}
		return results;
	}
}
