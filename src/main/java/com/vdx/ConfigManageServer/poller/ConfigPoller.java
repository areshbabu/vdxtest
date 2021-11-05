package com.vdx.ConfigManageServer.poller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.vdx.ConfigManageServer.service.FileProcessorService;

@Configuration
@EnableScheduling
public class ConfigPoller {
	
	@Autowired
	FileProcessorService fileProcessorService;
	
	Map<String, Long> fileReaderTimeStamps = new HashMap<String, Long>();
	
	@Scheduled(fixedRate = 1000)
	public void fixedRateSch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Fixed Rate scheduler:: " + strDate);
		ClassLoader cl = this.getClass().getClassLoader();
		
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		try {
			String envTarget = System.getProperty("envTarget");
			Resource resources[] = resolver.getResources("classpath:config-repo/*/*" + envTarget + ".properties");
			for (Resource resource : resources) {
				File file = resource.getFile();
				System.out.println("FileName:" + file.getAbsolutePath());
				long lastModified = fileReaderTimeStamps.getOrDefault(file.getAbsolutePath(), 0L);
				//String appName = file.getAbsolutePath().sub
				if (lastModified == 0 || lastModified < file.lastModified()) {
					fileProcessorService.process(file);
				}
				fileReaderTimeStamps.put(file.getAbsolutePath(), file.lastModified());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
