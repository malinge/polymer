package com.polymer.quartz.service.impl;

import com.polymer.quartz.service.base.AbstractScheduleJob;
import org.quartz.DisallowConcurrentExecution;

/**
 * 禁止并发
 *
 * @author polymer
 */
@DisallowConcurrentExecution
public class ScheduleDisallowConcurrentExecutionImpl extends AbstractScheduleJob {

}
