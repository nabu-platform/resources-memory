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
