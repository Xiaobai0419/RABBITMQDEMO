package com.mc.util;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test3 {
    public static void demo1() {
        int size = 50000;
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(Integer.toString(i));
        }

        System.out.println("size = " + size);
        long start2 = System.currentTimeMillis();
        Map<Integer, String> map3 = Maps.newHashMapWithExpectedSize(size);
//        for (int i = 0; i < size; i++) {
//            String string = list.get(i);
//            map3.put(string.hashCode(), string);
//        }
        for (String string : list) {
            map3.put(string.hashCode(), string);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("iterator --- t = " + (end2 - start2));

        long start = System.currentTimeMillis();
        Map<Integer, String> map = list.stream().collect(
                Collectors.toMap(s -> s.hashCode(), s -> s));
        long end = System.currentTimeMillis();
        System.out.println("stream --- t = " + (end - start));

        long start1 = System.currentTimeMillis();
        //lists为value的列表
        Map<Integer, String> map1 = Maps.uniqueIndex(list,
                new com.google.common.base.Function<String, Integer>() {
                    @Override
                    public Integer apply(String value) {//返回值是key，参数是value

                        return value.hashCode();
                    }
                });

      //  Map<Integer, String> g2 = Maps.uniqueIndex(list,(String value)-> value.hashCode());
        long end1 = System.currentTimeMillis();
        System.out.println("guava --- t = " + (end1 - start1));
    }

    public static void main(String[] args) {
        demo1();
    }
}
