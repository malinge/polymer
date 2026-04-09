package com.polymer.monitor.model;

import com.polymer.monitor.utils.ArityUtil;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Mem Info
 *
 * @author Pure tea
 */
public class Mem {

    /**
     * 内存总数(G)
     */
    private double total;

    /**
     * 已用内存(G)
     */
    private double used;

    /**
     * 剩余内存(G)
     */
    private double free;

    /**
     * 内存使用率
     */
    private double usage;

    public Mem() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        this.setTotal(memory.getTotal());
        this.setUsed(memory.getTotal() - memory.getAvailable());
        this.setFree(memory.getAvailable());
        this.setUsage(ArityUtil.round(ArityUtil.div(this.getUsed(), this.getTotal(), 4) * 100, 2));
        /*GlobalMemory globalMemory = OshiUtil.getMemory();
        this.setTotal(ArityUtil.div(globalMemory.getTotal(), 1024 * 1024 * 1024, 2));
        this.setFree(ArityUtil.div(globalMemory.getAvailable(), 1024 * 1024 * 1024, 2));
        this.setUsed(ArityUtil.sub(this.getTotal(), this.getFree()));
        this.setUsage(ArityUtil.round(ArityUtil.div(this.getUsed(), this.getTotal(), 4) * 100, 2));*/
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }
}
