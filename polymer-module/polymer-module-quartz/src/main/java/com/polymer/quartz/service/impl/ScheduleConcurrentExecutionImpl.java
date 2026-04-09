package com.polymer.quartz.service.impl;

import com.polymer.quartz.service.base.AbstractScheduleJob;

/**
 * 允许并发（不会等待上一次任务执行完毕，只要时间到就会执行）
 *
 * @author polymer
 */
public class ScheduleConcurrentExecutionImpl extends AbstractScheduleJob {

}
