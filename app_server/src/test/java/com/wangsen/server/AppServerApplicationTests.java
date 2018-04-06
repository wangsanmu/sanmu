package com.wangsen.server;

import com.wangsen.user.api.SysUserService;
import com.wangsen.user.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServerApplicationTests {

//	@Autowired
//	private SysUserService sysUserServiceImpl;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RedisTemplate template;


//	@Test
//	public void contextLoads() {
//		SysUser sysUser = new SysUser();
//		sysUser.setUserName("w");
//		sysUser.setPassword("2423432");
//		sysUserServiceImpl.insert(sysUser);
//
//	}

	/**
	 * 测试 redis 序列化方式
	 * StringRedis默认采用的是Stirng 的序列化策略
	 * public interface ValueOperations<K,V>
	 * RedisSerializer<String> stringSerializer = new StringRedisSerializer();
	 */
	@Test
	public void testRedis(){
		//正常存储
		template.opsForValue().set("key","hello world");
		//设置10的存储时间
		template.opsForValue().set("wangsen","登陆时间发送到了咖啡健康第三方 ",10, TimeUnit.SECONDS);
		//该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
		template.opsForValue().set("key","redis",6);
		System.out.println("*********************"+template.opsForValue().get("key"));
		//看之前是否存在如果不存在就返回true,并保存数据 反之false
		System.out.println(template.opsForValue().setIfAbsent("key","sljfdslj"));
		System.out.println(template.opsForValue().setIfAbsent("key1","sljfdslj"));

		//为多个键分别设置它们的值
		Map<String,String> maps  = new HashMap<String, String>();
		maps.put("multi1","multi1");
		maps.put("multi2","multi2");
		maps.put("multi3","multi3");
		template.opsForValue().multiSet(maps);
		List<String> keys = new ArrayList<String>();
		keys.add("multi1");
		keys.add("multi2");
		keys.add("multi3");
		System.out.println(template.opsForValue().multiGet(keys));

		//如果map中有一个已经存在都不会再加载进去返回false
		Map<String,String> maps1 = new HashMap<String, String>();
		maps1.put("multi1","multi1");
		maps1.put("multi22","multi22");
		maps1.put("multi33","multi33");
		Map<String,String> maps2 = new HashMap<String, String>();
		maps2.put("multi1","multi1");
		maps2.put("multi2","multi2");
		maps2.put("multi3","multi3");
		System.out.println(template.opsForValue().multiSetIfAbsent(maps1));
		System.out.println(template.opsForValue().multiSetIfAbsent(maps2));

		//设置键的字符串值并返回其旧值getAndSet V getAndSet  返回test
		template.opsForValue().set("getSetTest","test");
		System.out.println(template.opsForValue().getAndSet("getSetTest","test2"));

		//increment Long increment(K key, long delta);
		template.opsForValue().increment("increlong",1);
		System.out.println(template.opsForValue().get("increlong"));

		//increment Double increment(K key, double delta);
		//也支持浮点数
		template.opsForValue().increment("increlong",1.2);
		System.out.println("***************"+template.opsForValue().get("increlong"));

		//append Integer append(K key, String value);
		//如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
		template.opsForValue().append("appendTest","Hello");
		System.out.println(template.opsForValue().get("appendTest"));
		template.opsForValue().append("appendTest","world");
		System.out.println(template.opsForValue().get("appendTest"));

		//get String get(K key, long start, long end);
		//截取key所对应的value字符串
		System.out.println("*********"+template.opsForValue().get("appendTest",0,5));

		//size Long size(K key);
		//返回key所对应的value值得长度
		template.opsForValue().set("key","hello world");
		System.out.println("***************"+template.opsForValue().size("key"));

		//setBit Boolean setBit(K key, long offset, boolean value);
		//对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
		//key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
		template.opsForValue().set("bitTest","a");
		// 'a' 的ASCII码是 97。转换为二进制是：01100001
		// 'b' 的ASCII码是 98  转换为二进制是：01100010
		// 'c' 的ASCII码是 99  转换为二进制是：01100011
		//因为二进制只有0和1，在setbit中true为1，false为0，因此我要变为'b'的话第六位设置为1，第七位设置为0
		template.opsForValue().setBit("bitTest",6, true);
		template.opsForValue().setBit("bitTest",7, false);
		System.out.println(template.opsForValue().get("bitTest"));

		//getBit Boolean getBit(K key, long offset);
		//获取键对应值的ascii码的在offset处位值
		System.out.println(template.opsForValue().getBit("bitTest",7));
	}


	/**
	 * redis 测试List数据结构
	 * public interface ListOperations<K,V>
	 * 这次使用的是jackson2JsonRedisSerializer 序列化
	 */
	@Test
	public void testRdisList(){
		//Long leftPush(K key, V value); :返回的结果为推送操作后的列表的长度
		//将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
		redisTemplate.opsForList().leftPush("list","java");
		redisTemplate.opsForList().leftPush("list","python");
		redisTemplate.opsForList().leftPush("list","C++");


		//List<V> range(K key, long start, long end);
		//返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
		System.out.println(redisTemplate.opsForList().range("list",0,-1));


		//Long leftPushAll(K key, V... values);
		//批量把一个数组插入到列表中
		String[] stringarrays = new String[]{"1","2","3"};
		redisTemplate.opsForList().leftPushAll("listarray",stringarrays);
		System.out.println(redisTemplate.opsForList().range("listarray",0,-1));

		//Long leftPushAll(K key, Collection<V> values);
		//批量把一个集合插入到列表中
		List<Object> strings = new ArrayList<Object>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		redisTemplate.opsForList().leftPushAll("listcollection4", strings);
		System.out.println(redisTemplate.opsForList().range("listcollection4",0,-1));

		//Long leftPushIfPresent(K key, V value);
		//只有存在key对应的列表才能将这个value值插入到key所对应的列表中
		System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","aa"));
		System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","bb"));
		System.out.println(redisTemplate.opsForList().leftPush("leftPushIfPresent","aa"));
		System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","bb"));

		//Long leftPush(K key, V pivot, V value);
		//把value值放到key对应列表中pivot值的左面，如果pivot值存在的话
		redisTemplate.opsForList().leftPush("list","java","oc");
		System.out.print(redisTemplate.opsForList().range("list",0,-1));

		//Long rightPush(K key, V value);
		//将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
		redisTemplate.opsForList().rightPush("listRight","java");
		redisTemplate.opsForList().rightPush("listRight","python");
		redisTemplate.opsForList().rightPush("listRight","c++");


		//Long rightPushAll(K key, V... values);
		String[] stringarrays1 = new String[]{"1","2","3"};
		redisTemplate.opsForList().rightPushAll("listarrayright",stringarrays1);
		System.out.println(redisTemplate.opsForList().range("listarrayright",0,-1));

		//Long rightPushAll(K key, Collection<V> values);
		List<Object> strings1 = new ArrayList<Object>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		redisTemplate.opsForList().rightPushAll("listcollectionright", strings1);
		System.out.println(redisTemplate.opsForList().range("listcollectionright",0,-1));

		//Long rightPushIfPresent(K key, V value);
		//只有存在key对应的列表才能将这个value值插入到key所对应的列表中
		System.out.println(redisTemplate.opsForList().rightPushIfPresent("rightPushIfPresent","aa"));
		System.out.println(redisTemplate.opsForList().rightPushIfPresent("rightPushIfPresent","bb"));
		System.out.println("==========分割线===========");
		System.out.println(redisTemplate.opsForList().rightPush("rightPushIfPresent","aa"));
		System.out.println(redisTemplate.opsForList().rightPushIfPresent("rightPushIfPresent","bb"));


		//Long rightPush(K key, V pivot, V value);
		//把value值放到key对应列表中pivot值的右面，如果pivot值存在的话
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));
		redisTemplate.opsForList().rightPush("listRight","python","oc");
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));

		//void set(K key, long index, V value);
		//在列表中index的位置设置value值
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));
		redisTemplate.opsForList().set("listRight",1,"setValue");
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));

		//Long remove(K key, long count, Object value);
		//从存储在键中的列表中删除等于值的元素的第一个计数事件。
		//计数参数以下列方式影响操作：
		//count> 0：删除等于从头到尾移动的值的元素。
		//count <0：删除等于从尾到头移动的值的元素。
		//count = 0：删除等于value的所有元素。
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));
		redisTemplate.opsForList().remove("listRight",1,"setValue");//将删除列表中存储的列表中第一次次出现的“setValue”。
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));

		//V index(K key, long index);
		//根据下表获取列表中的值，下标是从0开始的
		System.out.println(redisTemplate.opsForList().range("listRight",0,-1));
		System.out.println(redisTemplate.opsForList().index("listRight",2));

		//V leftPop(K key);
		//弹出最左边的元素，弹出之后该值在列表中将不复存在
		System.out.println(template.opsForList().range("list",0,-1));
		System.out.println(template.opsForList().leftPop("list"));
		System.out.println(template.opsForList().range("list",0,-1));

		//V leftPop(K key, long timeout, TimeUnit unit);
		//移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。


		//V rightPop(K key);
		//弹出最右边的元素，弹出之后该值在列表中将不复存在
		System.out.println(template.opsForList().range("list",0,-1));
		System.out.println(template.opsForList().rightPop("list"));
		System.out.println(template.opsForList().range("list",0,-1));

		//V rightPopAndLeftPush(K sourceKey, K destinationKey);
		//用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
		System.out.println(template.opsForList().range("list",0,-1));
		template.opsForList().rightPopAndLeftPush("list","rightPopAndLeftPush");
		System.out.println(template.opsForList().range("list",0,-1));
		System.out.println(template.opsForList().range("rightPopAndLeftPush",0,-1));
	}

}
