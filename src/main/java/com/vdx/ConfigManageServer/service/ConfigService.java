package com.vdx.ConfigManageServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.vdx.ConfigManageServer.redis.entity.ConfigEntity;
import com.vdx.ConfigManageServer.redis.respository.ConfigDao;

@Service
public class ConfigService {
//	@Autowired
//	Environment env;

	@Autowired
	ConfigDao confDao;

	public String getConfigValue(String appName, String key) {
		List<ConfigEntity>  list = confDao.findAll();
		System.out.println("list" + list);
		return confDao.findConfigById(appName + key).getValue();
	}

}
