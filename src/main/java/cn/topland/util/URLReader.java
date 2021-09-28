package cn.topland.util;

public interface URLReader<T> {

    T read(final String url);
}