package demoapp.global.repository.es;

import demoapp.global.repository.SimpleRepositoryServices;

import java.util.List;

public class ESSimpleRepositoryServicesImpl<T> implements SimpleRepositoryServices<T> {

    @Override
    public T addItem(String index, String type, T item) {
        return null;
    }

    @Override
    public List<T> getAll(String index, String type) {
        return null;
    }
}
