package com.vdx.ConfigManageServer.poller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.vdx.ConfigManageServer.service.FileProcessorServiceImpl;

@Service
public class FileSystemPoller implements Poller {

	@Autowired
	FileProcessorServiceImpl fileProcessorService;

	@Override
	public void poll(Map<String, Long> fileReaderTimeStamps) {
		ClassLoader cl = this.getClass().getClassLoader();

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		try {
			String envTarget = System.getProperty("envTarget");
			Resource resources[] = resolver.getResources("classpath:config-repo/*/*" + envTarget + ".properties");
			for (Resource resource : resources) {
				File file = resource.getFile();
				System.out.println("FileName:" + file.getAbsolutePath());
				String dirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));
				String appName = dirPath.substring(dirPath.lastIndexOf("\\") + 1);
				long lastModified = fileReaderTimeStamps.getOrDefault(appName + file.getName(), 0L);
				DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
				System.out.println("FileSystem Key:"+(appName + file.getName())+" LastModified ..."+sdf.format(lastModified));
				if (lastModified == 0 || lastModified < file.lastModified()) {
					fileProcessorService.process(file);
				}
				fileReaderTimeStamps.put(appName + file.getName(), file.lastModified());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
