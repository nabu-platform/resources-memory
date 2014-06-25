package be.nabu.libs.resources.memory;

import java.io.IOException;
import java.util.Date;

import be.nabu.libs.resources.api.FiniteResource;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.TimestampedResource;
import be.nabu.libs.resources.api.WritableResource;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;
import be.nabu.utils.io.api.WritableContainer;
import be.nabu.utils.io.buffers.bytes.DynamicByteBuffer;

public class MemoryItem extends MemoryResource implements ReadableResource, WritableResource, FiniteResource, TimestampedResource {

	private DynamicByteBuffer container = new DynamicByteBuffer();

	public MemoryItem(String name) {
		super(name);
		container.mark();
	}
	
	@Override
	public WritableContainer<ByteBuffer> getWritable() {
		// someone may have closed the container
		container.reopen();
		// truncate all the data inside of it
		container.truncate();
		return container;
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		ReadableContainer<ByteBuffer> cloned = container.duplicate(true);
		cloned.close();
		return cloned;
	}

	@Override
	public long getSize() {
		return container.remainingData();
	}

	@Override
	public Date getLastModified() {
		return container.getLastModified();
	}
}
