package com.example.mvc.scala;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * PO的工具类
 */
@Slf4j
public abstract class POUtils {
    private static Map<Class, Method> idMethodCache = new ConcurrentHashMap<>();
    private static Map<Class, Map<String, String>> columnCache = new ConcurrentHashMap<>();

    /**
     * MQ的失败队列
     */
    private static Queue<MQBean> mqSendFailQueue = new LinkedBlockingQueue<MQBean>(102400);

    /**
     * 高性能获取对象的ID的value
     *
     * @param po
     * @return
     */
    public static Serializable getTableIdValue(Object po) {
        Method method = idMethodCache.get(po.getClass());

        if (method != null) {
            return getIdValue(po, method);
        }

        TableName declaredAnnotation = po.getClass().getDeclaredAnnotation(TableName.class);

        if (declaredAnnotation == null) {
            throw new RuntimeException("must be TableName PO:" + po.getClass());
        }

        Serializable tableIdValue = getTableIdValueInner(po);

        if (tableIdValue != null) {
            return tableIdValue;
        }


        // 如果没有TableId，那么获取id的get方法
        Serializable idValue = getDefaultIdValue(po);

        if (idValue == null) {
            throw new RuntimeException("PO miss id get method:" + po.getClass());
        }

        return idValue;
    }

    /**
     * 获取数据库的字段名称
     *
     * @param claz
     * @param fieldName
     * @return
     */
    public static String getColumnName(Class claz, String fieldName) {

        Map<String, String> cache = columnCache.get(claz);

        // 走缓存
        if (cache != null) {
            String columnName = cache.get(fieldName);
            if (columnName != null) {
                return columnName;
            }
        }

        String columnName = getColumnNameInner(claz, fieldName);

        putColumnCache(claz, fieldName, columnName);

        return columnName;
    }


    private static String getColumnNameInner(Class claz, String fieldName) {
        try {
            Field declaredField = claz.getDeclaredField(fieldName);
            TableField tableField = declaredField.getDeclaredAnnotation(TableField.class);
            if (tableField != null) {
                String value = tableField.value();
                if (value == null || value.length() == 0) {
                    return fieldName;
                }

                return value;
            }

            return fieldName;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static void putColumnCache(Class claz, String fieldName, String columnName) {
        synchronized (columnCache) {
            Map<String, String> cache = columnCache.get(claz);
            if (cache == null) {
                cache = new HashMap<>();
            }

            cache.put(fieldName, columnName);

            columnCache.put(claz, cache);
        }
    }

    private static Serializable getTableIdValueInner(Object po) {
        Field[] declaredFields = po.getClass().getDeclaredFields();

        Field tableIdField = null;

        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            TableId tableId = declaredField.getDeclaredAnnotation(TableId.class);
            if (tableId != null) {
                tableIdField = declaredField;
                break;
            }
        }

        //·找到了PO的ID
        if (tableIdField != null) {
            Method[] methods = po.getClass().getMethods();
            for (Method each : methods) {
                if (each.getName().equalsIgnoreCase("get" + tableIdField.getName())) {
                    idMethodCache.put(po.getClass(), each);
                    break;
                }
            }

            // 如果没有get方法说明不符合PO
            Serializable idValue = getIdValue(po);

            if (idValue == null) {
                throw new RuntimeException("PO miss id get method:" + po.getClass());
            }

            return idValue;
        }
        return null;
    }

    public static void clearAllCache() {
        idMethodCache.clear();
    }

    /**
     * 允许多累加载器的时候清空缓存
     *
     * @param claz
     */
    public static void clearCache(Class claz) {
        idMethodCache.remove(claz);
    }

    public static <T> Class<T> getQueryWrapperEntityClass(QueryWrapper<T> wrapper) {
        Type type = wrapper.getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<T>) types[0];
        }
        return null;
    }

    public static void log(String msg) {
        log.info(msg);
    }

    public static void log(String msg, Object... var2) {
        log.info(msg, var2);
    }

    public static void warn(String msg) {
        log.warn(msg);
    }

    public static void warn(String msg, Throwable tw) {
        log.warn(msg, tw);
    }

    public static void warn(String msg, Object... var2) {
        log.warn(msg, var2);
    }

    public static void cacheSendFailMQ(String topic, String key, String msg) {
        MQBean mqBean = new MQBean(topic, key, msg);

        // 插入队列，不阻塞，插入失败返回false
        boolean ret = POUtils.mqSendFailQueue.offer(mqBean);

        if (ret) {
            log.info("Add send fail mq to cache queue success:{}", mqBean);
        } else {
            log.error("Add send fail mq to cache queue fail:{}", mqBean);
        }
    }

    public static MQBean pollSendFailMQ() {
        return POUtils.mqSendFailQueue.poll();
    }

    private static Serializable getDefaultIdValue(Object po) {
        Method[] methods = po.getClass().getMethods();
        for (Method each : methods) {
            if (each.getName().equalsIgnoreCase("getId")) {
                idMethodCache.put(po.getClass(), each);
                break;
            }
        }

        return getIdValue(po);
    }

    private static Serializable getIdValue(Object po, Method method) {
        try {
            return (Serializable) method.invoke(po);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Serializable getIdValue(Object po) {
        Method method = idMethodCache.get(po.getClass());
        if (method != null) {
            return getIdValue(po, method);
        }

        return null;
    }

    @Data
    @AllArgsConstructor
    public static class MQBean {
        String topic;
        String key;
        String msg;
    }
}
