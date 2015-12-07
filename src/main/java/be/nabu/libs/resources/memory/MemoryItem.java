package be.nabu.libs.resources.memory;

import java.io.IOException;
import java.util.Date;

import be.nabu.libs.resources.api.AccessTrackingResource;
import be.nabu.libs.resources.api.AppendableResource;
import be.nabu.libs.resources.api.FiniteResource;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.TimestampedResource;
import be.nabu.utils.io.IOUtils;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.ReadableContainer;
import be.nabu.utils.io.api.WritableContainer;
import be.nabu.utils.io.buffers.bytes.ByteBufferFactory;
import be.nabu.utils.io.buffers.bytes.DynamicByteBuffer;

public class MemoryItem extends MemoryResource implements ReadableResource, AppendableResource, FiniteResource, TimestampedResource, AccessTrackingResource {

	private DynamicByteBuffer container = new DynamicByteBuffer();
	private Date lastAccessed = new Date();
	private long maxSize;
	
	public MemoryItem(String name) {
		this(name, 0);
	}
	
	public MemoryItem(String name, long maxSize) {
		super(name);
		this.maxSize = maxSize;
		container.mark();
	}
	
	@Override
	public WritableContainer<ByteBuffer> getWritable() {
		// someone may have closed the container
		container.reopen();
		// truncate all the data inside of it
		container.truncate();
		WritableContainer<ByteBuffer> target = maxSize > 0
			? IOUtils.limitWritable(container, maxSize)
			: container;
		return IOUtils.bufferWritable(target, ByteBufferFactory.getInstance().newInstance(4096, true));
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		lastAccessed = new Date();
		ReadableContainer<ByteBuffer> cloned = container.duplicate(true);
		cloned.close();
		return IOUtils.bufferReadable(cloned, IOUtils.newByteBuffer(4096, true));
	}

	@Override
	public long getSize() {
		return container.remainingData();
	}

	@Override
	public Date getLastModified() {
		return container.getLastModified();
	}

	@Override
	public Date getLastAccessed() {
		return lastAccessed; 
	}

	@Override
	public WritableContainer<ByteBuffer> getAppendable() throws IOException {
		// someone may have closed the container
		container.reopen();
		WritableContainer<ByteBuffer> target = maxSize > 0
			? IOUtils.limitWritable(container, maxSize)
			: container;
		return IOUtils.bufferWritable(target, ByteBufferFactory.getInstance().newInstance(4096, true));
	}
}
