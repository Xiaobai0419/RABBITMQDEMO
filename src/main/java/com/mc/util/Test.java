package com.mc.util;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class A {
    private String roomNo;

    private String roomStatus;

    private Integer smTime;

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Integer getSmTime() {
        return smTime;
    }

    public void setSmTime(Integer smTime) {
        this.smTime = smTime;
    }

    public A(String roomNo, Integer smTime) {
        this.roomNo = roomNo;
        this.smTime = smTime;
        this.roomStatus = "1";
    }



}

public class Test {
    public static void main(String[] args) {
        List<A> list = new ArrayList();
        String no = ((int) (Math.random() * 100) + 1) + "";
        list.add(new A(no, (int) (Math.random() * 100) + 1));
        list.add(new A(no, (int) (Math.random() * 100) + 1));
        list.add(new A(no, (int) (Math.random() * 100) + 1));
        list.add(new A(((int) (Math.random() * 100) + 1) + "", (int) (Math.random() * 100) + 1));
        list.add(new A(((int) (Math.random() * 100) + 1) + "", (int) (Math.random() * 100) + 1));
        list.add(new A(((int) (Math.random() * 100) + 1) + "", (int) (Math.random() * 100) + 1));
        list.add(new A(((int) (Math.random() * 100) + 1) + "", (int) (Math.random() * 100) + 1));
        list.add(new A(((int) (Math.random() * 100) + 1) + "", (int) (Math.random() * 100) + 1));
//        Map<Long, Map<Integer, Long>> tradeNumMap = list.stream().
//                collect(Collectors.groupingBy(OrdersDO::getAppId,
//                        Collectors.groupingBy(OrdersDO::getStatus,
//                                Collectors.counting())));
        Map<String, Long> collect = list.stream().
                collect(Collectors.groupingBy(A::getRoomNo,
                        Collectors.summingLong(A::getSmTime)));
        System.err.println("");
    }
    }