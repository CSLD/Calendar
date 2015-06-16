package net.balhar.calendar.persistence;

import java.util.Collection;

/**
 * Interface explaining DSL for storing and retrieving data from persistent store. It defines language necessary to use
 * standard crud operations.
 */
public interface Adapter<T> {
    /**
     * It stores given item in persistent store implementing this interface.
     *
     * @param toStore Item to store. It mustn't be null.
     */
    void store(T toStore);

    /**
     * It stores collection of items in persistent store.
     *
     * @param toStore ResourceCollection to store. It mustn't be null and must contain at least one item.
     */
    void store(Collection<T> toStore);

    /**
     * Returns collection of all items in the store. Conceptually the data should be in memory and only on load you
     * retrieve all data.
     *
     * @return ResourceCollection of items. If there are no items of given type in store, return empty collection.
     */
    Collection<T> retrieve();

    /**
     * Removes passed in item from persistent store. If Item is null throw runtime exception.
     *
     * @param toDelete Item to remove from persistent store.
     */
    void remove(T toDelete);

    /**
     * Removes all items passed in from the collection of the items. If the collection is null or empty throw runtime
     * exception
     *
     * @param toDelete ResourceCollection of items to be removed from persistent store.
     */
    void remove(Collection<T> toDelete);

    /**
     * Class, which represents item for which this store works. It isn't best solution, but I was unable to make
     * reflection
     * based solution run.
     *
     * @param entityClass Class over which this store operates.
     */
    void setClass(Class<T> entityClass);
}
