package be.nabu.libs.resources.memory;

import java.util.Iterator;

import be.nabu.libs.resources.api.ResourceContainer;

public class MemoryReadOnlyDirectory extends MemoryReadOnlyResource<MemoryDirectory> implements ResourceContainer<MemoryResource> {

	public MemoryReadOnlyDirectory(MemoryDirectory directory) {
		super(directory);
	}

	@Override
	public Iterator<MemoryResource> iterator() {
		final Iterator<MemoryResource> iterator = getWrapped().iterator();
		return new Iterator<MemoryResource>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			@Override
			public MemoryResource next() {
				return wrap(iterator.next());
			}
		};
	}

	@Override
	public MemoryResource getChild(String name) {
		return wrap(getWrapped().getChild(name));
	}

}
