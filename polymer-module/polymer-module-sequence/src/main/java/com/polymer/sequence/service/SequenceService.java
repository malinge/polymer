package com.polymer.sequence.service;

/**
 * 发号器表Service接口
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-03-27
 */
public interface SequenceService {
    long nextValue(String name, int step, long stepStart);
}
