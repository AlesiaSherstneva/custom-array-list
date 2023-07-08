package ru.astondevs;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class CustomArrayListWithIntTest {
    CustomArrayList<Integer> intList;
    int newElement;

    static Random random;

    @BeforeAll
    static void beforeAll() {
        random = new Random();
    }

    @BeforeEach
    void setUp() {
        intList = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            intList.add(random.nextInt(1000));
        }

        newElement = random.nextInt(1000) + 1000;
    }

    @Test
    public void createListWithNegativeCapacityTest() {
        assertThrows(IllegalArgumentException.class, () -> intList = new CustomArrayList<>(-1),
                "Capacity should be a positive number");
    }

    @Test
    public void addElementTest() {
        // given
        assertEquals(10, intList.size());
        // when
        intList.add(newElement);
        // then
        assertEquals(11, intList.size());
        assertEquals(newElement, intList.get(intList.size() - 1));
    }

    @Test
    public void addElementByIndexTest() {
        // given
        int rightIndex = random.nextInt(intList.size() - 1);
        assertNotEquals(newElement, intList.get(rightIndex));
        // when
        intList.add(rightIndex, newElement);
        // then
        assertEquals(newElement, intList.get(rightIndex));

        // given
        int wrongIndex = intList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> intList.add(wrongIndex, newElement),
                String.format("Index %d out of bounds for length %d", wrongIndex, intList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> intList.add(negativeIndex, newElement),
                "Index should be a positive number or zero");
    }

    @Test
    public void setElementByIndexTest() {
        // given
        int rightIndex = random.nextInt(intList.size() - 1);
        assertNotEquals(newElement, intList.get(rightIndex));
        // when
        intList.set(rightIndex, newElement);
        // then
        assertEquals(newElement, intList.get(rightIndex));

        // given
        int wrongIndex = intList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> intList.set(wrongIndex, newElement),
                String.format("Index %d out of bounds for length %d", wrongIndex, intList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> intList.set(negativeIndex, newElement),
                "Index should be a positive number or zero");
    }

    @Test
    public void removeElementByIndexTest() {
        // given
        int firstIndex = 0;
        intList.set(firstIndex, newElement);
        assertEquals(newElement, intList.get(firstIndex));
        assertEquals(10, intList.size());
        // when
        intList.remove(firstIndex);
        // then
        assertNotEquals(newElement, intList.get(firstIndex));
        assertEquals(9, intList.size());

        // given
        int lastIndex = intList.size() - 1;
        intList.set(lastIndex, newElement);
        assertEquals(newElement, intList.get(lastIndex));
        // when
        intList.remove(lastIndex);
        // then
        assertThrows(IndexOutOfBoundsException.class, () -> intList.remove(lastIndex),
                String.format("Index %d out of bounds for length %d", lastIndex, intList.size()));
        assertEquals(8, intList.size());

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> intList.remove(negativeIndex),
                "Index should be a positive number or zero");
    }

    @Test
    public void removeElementByValueTest() {
        // given
        intList.add(newElement);
        assertTrue(intList.contains(newElement));
        assertEquals(11, intList.size());
        // when
        intList.remove((Integer) newElement);
        // then
        assertFalse(intList.contains(newElement));
        assertEquals(10, intList.size());
    }

    @Test
    public void getElementByIndexTest() {
        // given
        int rightIndex = random.nextInt(intList.size() - 1);
        intList.set(rightIndex, newElement);
        // when
        int receivedElement = intList.get(rightIndex);
        // then
        assertEquals(newElement, receivedElement);

        // given
        int wrongIndex = intList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> intList.get(wrongIndex),
                String.format("Index %d out of bounds for length %d", wrongIndex, intList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> intList.get(negativeIndex),
                "Index should be a positive number or zero");
    }

    @Test
    public void sizeOfListTest() {
        // given
        assertEquals(10, intList.size());
        // when
        for (int i = intList.size() - 1; i >= 5; i--) {
            intList.remove(i);
        }
        // then
        assertEquals(5, intList.size());
    }

    @Test
    public void containsElementTest() {
        // given
        int elementInList = intList.get(random.nextInt(intList.size() - 1));
        int elementNotInList = newElement;
        // when, then
        assertTrue(intList.contains(elementInList));
        assertFalse(intList.contains(elementNotInList));
    }

    @Test
    public void comparisonTest() {
        // given
        int firstElement = 123, secondElement = 123, thirdElement = 321;
        // when, then
        assertEquals(0, intList.compare(firstElement, secondElement));
        assertTrue(intList.compare(firstElement, thirdElement) < 1);
        assertTrue(intList.compare(thirdElement, secondElement) >= 1);
    }

    @Test
    public void quickSortWithIntTest() {
        // given, when
        intList.sortList();
        // then
        for (int i = 0; i < intList.size() - 1; i++) {
            int first = intList.get(i), second = intList.get(i + 1);
            assertTrue(intList.compare(first, second) <= 0);
        }
    }
}