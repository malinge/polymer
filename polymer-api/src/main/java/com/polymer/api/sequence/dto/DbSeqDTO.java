package com.polymer.api.sequence.dto;

import java.io.Serializable;

public class DbSeqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //"业务标识"
    private String bizName = "pay";

    /**
     * 获取range步长[可选：默认：1000]
     */
    private int step = 1;

    /**
     * 序列号分配起始值[可选：默认：0]
     */
    private long stepStart = 0;

    private String repair = "06d";

    private String pureDatetimeFormat = "yyyyMMddHHmmss";

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getStepStart() {
        return stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getPureDatetimeFormat() {
        return pureDatetimeFormat;
    }

    public void setPureDatetimeFormat(String pureDatetimeFormat) {
        this.pureDatetimeFormat = pureDatetimeFormat;
    }
}
