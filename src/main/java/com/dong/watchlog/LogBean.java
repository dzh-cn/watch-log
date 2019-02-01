package com.dong.watchlog;

import java.util.Collection;
import java.util.List;

/**
 * bean
 *
 * @author: dongzhihua
 * @time: 2019/2/1 12:10:49
 */
public class LogBean {
    private String system;
    private Long offset;
    private Collection<String> logs;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Collection<String> getLogs() {
        return logs;
    }

    public void setLogs(Collection<String> logs) {
        this.logs = logs;
    }
}
