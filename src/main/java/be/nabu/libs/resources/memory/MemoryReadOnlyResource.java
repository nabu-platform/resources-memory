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

import be.nabu.libs.resources.api.ResourceContainer;

public class MemoryReadOnlyResource<R extends MemoryResource> extends MemoryResource {
	
	private R item;

	public MemoryReadOnlyResource(R item) {
		super(item.getName());
		this.item = item;
	}

	@Override
	public ResourceContainer<?> getParent() {
		return item.getParent() instanceof MemoryResource ? (ResourceContainer<?>) wrap((MemoryResource) item.getParent()) : item.getParent();
	}

	@Override
	public void setParent(ResourceContainer<?> parent) {
		throw new IllegalStateException("Can not update the parent of a read only memory item");
	}
	
	protected MemoryResource wrap(MemoryResource resource) {
		if (resource instanceof MemoryDirectory) {
			return new MemoryReadOnlyDirectory((MemoryDirectory) resource);
		}
		else if (resource instanceof MemoryItem) {
			return new MemoryReadOnlyItem((MemoryItem) resource);
		}
		else {
			return resource;
		}
	}
	
	protected R getWrapped() {
		return item;
	}
}
