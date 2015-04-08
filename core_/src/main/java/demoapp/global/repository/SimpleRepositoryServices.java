package demoapp.global.repository;

import java.util.List;

public interface SimpleRepositoryServices<T> {

    T addItem(String index, String type, T item);

    List<T> getAll(String index, String type);

}
