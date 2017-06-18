package com.itfsm.util.reflect;

import java.util.Date;

import org.junit.Before;

import link.smartwall.util.reflect.ReflectUtils;

public class ReflectUtilsTest {
    private ReflectObject fo;
    private Date date = new Date();

    @Before
    public void setUp() throws Exception {
        fo = new ReflectObject();

        ReflectUtils.setValue(fo, "str1", "STR1");
        ReflectUtils.setValue(fo, "int2", 2L);
        ReflectUtils.setValue(fo, "long3", 3);
        ReflectUtils.setValue(fo, "date4", date);
        ReflectUtils.setValue(fo, "float5", 5.0);
        ReflectUtils.setValue(fo, "double6", 6.0);
        ReflectUtils.setValue(fo, "boolean7", true);
    }

   /* @Test
    public void test() {
        assertEquals("STR1", fo.getStr1());
        assertEquals(2, fo.getInt2());
        assertEquals(3, fo.getLong3());
        assertEquals(date, fo.getDate4());
        assertEquals(5.0, fo.getFloat5(), 0.0000001);
        assertEquals(6.0, fo.getDouble6(), 0.0000001);
        assertEquals(true, fo.isBoolean7());
    }*/

}
