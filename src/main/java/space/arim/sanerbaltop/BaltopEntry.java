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
import java.util.UUID;

public class BaltopEntry implements Comparable<BaltopEntry>, Cloneable {

	private final UUID uuid;
	private volatile BigDecimal balance;
	
	BaltopEntry(UUID uuid, BigDecimal balance) {
		this.uuid = uuid;
		this.balance = balance;
	}

	public UUID getKey() {
		return uuid;
	}

	public BigDecimal getValue() {
		return balance;
	}

	public void setValue(BigDecimal value) {
		balance = value;
	}

	@Override
	public int compareTo(BaltopEntry o) {
		return (balance.subtract(o.balance)).intValue();
	}
	
	@Override
	public BaltopEntry clone() {
		return new BaltopEntry(uuid, balance);
	}

}
