package edu.ccrm.service;

import java.util.List;
import java.util.function.Predicate;

public interface Searchable<T> {
    List<T> search(Predicate<T> criteria);
    
    default List<T> searchByField(String fieldValue, java.util.function.Function<T, String> fieldExtractor) {
        return search(entity -> fieldExtractor.apply(entity).toLowerCase()
                                            .contains(fieldValue.toLowerCase()));
    }
}
