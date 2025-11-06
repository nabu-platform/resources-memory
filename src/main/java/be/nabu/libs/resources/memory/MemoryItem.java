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

	private static final int BUFFER_SIZE = 8192;
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
		return IOUtils.bufferWritable(target, ByteBufferFactory.getInstance().newInstance(BUFFER_SIZE, true));
	}

	@Override
	public ReadableContainer<ByteBuffer> getReadable() throws IOException {
		lastAccessed = new Date();
		ReadableContainer<ByteBuffer> cloned = container.duplicate(true);
		cloned.close();
		return IOUtils.bufferReadable(cloned, IOUtils.newByteBuffer(BUFFER_SIZE, true));
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
	
	public void write(byte [] content) {
		try {
			container.reopen();
			container.truncate();
			container.write(content);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public WritableContainer<ByteBuffer> getAppendable() throws IOException {
		// someone may have closed the container
		container.reopen();
		WritableContainer<ByteBuffer> target = maxSize > 0
			? IOUtils.limitWritable(container, maxSize)
			: container;
		return IOUtils.bufferWritable(target, ByteBufferFactory.getInstance().newInstance(BUFFER_SIZE, true));
	}
	
	public MemoryReadOnlyItem toReadOnly() {
		return new MemoryReadOnlyItem(this);
	}
}
