package com.vdx.ConfigManageServer.poller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ConfigPoller {

	Map<String, Long> fileReaderTimeStamps = new HashMap<String, Long>();

	@Autowired
	FileSystemPoller fileSystemPoller;
	
	@Autowired
	GitPoller gitPoller;

	@Scheduled(fixedRate = 1000)
	public void fixedRateSch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Fixed Rate scheduler:: " + strDate);

		fileSystemPoller.poll(fileReaderTimeStamps);
		gitPoller.poll(fileReaderTimeStamps);

	}
}
