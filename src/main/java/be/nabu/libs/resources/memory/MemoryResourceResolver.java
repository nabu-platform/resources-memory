package be.nabu.libs.resources.memory;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import be.nabu.libs.resources.ResourceUtils;
import be.nabu.libs.resources.api.Resource;
import be.nabu.libs.resources.api.ResourceResolver;

/**
 * Important to remember that by default the memory provider is "empty"
 * This means you can only successfully resolve the "root"
 */
public class MemoryResourceResolver implements ResourceResolver {

	private MemoryDirectory root = new MemoryDirectory();
	
	@Override
	public Resource getResource(URI uri, Principal principal) throws IOException {
		return ResourceUtils.resolve(root, uri.getPath());
	}
	
	@Override
	public List<String> getDefaultSchemes() {
		return Arrays.asList(new String [] { "memory" });
	}

}

