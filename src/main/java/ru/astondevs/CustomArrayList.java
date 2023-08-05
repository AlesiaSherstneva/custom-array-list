package ru.astondevs;

import java.util.Comparator;

/**
 * Custom version of the class {@code ArrayList}.
 * <p>
 * This class is created to work with an array of objects.
 * The type of object must implement the Comparable interface.
 *
 * @param <E> the type of elements in this list
 * @author Alesia Sherstneva
 */
public class CustomArrayList<E extends Comparable<E>> implements Comparator<E> {
    /**
     * The array of objects which contains elements of the CustomArrayList.
     */
    private Object[] list;

    /**
     * The size of the CustomArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * Constructor that creates empty CustomArrayList with default capacity of 10.
     */
    public CustomArrayList() {
        list = new Object[10];
    }

    /**
     * Constructor that creates empty CustomArrayList with initial capacity.
     *
     * @param capacity the initial capacity of new CustomArrayList
     * @throws IllegalArgumentException if the initial capacity is less than 0
     */
    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should be a positive number or zero");
        } else if (capacity == 0) {
            capacity = 10;
        }
        list = new Object[capacity];
    }

    /**
     * Appends new element to the end of the list.
     *
     * @param element new element to add
     */
    public void add(E element) {
        if (size == list.length) {
            increaseCapacity();
        }
        list[size++] = element;
    }

    /**
     * Inserts the new element at the given position in the list.
     * The element at that position and all following elements are moving to the right by one position.
     * If the capacity becomes less than the total number of elements, it is increase by 10.
     *
     * @param index   index where the new element will be inserted
     * @param element new element to insert
     * @throws IllegalArgumentException  if the index is less than zero
     * @throws IndexOutOfBoundsException if the index is greater than size of the list
     */
    public void add(int index, E element) {
        if (index < 0 || index > size - 1) {
            throwWrongIndexException(index);
        }
        if (size == list.length) {
            increaseCapacity();
        }
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }
        list[index] = element;
        size++;
    }

    private void increaseCapacity() {
        Object[] newList = new Object[list.length + 10];
        System.arraycopy(list, 0, newList, 0, size);
        list = newList;
    }

    /**
     * Replaces the element in the given position with the new element.
     *
     * @param index   index where the new element will be replaced
     * @param element new element to replace
     * @throws IllegalArgumentException  if the index is less than zero
     * @throws IndexOutOfBoundsException if the index is greater than size of the list
     */
    public void set(int index, E element) {
        if (index < 0 || index > size - 1) {
            throwWrongIndexException(index);
        }
        list[index] = element;
    }

    /**
     * Removes the element at the given position in the list.
     * All following elements are moving to the left by one position.
     *
     * @param index index where an element will be removed
     * @throws IllegalArgumentException  if the index is less than zero
     * @throws IndexOutOfBoundsException if the index is greater than size of the list
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throwWrongIndexException(index);
        }
        for (int i = index; i < size; i++) {
            if (i != size - 1) {
                list[i] = list[i + 1];
            } else {
                list[i] = null;
            }
        }
        size--;
    }

    /**
     * Removes from the list the first element that is equal to given element.
     * If the list doesn't contain a given element, the method does nothing.
     *
     * @param element element to be removed
     */
    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(list[i])) {
                remove(i);
                return;
            }
        }
    }

    /**
     * Returns the element at the given position in the list.
     *
     * @param index index of the element to be returned
     * @return the element at the given position
     * @throws IllegalArgumentException  if the index is less than zero
     * @throws IndexOutOfBoundsException if the index is greater than size of the list
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throwWrongIndexException(index);
        }
        return (E) list[index];
    }

    private void throwWrongIndexException(int wrongIndex) {
        if (wrongIndex < 0) {
            throw new IllegalArgumentException("Index should be a positive number or zero");
        }
        if (wrongIndex > size - 1) {
            throw new IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", wrongIndex, size));
        }
    }

    /**
     * Returns the size of the list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the given element is in the list.
     *
     * @param element element to be found in the list
     * @return {@code true} if the list contains given element
     */
    public boolean contains(E element) {
        if (size == 0) {
            return false;
        }
        for (Object e : list) {
            if ((e != null && e.equals(element)) || (e == null && element == null)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compare(E object1, E object2) {
        return object1.compareTo(object2);
    }

    /**
     * Sorts the elements in the list from lesser to greater using the "quick sort" algorithm.
     */
    public void sortList() {
        sortList(this, 0, this.size() - 1);
    }

    private void sortList(CustomArrayList<E> list, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return;
        }

        E pivot = list.get(highIndex);
        int leftPointer = lowIndex, rightPointer = highIndex;

        while (leftPointer < rightPointer) {
            while (list.compare(list.get(leftPointer), pivot) <= 0 && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (list.compare(list.get(rightPointer), pivot) >= 0 && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(list, leftPointer, rightPointer);
        }

        swap(list, leftPointer, highIndex);

        sortList(list, lowIndex, leftPointer - 1);
        sortList(list, leftPointer + 1, highIndex);
    }

    private void swap(CustomArrayList<E> list, int index1, int index2) {
        E temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder().append("[");
        for (int i = 0; i < size; i++) {
            result.append(list[i]);
            if (i != size - 1) {
                result.append(", ");
            } else {
                result.append("]");
            }
        }
        return result.toString();
    }
}