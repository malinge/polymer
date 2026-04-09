package com.polymer.framework.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Collection 工具类
 *
 * @author polymer
 */
public class CollectionUtils {

    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
        if (isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 判断集合是否为空 - 替换 CollectionUtil.isEmpty
     * @param collection 要检查的集合
     * @return 如果集合为null或空返回true，否则返回false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 过滤集合
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        for (T item : collection) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 转换集合
     */
    public static <T, R> List<R> map(Collection<T> collection, java.util.function.Function<T, R> mapper) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }

        List<R> result = new ArrayList<>();
        for (T item : collection) {
            result.add(mapper.apply(item));
        }
        return result;
    }

    /**
     * 判断集合是否包含某个元素
     */
    public static <T> boolean contains(Collection<T> collection, T element) {
        return isNotEmpty(collection) && collection.contains(element);
    }

    /**
     * 将集合转换为字符串
     */
    public static String join(Collection<?> collection, String separator) {
        if (isEmpty(collection)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> iterator = collection.iterator();

        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

}
