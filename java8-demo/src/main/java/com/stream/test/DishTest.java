package com.stream.test;

import com.stream.bean.Dish;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zyting
 * @sinne 2020-06-28
 */
public class DishTest {

    private final static List<Dish> menu = Arrays.asList(
            new Dish("pork",false,800,Dish.Type.MEAT),
            new Dish("beef",false,700,Dish.Type.MEAT),
            new Dish("chicken",false,400,Dish.Type.MEAT),
            new Dish("french fries",true,530,Dish.Type.OTHER),
            new Dish("rice",true,350,Dish.Type.OTHER),
            new Dish("season fruit",true,120,Dish.Type.OTHER),
            new Dish("pizza",true,550,Dish.Type.OTHER),
            new Dish("prawns",false,300,Dish.Type.FISH),
            new Dish("salmon",false,450,Dish.Type.FISH));

    /**
     * 筛选出<400卡路里的菜肴
     * 按 calories 进行排序
     * 提取菜肴的名称
     * 将所有名称保存在list中
     */
    @Test
    public void test1(){
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .collect(Collectors.toList());
        System.out.println(dishes);
    }

    /**
     * 筛选出高能量的菜肴  > 300
     * 获取菜名
     * 值选择前面3个
     * 将结果保存在另一个list中
     */
    @Test
    public void test2(){
        List<String> dishNames = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(dishNames);
    }

    /**
     * 获取所有菜肴名称并去重
     */
    @Test
    public void test3(){
        Set<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toSet());
        System.out.println(dishNames);
    }

    /**
     * stream has already been operated upon or closed  流已被操作或关闭
     * 流只能遍历一次。遍历完后，就说这个流已经被消费掉了，否则会抛出异常
     */
    @Test
    public void test4(){
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> stream = title.stream();
        stream.forEach(System.out::println);
        stream.forEach(System.out::println);  //stream has already been operated upon or closed
    }

    /**
     * 筛选素食
     */
    @Test
    public void test5(){
        List<Dish> dishes = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        System.out.println(dishes);
    }

    /**
     * 查找和匹配
     * anyMatch: 流中是否有一个元素能匹配给定的谓词
     * anyMatch方法返回一个boolean，因此是一个终端操作
     */
    @Test
    public void test6(){
        boolean b = menu.stream().anyMatch(Dish::isVegetarian);
        if(b){
            System.out.println("The menu is (somewhat) vegetarian friendly!");
        }
    }

    /**
     * allMatch 流中元素是否都能匹配给定的谓词
     * 查看菜品是否有利健康（即所有菜的热量都低于1000卡路里）
     */
    @Test
    public void test7(){
        boolean isHealthy = menu.stream()
                .allMatch(d -> d.getCalories() < 1000);
        if(isHealthy){
            System.out.println("this is a healthy menu");
        }
    }

    /**
     * noneMatch：与allMatch相对，可以确保流中没有任何元素与给定的谓词匹配
     * anyMatch、allMatch、noneMatch这三个操作都用到了断路，即 &&和 ||
     * 断路求值：有些操作不需要处理整个流就能得到结果。例如，假设你需要对一个用and连起来的大布尔
     * 表达式求值。不管表达式有多长，你只需找到一个表达式为false，就可以推断整个表达式将返回false，
     * 所以不用计算整个表达式。这就是短路。
     * 对于流而言，某些操作（allMatch、anyMatch、noneMatch、findFirst和findAny）不用处理整个
     * 流就能得到结果。只要找到一个元素，就可以有结果了，同样，limit也是一个短路操作；它只需要
     * 创建一个给定大小的流，而用不着处理流中所有的元素。在碰到无限大小的流的时候，这种操作就有用了
     */
    @Test
    public void test8(){
        boolean isHealthy = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);
        if(isHealthy){
            System.out.println("this is a healthy menu");
        }
    }

    /**
     * findAny方法将返回当前流中的任意元素。它可以与其他流操作结合使用。
     * Optional<T>类是一个容器类，代表一个值存在或不存在。
     *  isPresent()将在Optional包含值的时候返回true，否则返回false
     *  ifPresent(Consumer<T> block)会在值存在的时候执行给定的代码块。
     */
    @Test
    public void test9(){
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        System.out.println(dish);
    }

    /**
     * 统计个数
     * 总共有多少道菜
     */
    @Test
    public void test10(){
        long count = menu.stream().count();
        System.out.println(count);
    }



}
