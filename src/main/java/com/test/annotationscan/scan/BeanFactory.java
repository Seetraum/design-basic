package com.test.annotationscan.scan;

import com.test.annotationscan.ExaminerProvider;
import com.test.annotationscan.Handle;
import com.test.annotationscan.Event;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanFactory implements ExaminerProvider {

    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);
    /**
     *对象容器
     * */
    private static final Map<String,Class> beanClassContainer = new HashMap<>();
    private static final Map<String,Object> beanContainer = new HashMap<>();

    /**
     * 初始化指定包下的所有@Handle注解标记的类
     *
     * @param packageNames 初始化包路径集合
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void init(String... packageNames){
        if (packageNames == null && packageNames.length <= 0){
            return;
        }
        logger.info("Scan packages = {}",packageNames);
        for (String packageName : packageNames) {
            Reflections reflections = new Reflections(packageName);
            Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Handle.class);
            classSet.parallelStream().forEach(c -> {
                logger.info("Scan class = {}",c);
                try {
                    Object bean = c.getDeclaredConstructor().newInstance();
                    Handle handle = c.getAnnotation(Handle.class);
                    beanContainer.put(handle.value(), bean);
                    beanClassContainer.put(handle.value(),c);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /**
     * 根据注解名获取实例
     *
     * @param beanName 注解的名称
     * @return 对应实例
     */
    public static Object getBean(String beanName){
        return beanContainer.get(beanName);
    }

    /**
     * 根据注解名获取指定类型的实例
     *
     * @param beanName bean名称，注解指定的value值
     * @param beanClass bean类型
     * @return 指定类型的实例
     */
    public static <T> T getBean(String beanName,Class<T> beanClass){
        return beanClass.cast(getBean(beanName));
    }

    @Override
    public Map<String,Event> examinerChain(String... conditions) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        if (conditions == null && conditions.length <= 0){
            return null;
        }
        Map<String,Event> eventMap = new HashMap<>();
        for (String condition : conditions){
            Event event = eventMap.get(condition);
            if (event == null) {
                Class c = beanClassContainer.get(condition);
                logger.info("Get classes is = {}",c);
                Object o = beanContainer.get(condition);
                if (c != null && o != null) {
                    if (o instanceof Event){
                        event = (Event) c.getDeclaredConstructor().newInstance();
                        eventMap.put(condition, event);
                    }
                }
            }
        }
        return eventMap;
    }
}
