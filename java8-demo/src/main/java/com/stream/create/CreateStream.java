package com.stream.create;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author zyting
 * @sinne 2020-06-28
 * 创建流测试类
 */
public class CreateStream {

    public static void main(String[] args) {

        //1、由值创建流,转化成大写并遍历
//        Stream<String> stream = Stream.of("Java8","Lambdas","In");
//        stream.map(String::toUpperCase).forEach(System.out::println);

        //2、由数组创建流,梁中方式计算数组元素的和
//        int[] numbers = {2,3,5,7,11,13};
//        int sum = Arrays.stream(numbers).sum();
//        int sum = Arrays.stream(numbers).reduce(0,(a,b)->a+b);
//        System.out.println(sum);  // 41

        //3.1 由函数生成流：创建无限流,返回去前10个的偶数
//        Stream.iterate(0,n->n+2).limit(10)
//                .forEach(System.out::println);
        //生成斐波那契元祖
//        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1],t[0]+t[1]})
//                .limit(20)
//                .forEach(t -> System.out.println("("+t[0]+","+t[1]+")"));
//        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1],t[0]+t[1]})
//                .limit(20)
//                .map(t -> t[0])
//                .forEach(System.out::println);

        //3.2 generate生成流
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }

}
