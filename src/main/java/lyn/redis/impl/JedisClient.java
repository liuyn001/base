package lyn.redis.impl;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lyn.redis.IJedisClient;
import lyn.util.SerializeUtil;
import lyn.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 单机方式redis客户端操作
 * @author 353654
 *
 */
@Service("jedisClient")
public class JedisClient implements IJedisClient {

    private static Logger logger = Logger.getLogger(JedisClient.class);

    @Resource
    private JedisPool jedisPool;

    /**
     * 获取缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:16:19
     * @param key
     * @return
     */
    public  String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:16:15
     * @param key
     * @return
     */
    public  Object getObject(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = toObject(jedis.get(getBytesKey(key)));
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:16:10
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:16:03
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(getBytesKey(key), SerializeUtil.serialize(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取List缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:57
     * @param key
     * @return
     */
    public  List<String> getList(String key) {
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:52
     * @param key
     * @return
     */
    public  List<Object> getObjectList(String key) {
        List<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list){
                    value.add(toObject(bs));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:47
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置List缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:41
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(SerializeUtil.serialize(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:35
     * @param key
     * @param value
     * @return
     */
    public  long listAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.rpush(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:29
     * @param key
     * @param value
     * @return
     */
    public  long listObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(SerializeUtil.serialize(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:24
     * @param key
     * @return
     */
    public  Set<String> getSet(String key) {
        Set<String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.smembers(key);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:18
     * @param key
     * @return
     */
    public  Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Sets.newHashSet();
                Set<byte[]> set = jedis.smembers(getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Set缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:12
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.sadd(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Set缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:07
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(SerializeUtil.serialize(o));
            }
            result = jedis.sadd(getBytesKey(key), (byte[][])set.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:15:02
     * @param key
     * @param value
     * @return
     */
    public  long setSetAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.sadd(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:57
     * @param key
     * @param value
     * @return
     */
    public  long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(SerializeUtil.serialize(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])set.toArray());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取Map缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:46
     * @param key
     * @return
     */
    public  Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.hgetAll(key);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Map缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:41
     * @param key
     * @return
     */
    public  Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = Maps.newHashMap();
                Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()){
                    value.put(new String(e.getKey()), toObject(e.getValue()));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置Map缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:36
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置Map缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:29
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), SerializeUtil.serialize(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:24
     * @param key
     * @param value
     * @return
     */
    public  String mapPut(String key, Map<String, String> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hmset(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:19
     * @param key
     * @param value
     * @return
     */
    public  String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), SerializeUtil.serialize(e.getValue()));
            }
            result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:11
     * @param key
     * @param mapKey
     * @return
     */
    public  long mapRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(key, mapKey);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:14:04
     * @param key
     * @param mapKey
     * @return
     */
    public  long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:58
     * @param key
     * @param mapKey
     * @return
     */
    public  boolean mapExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(key, mapKey);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:52
     * @param key
     * @param mapKey
     * @return
     */
    public  boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:47
     * @param key
     * @return
     */
    public  long del(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)){
                result = jedis.del(key);
            }else{
                logger.debug("del {"+key+"} not exists");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:42
     * @param key
     * @return
     */
    public  long delObject(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))){
                result = jedis.del(getBytesKey(key));
            }else{
                logger.debug("delObject {"+key+"} not exists");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:37
     * @param key
     * @return
     */
    public  boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:27
     * @param key
     * @return
     */
    public  boolean existsObject(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.exists(getBytesKey(key));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取资源
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:19
     * @return
     * @throws JedisException
     */
    public  Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
//          logger.debug("getResource.", jedis);
        } catch (JedisException e) {
            logger.warn("getResource.", e);
            returnBrokenResource(jedis);
            throw e;
        }
        return jedis;
    }

    /**
     * 归还资源
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:13
     * @param jedis
     */
    @SuppressWarnings("deprecation")
    public  void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:06
     * @param jedis
     */
    @SuppressWarnings("deprecation")
    public  void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 获取byte[]类型Key
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:13:01
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object){
        if(object instanceof String){
            return StringUtils.getBytes((String)object);
        }else{
            return SerializeUtil.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:12:55
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object){
        return SerializeUtil.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param key
     * @return
     */
    public static Object toObject(byte[] bytes){
        return SerializeUtil.deserialize(bytes);
    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午10:25:16
     * @param key
     * @param value
     * @return
     */
    @Override
    public long setnx(String key, String value) {
        return this.getResource().setnx(key,value);
    }

    /**
     * 
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:03:52
     * @param key
     * @param ttl
     */
    @Override
    public void expire(String key, int ttl) {
        this.getResource().expire(key,ttl);
    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:03:52
     * @param array
     */
    @Override
    public void del(String[] array) {
        this.getResource().del(array);
    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:09:28
     * @return
     */
    @Override
    public Pipeline pipelined() {
        return this.getResource().pipelined();
    }

}