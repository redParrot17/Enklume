package io.xol.enklume.util;

public class BlockProperty<T> {
    private String name;
    private T value;
    public BlockProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }
    public String name() {
        return name;
    }
    public T value() {
        return value;
    }
}
