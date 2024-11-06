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

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import be.nabu.libs.resources.ResourceUtils;
import be.nabu.libs.resources.api.LocatableResource;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceContainer;
import be.nabu.utils.io.ContentTypeMap;

public class MemoryResource implements Resource, Closeable, LocatableResource {

	private String name;
	private ResourceContainer<?> parent;
	
	public MemoryResource(String name) {
		this.name = name;
	}
	
	@Override
	public String getContentType() {
		return ContentTypeMap.getInstance().getContentTypeFor(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ResourceContainer<?> getParent() {
		return parent;
	}
	
	public void setParent(ResourceContainer<?> parent) {
		this.parent = parent;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void close() throws IOException {
		// do nothing
	}

	@Override
	public URI getUri() {
		try {
			return new URI("memory:" + ResourceUtils.getPath(this));
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return getUri().toString();
	}
}
