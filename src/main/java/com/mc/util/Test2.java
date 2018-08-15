package com.mc.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Test2 {

    public static void main(String[] args) {
        Consumer c1 = System.out::print;
        Consumer c2 = n -> System.out.println(n+"2");

        c1.andThen(c2).accept("c1");

        Function<Integer,Integer> f1 = s -> s++;
        Function<Integer,Integer> f2 = s -> s--;

        System.out.println(f1.compose(f2).apply(3));

        System.out.println(f1.andThen(f2).apply(1));

        Predicate<String> p1 = o -> o.equals("test");

    }
}
