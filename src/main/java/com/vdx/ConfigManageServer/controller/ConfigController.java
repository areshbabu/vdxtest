package com.vdx.ConfigManageServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vdx.ConfigManageServer.bean.ConfigValues;
import com.vdx.ConfigManageServer.service.ConfigService;

@RestController
@RequestMapping(value = "/conf-server")
public class ConfigController {

	@Autowired
	ConfigService service;

	@GetMapping(path = "/getConfig", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ConfigValues> retreiveConfigValues(@RequestParam String appName, @RequestParam String key) {
		String value = service.getConfigValue(appName, key);
		ConfigValues res = new ConfigValues(key, value);
		return ResponseEntity.ok(res);
	}
}
