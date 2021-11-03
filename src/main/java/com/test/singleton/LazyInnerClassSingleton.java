package com.test.singleton;

public class LazyInnerClassSingleton {
    //默认先初始化内部类
    private LazyInnerClassSingleton(){
        if(LazyHolder.LAZY != null){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    //static 保证使用单例的空间共享，保证这个方法不会被重写、重载
    public static final LazyInnerClassSingleton getInstance(){
        //返回结果前一定会先加载内部类
        return LazyHolder.LAZY;
    }
    //默认不加载
    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
