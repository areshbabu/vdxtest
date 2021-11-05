package com.vdx.ConfigManageServer.service;

import java.io.File;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdx.ConfigManageServer.redis.entity.ConfigEntity;
import com.vdx.ConfigManageServer.redis.respository.ConfigDao;

@Service
public class FileProcessorServiceImpl implements FileProcessor {

	@Autowired
	ConfigDao confDao;

	public void process(File file) {

		try {
			String dirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));
			String appName = dirPath.substring(dirPath.lastIndexOf("\\") + 1);
			System.out.println("appName.." + appName);

			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				String lines[] = input.nextLine().split("=");
				ConfigEntity entity = new ConfigEntity(appName + lines[0], lines[0], lines[1]);
				confDao.saveOrUpdate(entity);
			}
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
