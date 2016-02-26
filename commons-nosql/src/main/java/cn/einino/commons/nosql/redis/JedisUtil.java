package cn.einino.commons.nosql.redis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.einino.commons.nosql.model.RedisPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private final Object lock = new Object();
	private final Map<String, RedisPoolConfig> configs;
	private volatile Map<String, JedisPool> pools = Collections.synchronizedMap(new HashMap<String, JedisPool>());

	public JedisUtil(Map<String, RedisPoolConfig> configs) {
		this.configs = configs;
	}

	public Jedis getJedis(String host, int port) {
		Jedis jedis = null;
		JedisPool pool = getJedisPool(host, port);
		if (pool != null) {
			jedis = pool.getResource();
		}
		return jedis;
	}

	public Jedis getJedis(String host, int port, int database) {
		Jedis jedis = getJedis(host, port);
		if (jedis != null) {
			jedis.select(database);
		}
		return jedis;
	}

	protected JedisPool getJedisPool(String host, int port) {
		String key = new StringBuilder(host).append(":").append(port).toString();
		JedisPool pool = pools.get(key);
		if (pool == null) {
			RedisPoolConfig config = configs.get(key);
			if (config != null) {
				synchronized (lock) {
					pool = pools.get(key);
					if (pool == null) {
						JedisPoolConfig poolConf = new JedisPoolConfig();
						poolConf.setMaxTotal(config.getMaxTotal());
						poolConf.setMaxIdle(config.getMaxIdle());
						poolConf.setMaxWaitMillis(config.getMaxWait());
						poolConf.setTestOnBorrow(config.isTestOnBorrow());
						poolConf.setTestOnReturn(config.isTestOnReturn());
						pool = new JedisPool(poolConf, config.getHost(), config.getPort(), config.getTimeout());
						pools.put(key, pool);
					}
				}
			}
		}
		return pool;
	}
}
