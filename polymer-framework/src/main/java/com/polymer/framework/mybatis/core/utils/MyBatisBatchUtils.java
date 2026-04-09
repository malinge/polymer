package com.polymer.framework.mybatis.core.utils;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * MyBatis 批处理工具类
 * 提供通用的批处理操作，支持插入、更新、删除等批量操作
 * 支持参与外部事务和独立事务两种模式
 * 增强版：返回批处理操作影响的行数
 */
@Component
public class MyBatisBatchUtils {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisBatchUtils.class);

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 执行批量操作（参与外部事务）
     * 此方法不会提交事务，由外部事务控制提交和回滚
     * @param mapperClass Mapper接口类
     * @param dataList 数据列表
     * @param operation 操作函数 (mapper, item) -> mapper.method(item)
     * @param <T> Mapper类型
     * @param <U> 数据类型
     * @return 影响的行数
     */
    public <T, U> int executeBatch(Class<T> mapperClass, List<U> dataList, BiConsumer<T, U> operation) {
        if (dataList == null || dataList.isEmpty()) {
            logger.info("批量操作数据列表为空，跳过执行");
            return 0;
        }

        int totalAffectedRows = 0;
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            T mapper = session.getMapper(mapperClass);
            for (int i = 0; i < dataList.size(); i++) {
                U item = dataList.get(i);
                operation.accept(mapper, item);

                // 每1000条刷新一次，防止内存溢出但不提交
                if (i % 1000 == 0 || i == dataList.size() - 1) {
                    List<?> results = session.flushStatements(); // 刷新但不提交
                    totalAffectedRows += calculateAffectedRows(results);
                }
            }
            logger.info("批量操作完成，数据量: {}，影响行数: {}，等待外部事务提交",
                    dataList.size(), totalAffectedRows);
        } catch (Exception e) {
            // 发生异常时回滚当前批处理会话
            session.rollback();
            logger.error("批量操作失败，已回滚当前批处理会话", e);
            throw new RuntimeException("批量操作失败: " + e.getMessage(), e);
        } finally {
            session.close();
        }

        return totalAffectedRows;
    }

    /**
     * 执行批量操作（带批次大小控制，参与外部事务）
     * @param mapperClass Mapper接口类
     * @param dataList 数据列表
     * @param operation 操作函数
     * @param batchSize 每批处理的大小
     * @param <T> Mapper类型
     * @param <U> 数据类型
     * @return 影响的行数
     */
    public <T, U> int executeBatch(Class<T> mapperClass, List<U> dataList, BiConsumer<T, U> operation, int batchSize) {
        if (dataList == null || dataList.isEmpty()) {
            logger.info("批量操作数据列表为空，跳过执行");
            return 0;
        }

        int totalAffectedRows = 0;
        // 分批处理
        for (int i = 0; i < dataList.size(); i += batchSize) {
            int end = Math.min(dataList.size(), i + batchSize);
            List<U> subList = dataList.subList(i, end);

            SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            try {
                T mapper = session.getMapper(mapperClass);
                for (U item : subList) {
                    operation.accept(mapper, item);
                }
                List<?> results = session.flushStatements(); // 刷新但不提交
                totalAffectedRows += calculateAffectedRows(results);
                logger.info("批量操作完成批次: {}-{}，影响行数: {}", i, end, calculateAffectedRows(results));
            } catch (Exception e) {
                session.rollback();
                logger.error("批量操作失败，批次: {}-{}, 已回滚", i, end, e);
                throw new RuntimeException("批量操作失败: " + e.getMessage(), e);
            } finally {
                session.close();
            }
        }

        return totalAffectedRows;
    }

    /**
     * 执行批量操作并返回结果（参与外部事务）
     * @param mapperClass Mapper接口类
     * @param dataList 数据列表
     * @param operation 操作函数 (mapper, item) -> result
     * @param <T> Mapper类型
     * @param <U> 数据类型
     * @param <R> 结果类型
     * @return 操作结果列表
     */
    public <T, U, R> List<R> executeBatchWithResult(Class<T> mapperClass, List<U> dataList, Function<T, Function<U, R>> operation) {
        if (dataList == null || dataList.isEmpty()) {
            logger.info("批量操作数据列表为空，跳过执行");
            return Collections.emptyList();
        }

        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            T mapper = session.getMapper(mapperClass);
            List<R> results = dataList.stream()
                    .map(item -> operation.apply(mapper).apply(item))
                    .collect(Collectors.toList());

            session.flushStatements(); // 刷新但不提交
            logger.info("批量操作完成，数据量: {}，等待外部事务提交", dataList.size());

            return results;
        } catch (Exception e) {
            session.rollback();
            logger.error("批量操作失败，已回滚当前批处理会话", e);
            throw new RuntimeException("批量操作失败: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * 在独立事务中执行批量操作
     * @param mapperClass Mapper接口类
     * @param dataList 数据列表
     * @param operation 操作函数
     * @param <T> Mapper类型
     * @param <U> 数据类型
     * @return 影响的行数
     */
    public <T, U> int executeBatchInNewTransaction(Class<T> mapperClass, List<U> dataList, BiConsumer<T, U> operation) {
        if (transactionManager == null) {
            throw new IllegalStateException("未配置事务管理器，无法使用独立事务模式");
        }

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setTimeout(300); // 5分钟超时

        TransactionStatus status = transactionManager.getTransaction(definition);
        int totalAffectedRows = 0;

        try {
            // 使用独立会话执行批量操作
            SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            try {
                T mapper = session.getMapper(mapperClass);
                for (int i = 0; i < dataList.size(); i++) {
                    U item = dataList.get(i);
                    operation.accept(mapper, item);

                    // 每1000条提交一次，防止内存溢出
                    if (i % 1000 == 0 || i == dataList.size() - 1) {
                        List<?> results = session.flushStatements();
                        totalAffectedRows += calculateAffectedRows(results);
                        session.commit();
                        session.clearCache(); // 清理缓存，防止内存溢出
                    }
                }
            } catch (Exception e) {
                session.rollback();
                throw e;
            } finally {
                session.close();
            }

            transactionManager.commit(status);
            logger.info("独立事务批量操作成功完成，数据量: {}，影响行数: {}", dataList.size(), totalAffectedRows);
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("独立事务批量操作失败，已回滚事务", e);
            throw new RuntimeException("独立事务批量操作失败: " + e.getMessage(), e);
        }

        return totalAffectedRows;
    }

    /**
     * 在独立事务中执行批量操作（带批次大小控制）
     * @param mapperClass Mapper接口类
     * @param dataList 数据列表
     * @param operation 操作函数
     * @param batchSize 每批处理的大小
     * @param <T> Mapper类型
     * @param <U> 数据类型
     * @return 影响的行数
     */
    public <T, U> int executeBatchInNewTransaction(Class<T> mapperClass, List<U> dataList,
                                                   BiConsumer<T, U> operation, int batchSize) {
        if (transactionManager == null) {
            throw new IllegalStateException("未配置事务管理器，无法使用独立事务模式");
        }

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setTimeout(300); // 5分钟超时

        TransactionStatus status = transactionManager.getTransaction(definition);
        int totalAffectedRows = 0;

        try {
            // 分批处理
            for (int i = 0; i < dataList.size(); i += batchSize) {
                int end = Math.min(dataList.size(), i + batchSize);
                List<U> subList = dataList.subList(i, end);

                SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
                try {
                    T mapper = session.getMapper(mapperClass);
                    for (U item : subList) {
                        operation.accept(mapper, item);
                    }
                    List<?> results = session.flushStatements();
                    totalAffectedRows += calculateAffectedRows(results);
                    session.commit();
                } catch (Exception e) {
                    session.rollback();
                    throw e;
                } finally {
                    session.close();
                }
            }

            transactionManager.commit(status);
            logger.info("独立事务批量操作成功完成，数据量: {}，影响行数: {}", dataList.size(), totalAffectedRows);
        } catch (Exception e) {
            transactionManager.rollback(status);
            logger.error("独立事务批量操作失败，已回滚事务", e);
            throw new RuntimeException("独立事务批量操作失败: " + e.getMessage(), e);
        }

        return totalAffectedRows;
    }

    /**
     * 计算批处理影响的行数
     * @param results 批处理结果
     * @return 影响的行数
     */
    private int calculateAffectedRows(List<?> results) {
        if (results == null || results.isEmpty()) {
            return 0;
        }

        int affectedRows = 0;
        for (Object result : results) {
            if (result instanceof Integer) {
                affectedRows += (Integer) result;
            } else if (result instanceof int[]) {
                for (int count : (int[]) result) {
                    affectedRows += count;
                }
            }
            // 可以添加其他结果类型的处理逻辑
        }

        return affectedRows;
    }
}