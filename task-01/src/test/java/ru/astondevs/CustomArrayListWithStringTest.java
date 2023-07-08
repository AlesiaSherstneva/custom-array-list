package ru.astondevs;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
public class CustomArrayListWithStringTest {
    CustomArrayList<String> stringList;
    String newElement;

    static Random random;

    @BeforeAll
    static void beforeAll() {
        random = new Random();
    }

    @BeforeEach
    void setUp() {
        stringList = new CustomArrayList<>();
        stringList.add("Zero");
        stringList.add("One");
        stringList.add("Two");
        stringList.add("Three");
        stringList.add("Four");

        newElement = "Five";
    }

    @Test
    public void createListWithNegativeCapacityTest() {
        assertThrows(IllegalArgumentException.class, () -> stringList = new CustomArrayList<>(-1),
                "Capacity should be a positive number");
    }

    @Test
    public void addElementTest() {
        // given
        assertEquals(5, stringList.size());
        // when
        stringList.add(newElement);
        // then
        assertEquals(6, stringList.size());
        assertEquals(newElement, stringList.get(stringList.size() - 1));
    }

    @Test
    public void addElementByIndexTest() {
        // given
        int rightIndex = random.nextInt(stringList.size() - 1);
        assertNotEquals(newElement, stringList.get(rightIndex));
        // when
        stringList.add(rightIndex, newElement);
        // then
        assertEquals(newElement, stringList.get(rightIndex));

        // given
        int wrongIndex = stringList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> stringList.add(wrongIndex, newElement),
                String.format("Index %d out of bounds for length %d", wrongIndex, stringList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> stringList.add(negativeIndex, newElement),
                "Index should be a positive number or zero");
    }

    @Test
    public void setElementByIndexTest() {
        // given
        int rightIndex = random.nextInt(stringList.size() - 1);
        assertNotEquals(newElement, stringList.get(rightIndex));
        // when
        stringList.set(rightIndex, newElement);
        // then
        assertEquals(newElement, stringList.get(rightIndex));

        // given
        int wrongIndex = stringList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> stringList.set(wrongIndex, newElement),
                String.format("Index %d out of bounds for length %d", wrongIndex, stringList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> stringList.set(negativeIndex, newElement),
                "Index should be a positive number or zero");
    }

    @Test
    public void removeElementByIndexTest() {
        // given
        int firstIndex = 0;
        String element = stringList.get(firstIndex);
        assertEquals("Zero", element);
        assertEquals(5, stringList.size());
        // when
        stringList.remove(firstIndex);
        // then
        assertNotEquals("Zero", stringList.get(firstIndex));
        assertEquals(4, stringList.size());

        // given
        int lastIndex = stringList.size() - 1;
        assertEquals("Four", stringList.get(lastIndex));
        // when
        stringList.remove(lastIndex);
        // then
        assertThrows(IndexOutOfBoundsException.class, () -> stringList.get(lastIndex),
                String.format("Index %d out of bounds for length %d", lastIndex, stringList.size()));
        assertEquals(3, stringList.size());

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> stringList.remove(negativeIndex),
                "Index should be a positive number or zero");
    }

    @Test
    public void removeElementByValueTest() {
        // given
        String element = "Three";
        assertTrue(stringList.contains(element));
        assertEquals(5, stringList.size());
        // when
        stringList.remove(element);
        // then
        assertFalse(stringList.contains(element));
        assertEquals(4, stringList.size());
    }

    @Test
    public void getElementByIndexTest() {
        // given
        int rightIndex = 4;
        String element = "Four";
        // when
        String receivedElement = stringList.get(rightIndex);
        // then
        assertEquals(element, receivedElement);

        // given
        int wrongIndex = stringList.size();
        // when, then
        assertThrows(IndexOutOfBoundsException.class, () -> stringList.get(wrongIndex),
                String.format("Index %d out of bounds for length %d", wrongIndex, stringList.size()));

        // given
        int negativeIndex = random.nextInt(100) * (-1);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> stringList.get(negativeIndex),
                "Index should be a positive number or zero");
    }

    @Test
    public void sizeOfListTest() {
        // given
        assertEquals(5, stringList.size());
        // when
        for (int i = 0; i < 5; i++) {
            stringList.add("Test string");
        }
        // then
        assertEquals(10, stringList.size());
    }

    @Test
    public void containsElementTest() {
        // given
        String elementInList = stringList.get(random.nextInt(stringList.size() - 1));
        String elementNotInList = newElement;
        // when, then
        assertTrue(stringList.contains(elementInList));
        assertFalse(stringList.contains(elementNotInList));
    }

    @Test
    public void comparisonTest() {
        // given
        String firstElement = "Another string";
        String secondElement = "Test string", thirdElement = "Test string";
        // when, then
        assertTrue(stringList.compare(firstElement, secondElement) < 1);
        assertTrue(stringList.compare(thirdElement, firstElement) >= 1);
        assertEquals(0, stringList.compare(secondElement, thirdElement));
    }

    @Test
    public void quickSortWithStringTest() {
        // given, when
        stringList.sortList();
        // then
        for (int i = 0; i < stringList.size() - 1; i++) {
            String first = stringList.get(i), second = stringList.get(i + 1);
            assertTrue(stringList.compare(first, second) <= 0);
        }
    }
}