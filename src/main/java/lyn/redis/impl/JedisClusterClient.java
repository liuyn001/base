package lyn.redis.impl;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lyn.redis.IJedisClient;
import lyn.util.SerializeUtil;
import lyn.util.StringUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;

/**
 * 集群方式redis客户端操作
 * @author 353654
 *
 */
//集群环境才开启Service，在spring注入
//@Service("jedisClusterClient")
public class JedisClusterClient implements IJedisClient{

    private static Logger logger = Logger.getLogger(JedisClusterClient.class);

    @Resource
    private JedisCluster jedisCluster;


    @Override
    public  String get(String key) {
        String value = null;
        try {
            if (jedisCluster.exists(key)) {
                value = jedisCluster.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return value;
    }

    @Override
    public   Object getObject(String key) {
        Object value = null;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                value = toObject(jedisCluster.get(getBytesKey(key)));
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
//          try {
//              jedisCluster.close();
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
        }
        return value;
    }

    @Override
    public   String set(String key, String value, int cacheSeconds) {
        String result = null;

        try {

            result = jedisCluster.set(key, value);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
           // logger.debug("set {} = {}", key, value);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return result;
    }

    @Override
    public   String setObject(String key, Object value, int cacheSeconds) {
        String result = null;

        try {
            result = jedisCluster.set(getBytesKey(key), toBytes(value));
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return result;
    }

    @Override
    public  List<String> getList(String key) {
        List<String> value = null;
        try {
            if (jedisCluster.exists(key)) {
                value = jedisCluster.lrange(key, 0, -1);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return value;
    }

    @Override
    public   List<Object> getObjectList(String key) {
        List<Object> value = null;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                List<byte[]> list = jedisCluster.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list){
                    value.add(toObject(bs));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return value;
    }

    @Override
    public   long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        try {
            if (jedisCluster.exists(key)) {
                jedisCluster.del(key);
            }
            result = jedisCluster.rpush(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                jedisCluster.del(key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = jedisCluster.rpush(getBytesKey(key), (byte[][])list.toArray());
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            //returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long listAdd(String key, String... value) {
        long result = 0;
        try {
            result = jedisCluster.rpush(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long listObjectAdd(String key, Object... value) {
        long result = 0;
        try {
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = jedisCluster.rpush(getBytesKey(key), (byte[][])list.toArray());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   Set<String> getSet(String key) {
        Set<String> value = null;
        try {
            if (jedisCluster.exists(key)) {
                value = jedisCluster.smembers(key);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return value;
    }

    @Override
    public Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                value = Sets.newHashSet();
                Set<byte[]> set = jedisCluster.smembers(getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return value;
    }

    @Override
    public   long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        try {
            if (jedisCluster.exists(key)) {
                jedisCluster.del(key);
            }
            result = jedisCluster.sadd(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                jedisCluster.del(key);
            }
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = jedisCluster.sadd(getBytesKey(key), (byte[][])set.toArray());
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long setSetAdd(String key, String... value) {
        long result = 0;
        try {
            result = jedisCluster.sadd(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        try {
            Set<byte[]> set = Sets.newHashSet();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = jedisCluster.rpush(getBytesKey(key), (byte[][])set.toArray());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        try {
            if (jedisCluster.exists(key)) {
                value = jedisCluster.hgetAll(key);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return value;
    }

    @Override
    public   Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                value = Maps.newHashMap();
                Map<byte[], byte[]> map = jedisCluster.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()){
                    value.put(new String(e.getKey()), toObject(e.getValue()));
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        }
        return value;
    }

    @Override
    public   String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        try {
            if (jedisCluster.exists(key)) {
                jedisCluster.del(key);
            }
            result = jedisCluster.hmset(key, value);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        try {
            if (jedisCluster.exists(getBytesKey(key))) {
                jedisCluster.del(key);
            }
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedisCluster.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   String mapPut(String key, Map<String, String> value) {
        String result = null;
        try {
            result = jedisCluster.hmset(key, value);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        try {
            Map<byte[], byte[]> map = Maps.newHashMap();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
            }
            result = jedisCluster.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long mapRemove(String key, String mapKey) {
        long result = 0;
        try {
            result = jedisCluster.hdel(key, mapKey);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        try {
            result = jedisCluster.hdel(getBytesKey(key), getBytesKey(mapKey));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   boolean mapExists(String key, String mapKey) {
        boolean result = false;
        try {
            result = jedisCluster.hexists(key, mapKey);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        try {
            result = jedisCluster.hexists(getBytesKey(key), getBytesKey(mapKey));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long del(String key) {
        long result = 0;
        try {
            if (jedisCluster.exists(key)){
                result = jedisCluster.del(key);
            }else{
                logger.info("del {"+key+"} not exists");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   long delObject(String key) {
        long result = 0;
        try {
            if (jedisCluster.exists(getBytesKey(key))){
                result = jedisCluster.del(getBytesKey(key));
            }else{
                logger.debug("delObject {"+key+"} not exists");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   boolean exists(String key) {
        boolean result = false;
        try {
            result = jedisCluster.exists(key);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }

    @Override
    public   boolean existsObject(String key) {
        boolean result = false;
        try {
            result = jedisCluster.exists(getBytesKey(key));
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
//          returnResource(jedis);
        }
        return result;
    }


    /**
     * 获取byte[]类型Key
     * @param key
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static  byte[] getBytesKey(Object object){
        if(object instanceof String){
            return StringUtils.getBytes((String)object);
        }else{
            return SerializeUtil.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     * @param key
     * @return
     */
    public static  byte[] toBytes(Object object){
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
     * @time 2017年5月6日 上午11:03:58
     * @param key
     * @param value
     * @return
     */
    @Override
    public long setnx(String key, String value) {
        return jedisCluster.setnx(key,value);
    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:03:58
     * @param key
     * @param ttl
     */
    @Override
    public void expire(String key, int ttl) {
        jedisCluster.expire(key, ttl);
    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:03:58
     * @param array
     */
    @Override
    public void del(String[] array) {
        jedisCluster.del(array);

    }

    /**
     * @autho 董杨炀
     * @time 2017年5月6日 上午11:09:24
     * @return
     */
    @Override
    public Pipeline pipelined() {
        //TODO redis集群环境获取管道模式执行批量操作
        return null;
        //TODO redis集群环境批量操作待续
    }

}