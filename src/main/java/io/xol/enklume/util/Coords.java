package io.xol.enklume.util;

import java.util.Objects;

public class Coords {

    public final int x;
    public final int y;
    public final int z;

    public Coords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x && y == coords.y && z == coords.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
