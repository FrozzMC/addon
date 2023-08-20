package com.aivruu.addon.thebridge.cmds.completer;

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
		arguments = new ArrayList<>();
	}
	
	@Override
	public @Nullable List<String> onTabComplete(
		final @NotNull CommandSender sender,
		final @NotNull Command command,
		final @NotNull String label,
		final @NotNull String[] args
	) {
		if (arguments.isEmpty()) {
			arguments.add("help");
			arguments.add("reload");
		}
		
		final List<String> results = new ArrayList<>();
		
		if (args.length == 1)  {
			for (final String possibleResult : arguments) {
				if (!args[0].startsWith(possibleResult)) continue;
				
				results.add(possibleResult);
			}
			
			return results;
		}
		
		return Collections.emptyList();
	}
}
