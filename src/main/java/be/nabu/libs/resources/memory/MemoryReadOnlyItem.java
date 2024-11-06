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
import java.util.Date;

import be.nabu.libs.resources.api.AccessTrackingResource;
import be.nabu.libs.resources.api.FiniteResource;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.TimestampedResource;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;

public class MemoryReadOnlyItem extends MemoryReadOnlyResource<MemoryItem> implements ReadableResource, FiniteResource, TimestampedResource, AccessTrackingResource {

	public MemoryReadOnlyItem(MemoryItem item) {
		super(item);
	}
	
	@Override
	public Date getLastAccessed() {
		return getWrapped().getLastAccessed();
	}

	@Override
	public Date getLastModified() {
		return getWrapped().getLastModified();
	}

	@Override
	public long getSize() {
		return getWrapped().getSize();
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		return getWrapped().getReadable();
	}

}
