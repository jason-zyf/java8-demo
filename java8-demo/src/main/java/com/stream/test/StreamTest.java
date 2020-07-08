package com.stream.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zyting
 * @sinne 2020-06-28
 */
public class StreamTest {

    /**
     * 打印偶数，并去重
     */
    @Test
    public void test1(){
        List<Integer> nums = Arrays.asList(1,2,1,3,3,2,4);
        nums.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 筛选出奇数并跳过前面两个
     */
    @Test
    public void test2(){
        List<Integer> nums = Arrays.asList(1,2,3,1,3,2,4);
        List<Integer> collect = nums.stream()
                .filter(i -> i % 2 == 1)
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 打印出个字符串的长度
     */
    @Test
    public void test3(){
        List<String> words = Arrays.asList("Java8","Lambdas","In","Action");
        words.stream()
                .map(String::length)
                .forEach(System.out::println);
    }

    /**
     * 流的扁平化
     * 使用flatMap方法的效果是，各个数组并不是分别映射成一个流，而是映射成流的内容。
     * 所有使用map(Arrays::stream)时生成的单个流都被合并起来，即扁平化为一个流。
     * 将每个单词转换为尤其字母构成的数组
     * 将各个生成流扁平化为单个流
     * 去重
     * 保存在一个list中
     */
    @Test
    public void test4(){
        List<String> words = Arrays.asList("Hello","World");
        List<String> collect = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 给定一个数字列表，返回每个数的平方构成的列表
     * [1,2,3,4,5] --> [1,4,9,16,25]
     */
    @Test
    public void test5(){
        List<Integer> num = Arrays.asList(1,2,3,4,5);
        List<Integer> squares = num.stream()
                .map(i -> i * i)
                .collect(Collectors.toList());
        System.out.println(squares);
    }

    @Test
    public void test6(){
        List<Integer> num1 = Arrays.asList(1,2,3);
        List<Integer> num2 = Arrays.asList(3,4);
        List<int[]> collect = num1.stream()
                .flatMap(i -> num2.stream()
                        .map(j -> new int[]{i,j})
                ).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 找出第一个满足要求的值
     */
    @Test
    public void test7(){
        List<Integer> nums = Arrays.asList(1,3,5,4,2);
        Optional<Integer> firstNum = nums.stream()
                .filter(i -> i % 2 == 0)
                .findFirst();
        System.out.println(firstNum.get());
    }

    /**
     * 循环求和
     * reduce接受两个参数：
     * 一个初始值0
     * 一个BinaryOperator<T>来将两个元素结合起来产生一个新值，这里用的是lambda (a,b)->a+b
     */
    @Test
    public void test8(){
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        int sum = nums.stream()
                .reduce(0, (a, b) -> a + b);
        int product = nums.stream().reduce(1, (a,b) -> a*b);
        System.out.println("sum: "+sum);
        System.out.println("product: "+product);

        //使用Integer类中的静态方法sum来对两个数求和
        int sum2 = nums.stream().reduce(0, Integer::sum);
        System.out.println("sum2: "+sum2);
    }

    /**
     * 扩展：使用Integer类中的一个静态sum 方法来对两个数求和
     * 无初始化值： 不接受初始值，但是会返回一个Optional对象（考虑流中没有任何元素的情况）
     * 如果集合中没有元素会返回
     */
    @Test
    public void test9(){
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        //使用Integer类中的静态方法sum来对两个数求和
        int sum1 = nums.stream().reduce(0, Integer::sum);
        System.out.println("sum1: "+sum1);

        Optional<Integer> sum = nums.stream().reduce((a, b) -> a + b);
        System.out.println("sum: "+sum);
    }

    /**
     * 求最值
     */
    @Test
    public void test10(){
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        Optional<Integer> max = nums.stream().reduce(Integer::max);

        Optional<Integer> min = nums.stream().reduce((a, b) -> a < b ? a : b);
        System.out.println("max: "+max);
        System.out.println("min: "+min);
    }

    /**
     * [1,100] 内的偶数个数
     * range和rangeClosed。这两个方法都是第一个参数接受起始值，第二个参数接受结束值。
     * 但range是不包含结束值的，而rangeClosed则包含结束值。
     */
    @Test
    public void test11(){
        IntStream intStream = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(intStream.count());
    }

    /**
     * 打印出[1,100]内所有的偶数
     */
    @Test
    public void test12(){
        IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);
    }

    /**
     * 生成a,b在[1,100]内的勾股数
     */
    @Test
    public void test13(){
        Stream<int[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b ->
                                        new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );
        stream.forEach(t ->
                System.out.println(t[0]+", "+t[1]+", "+t[2]));
    }

    @Test
    public void test14(){


    }


}
