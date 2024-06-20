package org.app;

import java.util.ArrayList;
import java.util.List;

public class MyArray {
    private List<Integer> list;

    public MyArray() {
        this.list = new ArrayList<>();
    }

    public List<Integer> getList() {
        return list;
    }

    public void addElement(int element) {
        list.add(element);
    }
}
