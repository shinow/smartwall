package link.smartwall.cache.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisMgr {
	private static final Logger LOG = LoggerFactory.getLogger(RedisMgr.class.getName());

	private static JedisPool pool;
	private static String HOST;
	private static String PASSWORD;
	private static int PORT;
	private static int DATABASE;

	static {
		initParams();
	}

	private static void initParams() {
		// String confFile = System.getProperty("sfa.home")
		// + File.separator
		// + "conf"
		// + File.separator
		// + "redis"
		// + File.separator
		// + "redis.properties";

		// try (FileInputStream in = new FileInputStream(confFile)) {
		// Properties prop = new Properties();
		// prop.load(new FileInputStream(confFile));

		// HOST = prop.getProperty("HOST");
		// PASSWORD = prop.getProperty("PASSWORD");
		// PORT = Integer.parseInt(prop.getProperty("PORT"));
		// DATABASE = Integer.parseInt(prop.getProperty("DATABASE"));
		HOST = "121.199.44.59";
		PASSWORD = "iTek123456";
		PORT = 6379;
		DATABASE = 15;

		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	private static synchronized void initPool() {
		if (null == pool) {
			LOG.info("start redis client");
			JedisPoolConfig config = new JedisPoolConfig();
			config.setBlockWhenExhausted(false);
			config.setMaxWaitMillis(10 * 1000);
			config.setMaxIdle(8);
			config.setMaxTotal(16);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);

			pool = new JedisPool(config, HOST, PORT, 3000, PASSWORD);
		}
	}

	public static Jedis getJedis() {
		if (pool == null) {
			initPool();
		}

		int timeoutCount = 0;
		Jedis jedis = null;
		while (true) {
			try {
				if (null != pool) {
					jedis = pool.getResource();
					jedis.select(DATABASE);

					return jedis;
				}
			} catch (Exception e) {
				if (e instanceof JedisConnectionException) {
					timeoutCount++;
					LOG.warn("getJedis timeoutCount={}", timeoutCount);
					if (jedis != null) {
						jedis.close();
					}

					if (timeoutCount > 3) {
						break;
					}
				} else {
					throw new RuntimeException("GetJedis error:jedisInfo ... NumActive=" + pool.getNumActive()
							+ ", NumIdle=" + pool.getNumIdle() + ", NumWaiters=" + pool.getNumWaiters() + ", isClosed="
							+ pool.isClosed());
				}
			}
		}

		return null;
	}

	public static void close() {
		LOG.info("stop redis client");
		if (pool != null) {
			pool.destroy();
		}
	}

	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisMgr.getJedis();
			if (null != jedis)
				jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				jedis.close();
		}
	}

	public static void setEx(String key, String value, int minutes) {
		Jedis jedis = null;
		try {
			jedis = RedisMgr.getJedis();
			jedis.setex(key, minutes * 60, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	public static long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisMgr.getJedis();
			return jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("鑾峰彇鍞竴鍙峰嚭閿�");
		} finally {
			jedis.close();
		}
	}

	public static String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = RedisMgr.getJedis();
			if (null != jedis)
				value = jedis.get(key);
		} finally {
			if (null != jedis)
				jedis.close();

		}

		return value;
	}

	public static void del(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisMgr.getJedis();

			Set<String> keys = jedis.keys(key + "*");
			for (String k : keys) {
				jedis.del(k);
			}
		} finally {
			jedis.close();
		}
	}

	public static void pub(String channel, String message) {
		Jedis jedis = null;
		try {
			jedis = RedisMgr.getJedis();
			jedis.clientSetname("CLIENT-PUB");
			jedis.publish(channel, message);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public static void subscribe(final String channel, final IJedisSubListener listener) {
		Executors.newSingleThreadExecutor().execute(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					Jedis jedis = null;
					try {
						jedis = getJedis();
						jedis.clientSetname("CLIENT-SUB");
						LOG.info("subscribe:" + channel);
						jedis.subscribe(listener, channel);
					} catch (JedisConnectionException e) {
						LOG.warn("Exit redis subscribe, retry after 10 second");
					} catch (Exception ex) {
						LOG.warn(ex.getMessage());
					}

					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (Exception ex) {
						LOG.warn(ex.getMessage());
					}

					try {
						if (jedis != null) {
							jedis.close();
						}
					} catch (Exception ex) {
						LOG.warn(ex.getMessage());
					}

				}
			}
		});
	}

	public static void main(String[] args) {
		for (int i = 1; i < 10; i++) {
			System.out.println(RedisMgr.incr("sss"));
		}
	}
}
