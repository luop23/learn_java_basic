package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/3/24 14:46
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS: CompareAndSwap,比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(10);
        System.out.println(num.compareAndSet(10, 2020) + "\t 当前值是：" + num.get());
        System.out.println(num.compareAndSet(10, 1949) + "\t 当前值是：" + num.get());

        People p1 = new People("lisi", 24);
        AtomicReference<People> people = new AtomicReference<>(p1);
        System.out.println(people.compareAndSet(p1, new People("Cheery", 18))+"\t 当前people是："+people.get());
        System.out.println(people.compareAndSet(p1, new People("James", 2))+"\t 当前people是："+people.get());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class People {
    private String name;

    private Integer age;
}
