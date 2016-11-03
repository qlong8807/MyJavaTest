package logback.service;

import logback.dao.UDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UService {
	Logger logger = LoggerFactory.getLogger(UService.class);
	public void testPrint(){
		logger.debug("service");
		logger.info("service");
		logger.error("service");
		UDao dao = new UDao();
		dao.testPrint();
	}
}
