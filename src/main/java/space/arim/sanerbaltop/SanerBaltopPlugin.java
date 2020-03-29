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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.appledash.saneeconomy.ISaneEconomy;
import org.appledash.saneeconomy.economy.economable.Economable;
import org.appledash.saneeconomy.economy.economable.EconomablePlayer;
import org.appledash.saneeconomy.vault.EconomySaneEconomy;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class SanerBaltopPlugin extends JavaPlugin {

	final List<BaltopEntry> topBalances = new ArrayList<BaltopEntry>();
	Config config;
	private ISaneEconomy economy;

	@Override
	public void onEnable() {
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		Economy econ = (rsp != null) ? rsp.getProvider() : null;
		if (!(econ instanceof EconomySaneEconomy)) {
			getLogger().warning("SaneEconomy not installed");
			return;
		}
		this.economy = (ISaneEconomy) getServer().getPluginManager().getPlugin("SaneEconomy");

		config = new Config(getDataFolder());
		getServer().getPluginCommand("sanerbaltop").setExecutor(new BaltopCommand(this));
		getServer().getPluginManager().registerEvents(new BalanceChangeListener(this), this);

		for (OfflinePlayer player : getServer().getOfflinePlayers()) {

			addEntry(player.getUniqueId(), economy.getEconomyManager().getBalance(Economable.wrap(player)));
		}
	}

	private void addAndSort(UUID uuid, BigDecimal balance) {
		topBalances.add(new BaltopEntry(uuid, balance));
		topBalances.sort(null);
	}
	
	private void addEntry(UUID uuid, BigDecimal balance) {
		int count = config.getCount();
		if (topBalances.size() < count) {
			addAndSort(uuid, balance);

		} else if (topBalances.get(count - 1).getValue().doubleValue() < balance.doubleValue()) {
			addAndSort(uuid, balance);
			topBalances.remove(count);
		}
	}

	void update(Economable target, BigDecimal delta) {
		if (target instanceof EconomablePlayer) {
			UUID uuid = UUID.fromString(((EconomablePlayer) target).getUniqueIdentifier().substring(7));
			BigDecimal result = economy.getEconomyManager().getBalance(target).add(delta);

			for (int n = 0; n < topBalances.size(); n++) {
				BaltopEntry entry = topBalances.get(n);
				if (entry.getKey().equals(uuid)) {
					entry.setValue(result);
					topBalances.sort(null);
					return;
				}
			}
			addEntry(uuid, result);
		}
	}

	@Override
	public void onDisable() {
		topBalances.clear();
	}
	
}