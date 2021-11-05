package com.vdx.ConfigManageServer.poller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdx.ConfigManageServer.service.FileProcessorServiceImpl;

@Service
public class GitPoller implements Poller {

	@Autowired
	FileProcessorServiceImpl fileProcessorService;

	@Override
	public void poll(Map<String, Long> fileReaderTimeStamps) {
		String envTarget = System.getProperty("envTarget");
		//String link = "https://github.com/areshbabu/vdxtest/tree/master/git-repo-config/app1/config-" + envTarget
		String link = "https://raw.githubusercontent.com/areshbabu/vdxtest/master/git-repo-config/app1/config-" + envTarget+ ".properties";
		String link2 = "https://raw.githubusercontent.com/areshbabu/vdxtest/master/git-repo-config/app2/config-" + envTarget+ ".properties";
		File destination = new File(
				System.getProperty("user.dir") + "\\git-config\\app1\\config-" + envTarget + ".properties");
		File destination2 = new File(
				System.getProperty("user.dir") + "\\git-config\\app2\\config-" + envTarget + ".properties");
		try {
			FileUtils.copyURLToFile(new URL(link), destination);
			FileUtils.copyURLToFile(new URL(link2), destination2);
			File[] gitFiles = new File[2];
			gitFiles[0] = destination;
			gitFiles[1] = destination2;
			
			for (File file2 : gitFiles) {
				String dirPath = file2.getAbsolutePath().substring(0, file2.getAbsolutePath().lastIndexOf("\\"));
				String appName = dirPath.substring(dirPath.lastIndexOf("\\") + 1);
				long lastModified = fileReaderTimeStamps.getOrDefault(appName + file2.getName(), 0L);
				DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
				System.out.println("GitPoller Key:"+(appName + file2.getName())+" LastModified ..."+sdf.format(lastModified));
				//System.out.println("GitPoller Key:"+(appName + file2.getName())+" LastModified ..."+lastModified);
				if (lastModified == 0 || lastModified < file2.lastModified()) {
					fileProcessorService.process(file2);
				}
				fileReaderTimeStamps.put(appName + file2.getName(), file2.lastModified());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
