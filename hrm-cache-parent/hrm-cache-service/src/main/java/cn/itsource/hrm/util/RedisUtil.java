package cn.itsource.hrm.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Redis的工具类
 */
public class RedisUtil {

    private static JedisPool jedisPool;

    static{

        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(20);
        //最大空闲连接数
        config.setMaxIdle(10);
        //最小空闲连接
        config.setMinIdle(1);
        //获取连接时测试连接的有效性
        config.setTestOnBorrow(true);
        //获取连接的最大等待时间
        config.setMaxWaitMillis(2000);
        jedisPool = new JedisPool(
                config,
                bundle.getString("host"),
                Integer.parseInt(bundle.getString("port")),
                Integer.parseInt(bundle.getString("timeout")),
                bundle.getString("password"));
    }

    /**
     * 获取连接
     * @return
     */
    public static Jedis getConnection(){
        return jedisPool.getResource();
    }

    /**
     * 设置值
     * @param key key
     * @param value value
     * @param expire 过期时间，如果为null则表示永不超时
     */
    public static void set(String key,String value,Integer expire){
        Jedis jedis = getConnection();
        if(expire==null){
            jedis.set(key,value);
        }else{
            jedis.setex(key,expire,value);
        }
        jedis.close();
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public static String get(String key){
        Jedis jedis = null;
        try {
            jedis = getConnection();
            String value = jedis.get(key);
            return value;
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 删除key
     * @param key
     */
    public static void delete(String key){
        Jedis jedis = null;
        try {
            jedis = getConnection();
            jedis.del(key);
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 关闭连接
     * @param jedis
     */
    public static void closeConnection(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }

}
