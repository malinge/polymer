package com.polymer.framework.common.utils;

import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.pojo.SysDict;

import java.util.List;

/**
 * 字典工具类
 *
 * @author polymer
 */
public class DictUtils {
    private static final RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<SysDict.DictData> getDictCache(String key) {
        Object cacheObj = redisCache.hGet(getCacheName(), getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDict.DictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.containsAny(dictValue, separator)) {
            for (SysDict.DictData dict : datas) {
                for (String value : dictValue.split(separator)) {
                    if (value.equals(dict.getDictValue())) {
                        propertyString.append(dict.getDictLabel()).append(separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDict.DictData dict : datas) {
                if (dictValue.equals(dict.getDictValue())) {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDict.DictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.containsAny(dictLabel, separator)) {
            for (SysDict.DictData dict : datas) {
                for (String label : dictLabel.split(separator)) {
                    if (label.equals(dict.getDictLabel())) {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDict.DictData dict : datas) {
                if (dictLabel.equals(dict.getDictLabel())) {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型获取字典所有标签
     *
     * @param dictType 字典类型
     * @return 字典值
     */
    public static String getDictLabels(String dictType) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDict.DictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas)) {
            return StringUtils.EMPTY;
        }
        for (SysDict.DictData dict : datas) {
            propertyString.append(dict.getDictLabel()).append(SEPARATOR);
        }
        return StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
    }

    /**
     * 获取cache name
     *
     * @return 缓存名
     */
    public static String getCacheName() {
        return CacheConstants.SYS_DICT_CACHE;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return CacheConstants.SYS_DICT_KEY + configKey;
    }
}
