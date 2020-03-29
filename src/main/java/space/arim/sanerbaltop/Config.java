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

import java.io.File;

import space.arim.api.config.SimpleConfig;

public class Config extends SimpleConfig {

	private volatile int perpage;
	private volatile int maxpages;

	public Config(File folder) {
		super(folder, "config.yml", "do-not-touch-version");
	}

	@Override
	public void reload() {
		super.reload();
		perpage = getInt("per-page");
		maxpages = getInt("max-pages");
	}

	int getPerPage() {
		return perpage;
	}

	int getMaxPages() {
		return maxpages;
	}

	int getCount() {
		return perpage * maxpages;
	}

}
