package be.nabu.libs.resources.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import be.nabu.libs.resources.api.ManageableContainer;

public class MemoryDirectory extends MemoryResource implements ManageableContainer<MemoryResource> {

	private Map<String, MemoryResource> children = new HashMap<String, MemoryResource>();
	
	public MemoryDirectory(String name) {
		super(name);
	}
	
	/**
	 * For automatic creation
	 */
	public MemoryDirectory() {
		super(null);
	}
	
	public Collection<MemoryResource> getChildren() {
		return new ArrayList<MemoryResource>(children.values());
	}

	@Override
	public MemoryResource getChild(String name) {
		return children.get(name);
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE_DIRECTORY;
	}

	@Override
	public synchronized MemoryResource create(String name, String contentType) {
		MemoryResource resource;
		if (contentType.equals(CONTENT_TYPE_DIRECTORY)) {
			resource = new MemoryDirectory(name);
		}
		else {
			resource = new MemoryItem(name);
		}
		children.put(name, resource);
		resource.setParent(this);
		return resource;
	}

	@Override
	public synchronized void delete(String name) {
		children.remove(name);
	}

	@Override
	public Iterator<MemoryResource> iterator() {
		return getChildren().iterator();
	}

}
