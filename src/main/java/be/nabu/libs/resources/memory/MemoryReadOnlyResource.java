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
		return (ResourceContainer<?>) wrap(item);
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
