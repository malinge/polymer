package com.polymer.monitor.model;

import com.polymer.framework.common.utils.IpUtils;

import java.util.Properties;

/**
 * System Info
 *
 * @author Pure tea
 */
public class Sys {

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    /**
     * 系统版本
     */
    private String osVersion;


    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    public Sys() {
        Properties props = System.getProperties();
        this.setComputerName(IpUtils.getHostName());
        this.setComputerIp(IpUtils.getHostIp());
        this.setOsName(props.getProperty("os.name"));
        this.setOsArch(props.getProperty("os.arch"));
        this.setOsVersion(props.getProperty("os.version"));
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerIp() {
        return computerIp;
    }

    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }
}
