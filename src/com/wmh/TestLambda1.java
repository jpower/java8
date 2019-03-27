package com.wmh;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by 周大侠
 * 2019-03-26 11:02
 */
public class TestLambda1 {
    Trader wmh1 = new Trader("wmh1", "jdz");
    Trader wmh2 = new Trader("wmh2", "jdz1");
    Trader wmh3 = new Trader("wmh3", "jdz2");
    Trader wmh4 = new Trader("wmh4", "jdz2");

    List<Transaction> transactions = Arrays.asList(
                new Transaction(wmh1, 2011, 300),
                new Transaction(wmh2, 2012, 400),
                new Transaction(wmh4, 2011, 310),
                new Transaction(wmh3, 2012, 500),
                new Transaction(wmh1, 2013, 420),
                new Transaction(wmh4, 2012, 540)

        );

    /**
     * 找出2011年发生的所有交易 按交易额排序
     */
    @Test
    public void fun1() {
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out :: println);
    }

    /**
     * 交易员在那些不同的城市中工作过
     */
    @Test
    public void fun2() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out :: println);

    }

    /**
     * 查找所有来自于剑桥的交易员，按姓名排序
     */
    @Test
    public void fun3() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("jdz2"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out :: println);
    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序
     */
    @Test
    public void fun4() {
        Set<String> set = transactions.stream()
                .map(t -> t.getTrader().getName())
                .collect(Collectors.toSet());
        for (String s : set) {
            System.out.println(s);
        }

    }

    /**
     * 有没有交易员在jdz2工作
     */
    @Test
    public void fun5() {
        System.out.println(transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("jdz2")));
    }

    /**
     * 打印生活在jdz2交易员的所有交易额
     */
    @Test
    public void fun6() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("jdz2"))
                .map(Transaction::getValue)
                .forEach(System.out :: println);
    }

    /**
     * 最高交易额
     */
    @Test
    public void fun7() {
        System.out.println(transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo).get());
    }

    /**
     * 交易额中最小的交易
     */
    @Test
    public void fun8() {
        System.out.println(transactions.stream()
                .min(Comparator.comparing(Transaction::getValue)).get());
    }

    @Test
    public void fun9() {
        IntStream intStream = transactions.stream().mapToInt(Transaction::getValue);
    }

    /**
     * 无限流求斐波拉契数列前20项
     */
    @Test
    public void fun10() {
        Stream.iterate(new int[]{0,1}, array -> new int[]{array[1],array[0] + array[1]})
                .limit(20)
                .map(array -> array[0])
                .forEach(System.out :: println);
    }

    @Test
    public void fun11() {
        System.out.println(transactions.stream().collect(Collectors.counting()));
    }
    public static String getTraderName(Transaction tra) throws IllegalArgumentException {
        if (tra != null) {
            Trader trader = tra.getTrader();
            if (trader != null) {
                String name = trader.getName();
                if (name != null) {
                    return name.toUpperCase();
                }
            }
        }
        throw new IllegalArgumentException("The value of param comp isn't available.");
    }
    @Test
    public void fun12() {
        getTraderName1(new Transaction());
    }

    public static String getTraderName1(Transaction tra) throws IllegalArgumentException {
        return Optional.ofNullable(tra)
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .map(String::toUpperCase)
                .orElseThrow(() -> new RuntimeException("参数错误"));

    }

}
