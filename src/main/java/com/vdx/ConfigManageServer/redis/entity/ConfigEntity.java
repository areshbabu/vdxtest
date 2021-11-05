package com.vdx.ConfigManageServer.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Config")
public class ConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String key;
	private String value;
	
	
}
