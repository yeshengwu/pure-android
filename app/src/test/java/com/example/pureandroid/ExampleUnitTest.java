package com.example.pureandroid;

import org.junit.Test;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        NavigableMap<Float, String> areas = new TreeMap<>(new Comparator<Float>() {
            @Override public int compare(Float o1, Float o2) {
                return Float.compare(o2, o1); // reverse order, from high to low.
            }
        });

        areas.put(1.0f,"1.0");
        areas.put(2.0f,"2");

        System.out.println("eeeee======");
        System.out.println(areas); // {2.0=2, 1.0=1.0}

        assertEquals(4, 2 + 2);
    }
}