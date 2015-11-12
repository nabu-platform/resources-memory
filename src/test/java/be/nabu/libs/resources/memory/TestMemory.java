package be.nabu.libs.resources.memory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import be.nabu.libs.resources.ResourceFactory;
import be.nabu.libs.resources.ResourceUtils;
import be.nabu.libs.resources.api.ManageableContainer;
import be.nabu.libs.resources.api.ReadableResource;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceContainer;
import be.nabu.libs.resources.api.WritableResource;
import be.nabu.utils.io.ContentTypeMap;
import be.nabu.utils.io.IOUtils;
import be.nabu.utils.io.api.ByteBuffer;
import be.nabu.utils.io.api.WritableContainer;
import junit.framework.TestCase;

public class TestMemory extends TestCase {
	
	public void test() throws URISyntaxException, IOException {
		URI uri = new URI("memory:/myrandomelement");
		ResourceContainer<?> root = (ResourceContainer<?>) ResourceFactory.getInstance().resolve(uri, null);
		assertNull(root);
		root = ResourceUtils.mkdir(uri, null);
		assertNotNull(root);
		Resource test = ((ManageableContainer<?>) root).create("test.txt", ContentTypeMap.getInstance().getContentTypeFor("test.txt"));
		WritableContainer<ByteBuffer> writable = ((WritableResource) test).getWritable();
		writable.write(IOUtils.wrap("testing this!".getBytes(), true));
		writable.close();
		assertEquals("testing this!", new String(IOUtils.toBytes(((ReadableResource) test).getReadable())));
		((ManageableContainer<?>) root).delete("test.txt");
		assertNull(ResourceUtils.resolve(root, "test.txt"));
	}
	
}
