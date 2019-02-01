package com.dong.watchlog;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

/**
 * log controller
 *
 * @author: dongzhihua
 * @time: 2019/2/1 12:02:20
 */
@Controller
@RequestMapping("log")
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    String logDir = "/export/log/%s/%s_detail.log";
    String appDir = "/home/wy/www/%s/logs/catalina.out";

    @RequestMapping("{system}")
    public String logView(@PathVariable("system") String system, Model model) {
        model.addAttribute("system", system);
        model.addAttribute("offset", getLogFile(system).length());
        return "log.html";
    }

    @RequestMapping("{system}.json")
    @ResponseBody
    public Object logJson(@PathVariable("system") String system, Long offset) throws IOException {
        LogBean logBean = new LogBean();
        File log = getLogFile(system);
        if (offset == null) {
            offset = log.length();
        }
        RandomAccessFile fr = null;
        try {
            fr = new RandomAccessFile(log, "r");
            fr.seek(offset);
            List<String> lines = new LinkedList<String>();
            logBean.setLogs(lines);
            String line;

            while ((line = fr.readLine()) != null) {
                line = new String(line.getBytes("ISO-8859-1"),"utf-8");
                lines.add(line);
                logger.info("logJson line: {}", line);
            }
        } finally {
            IOUtils.closeQuietly(fr);
        }
        logBean.setOffset(log.length());
        return logBean;
    }

    File getLogFile(String system) {
        File logFile = new File(String.format(logDir, system, system));
        if (logFile.exists()) {
            return logFile;
        }
        File appLogsFile = new File(String.format(appDir, system));
        Assert.isTrue(appLogsFile.exists(), String.format("系统找不到日志文件：%s，%s", logFile.getAbsolutePath(), appLogsFile.getAbsolutePath()));
        return appLogsFile;
    }
}
