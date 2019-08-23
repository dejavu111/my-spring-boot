package com.example.myspringboot.service.impl;

import com.example.myspringboot.dao.AyUserDao;
import com.example.myspringboot.exception.BusinessException;
import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.repository.AyUserRepository;
import com.example.myspringboot.service.AyUserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

//@Transactional
@Service
public class AyUserServiceImpl implements AyUserService {
    @Resource(name = "ayUserRepository")
    private AyUserRepository ayUserRepository;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AyUserDao ayUserDao;

    private static final String ALL_USER = "ALL_USER_LIST";

    Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public AyUser findById(String id) {
        // step.1 查询Redis缓存中的所有数据
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER,0,-1);
        if(ayUserList != null && ayUserList.size() > 0){
            for(AyUser user: ayUserList){
                if(user.getId().equals((id))){
                    return user;
                }
            }
        }

        // step.2 查询数据库中的数据
        AyUser ayUser = ayUserRepository.getOne(id);
        if(ayUser != null){
            // step.3将数据插入Redis缓存中
            redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
        }
        return ayUser;
    }


    @Override
    public List<AyUser> findAll() {
        try {
            System.out.println("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUser> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时：" + (end - start) + "毫秒");
            return ayUserList;
        }catch (Exception e) {
            logger.error("method [findAll] error" + e);
            return Collections.EMPTY_LIST;
        }

    }

    //注解在方法上
    @Transactional
    @Override
    public AyUser save(AyUser ayUser) {
        AyUser saveUser = ayUserRepository.save(ayUser);
        //出现空指针异常验证事务回滚
//        String error = null;
//        error.split("/");
//        return saveUser;
        redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
        return ayUserRepository.save(ayUser);
    }

    @Override
    public void delete(String id) {
        ayUserRepository.deleteById(id);
        logger.info("userId:" + id +"用户被删除");
        redisTemplate.opsForList().remove(ALL_USER,0,ayUserRepository.findById(id));
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String username) {
        return ayUserRepository.findByName(username);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        return ayUserDao.findByNameAndPassword(name,password);
    }

    @Override
    @Async
    public Future<List<AyUser>> findAsynAll() {
        try {
            System.out.println("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUser> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("共耗时" + (end - start) + "毫秒");
            return new AsyncResult<List<AyUser>>(null);
        }catch (Exception e) {
            logger.error("method [findAll] error",e);
            return new AsyncResult<List<AyUser>>(null);
        }
    }

    @Override
    @Retryable(value = {BusinessException.class}, maxAttempts = 5,
    backoff = @Backoff(delay = 5000, multiplier = 2))
    public AyUser findByNameAndPasswordRetry(String name, String password) {
        System.out.println("{findByNameAndPasswordRetry}方法失败重试了！");
        throw new BusinessException();
    }
}
