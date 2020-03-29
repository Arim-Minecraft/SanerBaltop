/* 
 * SanerBaltop
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * SanerBaltop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SanerBaltop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SanerBaltop. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.sanerbaltop;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import space.arim.universal.registry.UniversalRegistry;

import space.arim.api.uuid.PlayerNotFoundException;
import space.arim.api.uuid.UUIDResolver;

public class BaltopCommand implements CommandExecutor {

	private final SanerBaltopPlugin plugin;
	
	BaltopCommand(SanerBaltopPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int page = 1;
		if (args.length > 0) {
			try {
				page = Integer.parseInt(args[0]);
				if (page < 1) {
					sendMessage(sender, "&6Arim>> &cInvalid page: &e" + page + "&c.");
					return true;
				}
			} catch (NumberFormatException ignored) {
				sendMessage(sender, "&6Arim>> &cInvalid page: &e" + page + "&c.");
				return true;
			}
		}
		int perpage = plugin.config.getInt("per-page");
		int size = plugin.topBalances.size();
		int totalpage = size / perpage + ((size % perpage == 0) ? 0 : 1); // ceiling
		if (page > totalpage) {
			sendMessage(sender, "&6Arim>> &cMaximum page is &e" + totalpage + "&c.");
			return true;
		}
		int offset = (page - 1)*perpage;
		for (int n = 0; n < plugin.topBalances.size(); n++) {
			if (n >= offset && n < offset + perpage) {
				BaltopEntry entry = plugin.topBalances.get(n);
				sendMessage(sender, "&3" + n + "&7. " + uuidToName(entry.getKey()) + " &a$" + entry.getValue());
			}
		}
		return true;
	}
	
	private String uuidToName(UUID uuid) {
		try {
			return UniversalRegistry.get().getRegistration(UUIDResolver.class).resolveUUID(uuid, false);
		} catch (PlayerNotFoundException ex) {
			return "unknown";
		}
	}
	
	private void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
}
