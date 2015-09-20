/** 
 * SchoolKeeper  
 * com.schoolkeeper.log  
 */
package com.schoolkeeper.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * @Title: XLogger.java
 * @Package com.schoolkeeper.log
 * @Description: 日志记录工具
 * @author 王年明
 * @date 2015年1月1日 下午4:38:40
 * @version V1.0
 */
public class XLogger {
	private static LoggerContext logContext = null;
	
	private Logger logger = LoggerFactory.getLogger(XLogger.class);
	
	private Logger MainLogger = null;
	
	private Logger ErrLogger = null;

	private Logger DbLogger = null;
	
	@org.junit.Test
	public void doIt() {
		init();
		if (null == MainLogger) {
			MainLogger = LoggerFactory.getLogger("mainlog");
		}
		if (null == ErrLogger) {
			ErrLogger = LoggerFactory.getLogger("errlog");
		}
		if(null == DbLogger){
			DbLogger = LoggerFactory.getLogger("dblog");
		}
		//StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		MainLogger.info("===================");
		logger.debug("Hello {}", "debug message");
		logger.debug("doing my job");
		while (true) {
			try {
				Thread.sleep(1000);
				DbLogger.info("{}","测试...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void init() {
		if (logContext == null) {
			logContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(logContext);
			logContext.reset();
			try {
				configurator.doConfigure("conf/logback.xml");
			} catch (JoranException e) {
				e.printStackTrace();
			}
			StatusPrinter.printInCaseOfErrorsOrWarnings(logContext);
		}
	}
}
