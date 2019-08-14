package com.example.myspringboot;

import com.example.myspringboot.model.AyMood;
import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.mq.AyMoodProducer;
import com.example.myspringboot.service.AyMoodService;
import com.example.myspringboot.service.AyUserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySpringBootApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private AyUserService ayUserService;

    @Resource
    private AyMoodService ayMoodService;

    @Test
    public void testRepository(){
        //查询所有数据
        List<AyUser> userList = ayUserService.findAll();
        System.out.println("findAll():" + userList.size());
        //通过name查询数据
        List<AyUser> userList2 = ayUserService.findByName("阿毅");
        System.out.println("findByName():" + userList2.size());
        Assert.isTrue(userList2.get(0).getName().equals("阿毅"),"data error");
        // 通过name模糊查询
        List<AyUser> userList3 = ayUserService.findByNameLike("%毅%");
        System.out.println("findByNameLike():"+userList3.size());
        Assert.isTrue(userList3.get(0).getName().equals("阿毅"),"data error");

        // 通过id列表查找数据
        List<String> ids= new ArrayList<String>() ;
        ids.add ("1") ;
        ids.add ("2") ;
        List<AyUser> userList4 = ayUserService.findByIdIn(ids) ;
        System.out.println("findByIdIn():" + userList4.size());
        //分页查询数据
        PageRequest pageRequest = new PageRequest(0,10);
        Page<AyUser> userList5 = ayUserService.findAll(pageRequest);
        System.out.println("page findAll():" + userList5.getTotalPages()+"/"+userList5.getSize());
        //新增数据
//        AyUser ayUser = new AyUser();
//        ayUser.setId ("3");
//        ayUser.setName ("test");
//        ayUser.setPassword ("123") ;
//        ayUserService.save(ayUser);
        //删除数据
        ayUserService.delete ("3");

    }

    @Test
    public void testTransaction(){
        AyUser ayUser = new AyUser();
        ayUser.setId("3");
        ayUser.setName("阿华");
        ayUser.setPassword("123");
        ayUserService.save(ayUser);
    }



//    redis测试
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        // 增key:name,value:ay
        redisTemplate.opsForValue().set("name","ay");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
//        // 删除
//        redisTemplate.delete("name");
//        // 更新
//        redisTemplate.opsForValue().set("name","al");
//        // 查询
//        name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }


    @Test
    public void testFindById(){
        Long redisUserSize = 0L;
        // 查询id = 1的数据，该数据存在于Redis缓存中
        AyUser ayUser = ayUserService.findById("1");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为："+redisUserSize);
        System.out.println("---->>id:"+ayUser.getId()+"name"+ayUser.getName());
        // 查询id=2的数据，该数据存在于Redis缓存中
        AyUser ayUser1 = ayUserService.findById("2");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为："+redisUserSize);
        System.out.println("-->>>id:"+ayUser1.getId()+"name:"+ayUser1.getName());
        // 查询id = 4的数据，不存在于Redis缓存中，存在于数据库中，所以会把在数据库中查询的数据更新到缓存中
        AyUser ayUser2 = ayUserService.findById("4");
        System.out.println("---->>id:"+ayUser2.getId()+"name"+ayUser2.getName());
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为："+redisUserSize);
    }

    Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testLog4j(){
        ayUserService.delete("3");
        logger.info("delete success!!!");
    }

    @Test
    public void testMybatis() {
        AyUser ayUser = ayUserService.findByNameAndPassword("阿毅","123456");
        logger.info(ayUser.getId() + ayUser.getName());
    }

    @Test
    public void testAyMood() {
        AyMood ayMood = new AyMood();
        ayMood.setId("1");
        ayMood.setUserId("1");
        ayMood.setPraiseNum(0);
        ayMood.setContent("这");
        ayMood.setPublishTime(new Date());
        AyMood mood = ayMoodService.save(ayMood);
    }

    @Resource
    private AyMoodProducer ayMoodProducer;

    @Test
    public void testActiveMQQueue() {
        Destination destination = new ActiveMQQueue("ay.queue");
        ayMoodProducer.sendMessage(destination,"hello,mq!");
    }

    @Test
    public void testActiveMQAsynSave() {
        AyMood ayMood = new AyMood();
        ayMood.setId("2");
        ayMood.setUserId("2");
        ayMood.setPraiseNum(0);
        ayMood.setContent("这是我的第一条微信说说");
        ayMood.setPublishTime(new Date());
        String msg = ayMoodService.asynSave(ayMood);
        System.out.println("异步发表说说" + msg);
    }

    @Test
    public void testAysn() {
        long startTime = System.currentTimeMillis();
        System.out.println("第1次查询所有用户");
        List<AyUser> ayUserList = ayUserService.findAll();
        System.out.println("第2次查询所有用户");
        List<AyUser> ayUserList2 = ayUserService.findAll();
        System.out.println("第3次查询所有用户");
        List<AyUser> ayUserList3 = ayUserService.findAll();
        long endTime = System.currentTimeMillis();
        System.out.println("总共耗时" + (endTime - startTime) + "毫秒");

    }

    @Test
    public void testAsync2() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("第1次查询所有用户");
        Future<List<AyUser>> ayUserList = ayUserService.findAsynAll();
        System.out.println("第2次查询所有用户");
        Future<List<AyUser>> ayUserList2 = ayUserService.findAsynAll();
        System.out.println("第3次查询所有用户");
        Future<List<AyUser>> ayUserList3 = ayUserService.findAsynAll();
        while(true) {
            if(ayUserList.isDone() && ayUserList2.isDone() && ayUserList3.isDone()) {
                break;
            } else {
                Thread.sleep(10);
            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("共消耗" + (endTime - startTime) + "毫秒");
    }

}

