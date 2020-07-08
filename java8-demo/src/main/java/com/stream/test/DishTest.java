package com.stream.test;

import com.stream.bean.CaloricLevel;
import com.stream.bean.Dish;
import org.junit.Test;

import javax.xml.transform.Source;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    /**
     * 计算出菜肴的热量总和
     * 有一个暗含的装箱成本。每个Integer都必须拆箱成一个原始类型，在进行求和。
     */
    @Test
    public void test11(){
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(calories);
    }

    /**
     * java8引入了原始类型特化刘接口：IntStream、DoubleStream、LongStream，分别将
     * 流中的元素特化为int、long、double，从而避免了暗箱的装箱成本。
     * 求菜单中所有菜肴的热量和
     * 返回一个 IntStream
     * mapToInt会从每道菜中提取热量（用一个Integer表示），并返回一个IntStream（而不是一个Stream<Integer>）
     * 然后就可以调用IntStream接口中定义的sum方法进行求和。
     * 如果流式空的，sum默认返回0。IntStream还支持其他的方便方法，max、min、average等。
     */
    @Test
    public void test12(){
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println(calories);
    }

    /**
     * 转换回对象流
     */
    @Test
    public void test13(){
        //将stream转换为数值流
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        //将数值流转换为Stream
        Stream<Integer> stream = intStream.boxed();
    }

    /**
     * 默认值OptionalInt
     * 如果没有最大值的话，可以显示处理 OptionalInt定义一个默认值
     */
    @Test
    public void test14(){
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        // 如果没有最大值，显示提供一个默认最大值
        int max = maxCalories.orElse(1);
        System.out.println(max);
    }

    /**
     * 找出菜单中热量最高的菜
     * Comparator来根据所含热量对菜肴进行比较，
     * Collectors.maxBy和Collectors.minBy来计算流中的最大或最小值
     */
    @Test
    public void test15(){
        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> maxCalorieDish = menu.stream()
                .collect(Collectors.maxBy(dishComparator));
        System.out.println(maxCalorieDish);
    }

    /**
     * 求出菜单列表的总热量
     * Collectors.summingInt 可以接受一个把对象映射为求和所需int的函数，并返回一个收集器；
     * 该收集器在传递给普通的collect方法后即执行我们需要的汇总操作。
     * Collectors.summingLong和Collectors.summingDouble 方法的作用完全一样
     */
    @Test
    public void test16(){
        int totalCalories = menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
    }

    /**
     * 求出所有菜肴的热量平均数
     * averagingLong和averagingDouble 也可以计算数值的平均数
     */
    @Test
    public void test17(){
        Double avgCalories = menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(avgCalories);
    }

    /**
     * summarizingInt可以对菜单进行分析，打印出菜肴个数，总热量，最值、平均值
     * 结果输出：IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
     */
    @Test
    public void test18(){
        IntSummaryStatistics statistics = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(statistics);
    }

    /**
     * 连接字符串，将所有的菜肴名称拼接起来
     */
    @Test
    public void test19(){
        String nameStr = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", "));
        System.out.println(nameStr);
    }

    /**
     * 利用收集器进行分组
     */
    @Test
    public void test20(){
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);
    }

    /**
     * 根据热量进行分类
     */
    @Test
    public void test21(){
        Map<CaloricLevel, List<Dish>> collect = menu.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                    else
                        return CaloricLevel.FAT;
                }));
        System.out.println(collect);
    }

    /**
     * 多级分组
     * 可以使用一个由双参数版本的Collectors.groupongBy工厂方法创建的收集器，
     * 它除了普通的分类函数之外，还可以接受collector类型的第二个参数。那么要进行二级分组的话，
     * 我们可以把一个内层groupingBy传递给外层groupingBy，并定义一个为流中项目分类的二级标准
     */
    @Test
    public void test22(){
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> {
                            if (dish.getCalories() <= 400)
                                return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        }))
                );
        System.out.println(collect);
    }

    /**
     * 按类型统计每类菜有多少个
     */
    @Test
    public void test23(){
        Map<Dish.Type, Long> typeLongMap = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(typeLongMap);
    }

    /**
     * 求菜肴类型中各类型的热量最大值
     */
    @Test
    public void test24(){
        Map<Dish.Type, Optional<Dish>> maxGroupType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.maxBy(Comparator.comparing(Dish::getCalories)))
                );
        System.out.println(maxGroupType);
    }

    /**
     * 求各分类菜肴的热量和
     */
    @Test
    public void test25(){
        Map<Dish.Type, Integer> caloriesSumByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.summingInt(Dish::getCalories)));
        System.out.println(caloriesSumByType);
    }

    /**
     * 分区 ：是分组的特殊情况，由一个谓词（返回一个布尔值的函数）作为分类函数，
     * 分区函数返回一个布尔值，意味着得到的分组map的键烈性是Boolean，因此它最多可以分为两组：true和false
     * 将菜肴分成素食和非素食
     */
    @Test
    public void test26(){
        Map<Boolean, List<Dish>> partitionMenu = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(partitionMenu);
    }

    /**
     * 获取所有的素食菜肴
     */
    @Test
    public void test27(){
        Set<Dish> dishes = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toSet());
        System.out.println(dishes);
    }

    /**
     * 将菜肴根据热量等级进行分类
     * 调用dish类中自定义的分类方法作为参数传递给 groupingBy方法
     */
    @Test
    public void test28(){
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(Dish::getCaloriecLevel));
        System.out.println(dishesByCaloricLevel);
    }

    /**
     * 找到热量大于300卡路里的菜肴
     */
    @Test
    public void test29(){
        List<String> dishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(dishNames);
    }

}
