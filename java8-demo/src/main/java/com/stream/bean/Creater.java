package com.stream.bean;

import java.util.Arrays;
import java.util.List;

/**
 * @author zyting
 * @sinne 2020-06-28
 * 创建菜单
 */
public class Creater {

   public static List<Dish> createDish(){

       List<Dish> menu = Arrays.asList(
               new Dish("pork",false,800,Dish.Type.MEAT),
               new Dish("beef",false,700,Dish.Type.MEAT),
               new Dish("chicken",false,400,Dish.Type.MEAT),
               new Dish("french fries",true,530,Dish.Type.OTHER),
               new Dish("rice",true,350,Dish.Type.OTHER),
               new Dish("season fruit",true,120,Dish.Type.OTHER),
               new Dish("pizza",true,550,Dish.Type.OTHER),
               new Dish("prawns",false,300,Dish.Type.FISH),
               new Dish("salmon",false,450,Dish.Type.FISH));

       return menu;
   }

}
