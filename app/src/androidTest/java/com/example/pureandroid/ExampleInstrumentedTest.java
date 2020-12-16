package com.example.pureandroid;

import android.content.Context;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "Test";
    private static Context mContext;

    @BeforeClass
    public static void setup() {
        Log.d(TAG, "setup");

        // Context of the app under test.
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @AfterClass
    public static void tearDown() {
        Log.d(TAG, "tearDown");
        mContext = null;
    }

    @Before
    public void evanBefore() {
        Log.d(TAG, "evanBefore");
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Log.e("evan", "xxxx");
        assertEquals("com.example.pureandroid", mContext.getPackageName());


    }

    @Test
    public void testTopicConcurrentException(){
        // https://www.cnblogs.com/dolphin0520/p/3933551.html 源码分析，彻底根治这个问题
//        https://juejin.cn/post/6844903569095671816
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        Log.e("evan", "before" + test);
//        for (int i = 0; i < test.size(); i++) {
//            String item = test.get(i);
//            Log.e("evan","item = " + item + " size = "+test.size()+" i = "+i);
//            if (item.equals("3")) {
//                test.remove(item);
//                i--;
//            }
//            if (item.equals("2")) {
//                test.remove(item);
//                i--;
//            }
//        }
//        Log.e("evan", "after" + test);
        List<String> toRemove = new ArrayList<String>();
        for (String item: test) {
            Log.e("evan","item = " + item + " size = "+test.size());
            if (item.equals("1")) {
//                test.remove(item);
                toRemove.add(item);
            }
            if (item.equals("2")) {
//                test.remove(item);
                toRemove.add(item);
            }
        }
//        test.removeAll(toRemove);
//        Log.e("evan", "after" + test);

        Iterator<String> listIterator = test.iterator();
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            Log.e("evan", "iterator next = " + next);
            if (next.equals("2")){
                listIterator.remove();
            }
        }
        Log.e("evan", "listIterator after" + test);

        Map<String, String> iconMap = new HashMap<>();
        iconMap.put("1", "1");
        iconMap.put("2", "2");
        iconMap.put("3", "3");
        Log.e("evan", "before map " + iconMap);
        Set<Map.Entry<String, String>> entrySet = iconMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String iconBean = next.getValue();
            int index = iconBean.indexOf("1");
            if (index >= 0) {
//                updateIcon(iconBean, floatIconBeans.remove(index));
            } else {
                iterator.remove();
            }
        }
        Log.e("evan", "after map " + iconMap);
    }


}