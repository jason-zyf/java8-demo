package com.stream.test;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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



}
