package com.ts.main.dorm.init;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service("myInitTaskService")
public class MyInitTaskService {

	private static final Log log = LogFactory.getLog(MyInitTaskService.class);

	@PostConstruct
	public void initTask() {
		String currentPath = MyInitTaskService.class.getClassLoader().getResource("").getPath();
		String tempPath = currentPath.replace("WEB-INF/classes/", "resource/download/temp/");
		deleteFile(new File(tempPath));
	}

	/**
	 * 删除文件夹下的所有文件
	 * 
	 * @param oldPath
	 */
	public void deleteFile(File filePath) {
		if (filePath.isDirectory()) {
//			System.out.println(filePath + "是文件夹--");
			File[] files = filePath.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		} else {
			filePath.delete();
		}
	}
}
