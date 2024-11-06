/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.resources.memory;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import be.nabu.libs.resources.ResourceUtils;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceResolver;

/**
 * Important to remember that by default the memory provider is "empty"
 * This means you can only successfully resolve the "root"
 */
public class MemoryResourceResolver implements ResourceResolver {

	private MemoryDirectory root = new MemoryDirectory();
	
	@Override
	public Resource getResource(URI uri, Principal principal) throws IOException {
		return ResourceUtils.resolve(root, uri.getPath());
	}
	
	@Override
	public List<String> getDefaultSchemes() {
		return Arrays.asList(new String [] { "memory" });
	}

}

