package com.test.build;

import com.test.build.NyPizza.Size;
import com.test.build.Pizza.Topping;

public class BuildTest {

  public static void main(String[] args) {
    NutritionFacts nutritionFacts = new NutritionFacts.Builder(240,10)
        .calories(1).carbohydrate(10).fat(0).sodium(5).build();
    NyPizza pizza = new NyPizza.Builder(Size.SMALL)
        .addTopping(Topping.SAUSAGE)
        .addTopping(Topping.ONION)
        .build();
    Calzone calzone = new Calzone.Builder().addTopping(Topping.HAM).sauceInside().build();
  }

}
