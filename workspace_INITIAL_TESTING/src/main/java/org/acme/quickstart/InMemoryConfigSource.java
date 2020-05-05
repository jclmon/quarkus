package org.acme.quickstart;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class InMemoryConfigSource implements ConfigSource {

    private Map<String, String> props = new HashMap<>();

    public InMemoryConfigSource() {
        props.put("greeting.message","Memory Hello");
    }

    @Override
    public String getName() {
        return "InMemoryConfig";
    }

    @Override
    public Map<String, String> getProperties() {
        return props;
    }

    @Override
    public String getValue(final String key) {
        return props.get(key);
    }

    @Override
    public int getOrdinal() {
        return 900;
    }

}