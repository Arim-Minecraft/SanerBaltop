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
import org.appledash.saneeconomy.economy.transaction.Transaction;
import org.appledash.saneeconomy.event.SaneEconomyTransactionEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BalanceChangeListener implements Listener {

	private final SanerBaltopPlugin plugin;
	
	BalanceChangeListener(SanerBaltopPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onBalanceChange(SaneEconomyTransactionEvent evt) {
		Transaction transaction = evt.getTransaction();
		BigDecimal delta = transaction.getAmount();
		plugin.update(transaction.getReceiver(), delta);
		plugin.update(transaction.getSender(), delta.negate());
	}
	
}
