package com.vdx.ConfigManageServer.redis.respository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vdx.ConfigManageServer.redis.entity.ConfigEntity;

import java.util.List;

@Repository
public class ConfigDao {

	public static final String HASH_KEY = "Config";
	@Autowired
	private RedisTemplate template;

	@CachePut(key="#entity.id", value = "Config")
	public ConfigEntity saveOrUpdate(ConfigEntity entity) {
		template.opsForHash().put(HASH_KEY, entity.getId(), entity);
		return entity;
	}

	public List<ConfigEntity> findAll() {
		return template.opsForHash().values(HASH_KEY);
	}

	@Cacheable(key="#id", value = "Config")
	public ConfigEntity findConfigById(String id) {
		System.out.println("From the DB Call .......");
		Object obj =  template.opsForHash().get(HASH_KEY, id);
		return (ConfigEntity) obj;
	}

	public String deleteProduct(String id) {
		template.opsForHash().delete(HASH_KEY, id);
		return "config removed !!";
	}
}
