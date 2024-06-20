package org.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayTest {
    @Test
    @DisplayName("Kiểm tra mảng")
    void testAddElement() {
        MyArray myList = new MyArray();
        myList.addElement(1);
        // Kiểm tra xem danh sách ban đầu có rỗng không
        assertTrue(myList.getList().isEmpty());

        // Thêm phần tử vào danh sách
        myList.addElement(1);

        // Kiểm tra xem danh sách có đúng một phần tử
        assertEquals(1, myList.getList().size());

        // Kiểm tra xem phần tử đó có phải là 1 không
        assertEquals(Integer.valueOf(1), myList.getList().get(0));

        // Thêm phần tử khác vào danh sách
        myList.addElement(2);

        // Kiểm tra xem danh sách có đúng hai phần tử
        assertEquals(2, myList.getList().size());

        // Kiểm tra xem phần tử cuối có phải là 2 không
        assertEquals(Integer.valueOf(2), myList.getList().get(1));
    }
}