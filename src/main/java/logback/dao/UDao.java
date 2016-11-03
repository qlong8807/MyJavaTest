package logback.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDao {
	Logger logger = LoggerFactory.getLogger(UDao.class);
	public void testPrint(){
		logger.debug("UDao");
		logger.info("UDao");
		logger.error("UDao");
		File file = new File("E://tt.t");
		try {
			new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
