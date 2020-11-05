package com.stream.test;

import com.stream.bean.Trader;
import com.stream.bean.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zyting
 * @sinne 2020-06-29
 * 交易测试类
 */
public class TransactionTest {

    private final static Trader raoul = new Trader("Troul", "Cambridge");
    private final static Trader mario = new Trader("Mario", "Milan");
    private final static Trader alan = new Trader("Alan", "Cambridge");
    private final static Trader brian = new Trader("Brian", "Cambridge");

    private final static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian,2011,300),
            new Transaction(raoul,2012,1000),
            new Transaction(raoul,2011,400),
            new Transaction(mario,2012,710),
            new Transaction(mario,2012,700),
            new Transaction(alan,2012,950)
    );

    /**
     * 找出2011年的所有交易并按交易额排序（从低到高）
     * filter 传递一个谓词来选择2011年的交易
     * 按照交易额进行排序
     * 将生成的stream中的左右元素收集到一个list中
     */
    @Test
    public void test1(){
        List<Transaction> transactions = TransactionTest.transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactions);
    }

    /**
     * 交易员都在哪些不同的城市工作过
     * 获取每一笔交易的交易员城市
     * 去重
     */
    @Test
    public void test2(){
        List<String> citys = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(citys);
    }

    /**
     * 交易员都在哪些不同的城市工作过
     * 可以通过 toSet 去重
     */
    @Test
    public void test3(){
        Set<String> citys = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println(citys);
    }

    /**
     * 查找所有来自于剑桥的交易员，并按姓名进行排序
     * 映射成 trader对象
     * 选择剑桥的交易员
     * 去重
     * 按姓名排序
     */
    @Test
    public void test4(){
        List<Trader> traderList = TransactionTest.transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traderList);
    }

    /**
     * 查找所有来自于剑桥的交易员，并按姓名进行排序
     * 查询出来自剑桥的交易员的交易
     * 映射成 trader对象
     * 去重
     * 根据姓名排序
     */
    @Test
    public void test5(){
        List<Trader> traderList = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traderList);
    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序
     * 此解决方案效率不高（所有字符串都被反复连接，每次迭代的时候都要建立一个新的string对象）
     * 提取所有交易员的姓名，生成一个string构成的stream
     * 去重
     * 对姓名按字母进行排序
     * 逐个拼接每个名字，得到一个将所有名字连接起来的string
     */
    @Test
    public void test6(){
        String str = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(str);   // AlanBrianMarioTroul
    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序,使用joining（起内部会使用到 StringBuilder）
     * 映射成 trader对象
     * 映射成string类型的stream流对象
     * 去重
     * 排序
     * 通过joining拼接，里面可以设置拼接符号
     */
    @Test
    public void  test7(){
        String str = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println(str);   // Alan,Brian,Mario,Troul
    }

    /**
     * 有没有交易员是在米兰工作
     */
    @Test
    public void test8(){
        boolean milanBased = transactions.stream()
                .allMatch(transaction ->
                        transaction.getTrader().getCity().equals("Milan"));
        if(milanBased){
            System.out.println("有在米兰工作的交易员");
        }else {
            System.out.println("没有在米兰工作的交易员");
        }
    }

    /**
     * 这样写为什么不可以？
     * 得到这样的结果： [Trader: Mario in Milan] 但是 mario并不是 milan城市的
     */
    @Test
    public void test9(){
        List<Trader> milan = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(milan);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额信息
     */
    @Test
    public void test10(){
        transactions.stream()
                .filter(transaction ->
                        "Cambridge".equals(transaction.getTrader().getCity()))
                .forEach(System.out::println);
    }

    /**
     * 只打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test11(){
        transactions.stream()
                .filter(transaction ->
                        "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * 所有交易中，最高的交易额是多少
     * 映射成value
     * 计算出最大值
     */
    @Test
    public void test12(){
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(maxValue.get());
    }

    /**
     * 找到交易额最小的交易
     */
    @Test
    public void test13(){
        Optional<Transaction> minTransaction = transactions.stream()
                .reduce((t1, t2) ->
                        t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println(minTransaction.get());
    }

    /**
     * 通过 min函数找到交易额最小的交易
     */
    @Test
    public void test14(){
        Optional<Transaction> min = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        System.out.println(min);
    }

    /**
     * 找出最大的交易额
     * 映射出交易额
     * 排序
     */
    @Test
    public void test15(){
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce((t1, t2) -> t1 > t2 ? t1 : t2);
        System.out.println(maxValue.get());
    }

}
