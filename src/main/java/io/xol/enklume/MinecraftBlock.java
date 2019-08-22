package io.xol.enklume;

import io.xol.enklume.util.BlockProperty;
import io.xol.enklume.util.Coords;

import java.util.HashMap;
import java.util.Map;

public class MinecraftBlock {

    private Coords coordinates;
    private String namespace;
    private String name;
    private Map<String, BlockProperty> properties;

    public MinecraftBlock(Coords coordinates, String name, BlockProperty... properties) {
        this.coordinates = coordinates;
        String[] splitName = name.split(":",2);
        this.namespace = splitName.length == 2 ? splitName[0] : "minecraft";
        this.name = splitName.length == 2 ? splitName[1] : name;
        if (properties == null || properties.length == 0) this.properties = null;
        else {
            this.properties = new HashMap<>();
            for (BlockProperty p : properties)
                this.properties.put(p.name(), p);
        }
    }

    public Coords coordinates() {
        return coordinates;
    }

    public String namespace() {
        return namespace;
    }

    public String name() {
        return name;
    }

    public String fullName() {
        return namespace + ":" + name;
    }

    public BlockProperty getProperty(String propertyName) {
        return properties != null ? properties.get(propertyName) : null;
    }

    public static Coords blockToChunkCoords(Coords blockCoordinates) {
        return new Coords(blockCoordinates.x >> 4, blockCoordinates.y >> 4, blockCoordinates.z >> 4);
    }

}
