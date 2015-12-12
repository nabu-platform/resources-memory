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
	public URI getURI() {
		try {
			return new URI("memory:" + ResourceUtils.getPath(this));
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return getURI().toString();
	}
}
