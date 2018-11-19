package com.sgc.service.impl;

import com.sgc.dao.RedPacketDao;
import com.sgc.dao.UserRedPacketDao;
import com.sgc.pojo.RedPacket;
import com.sgc.pojo.UserRedPacket;
import com.sgc.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {
    // 失败
    private static final int FAILED=0;

    @Autowired
    private UserRedPacketDao userRedPacketDao;

    @Autowired
    private RedPacketDao redPacketDao;

    /**
     * 将用户抢到的红包信息存入数据库
     * @param redPacketId 所抢到的红包id
     * @param userId 抢到红包用户id
     * @return
     */
    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
    public int grapRedPacket(long redPacketId, long userId) {
        //根据红包id获取红包实例对象
        // RedPacket redPacket=redPacketDao.getRedPacket(redPacketId);
        // 使用共享锁根据红包ID获取红包
        RedPacket redPacket=redPacketDao.getRedPacketForUpdate(redPacketId);
        //获取红包剩余数量
        int leftRedPacket=redPacket.getStock();
        if(leftRedPacket>0){
            redPacketDao.decreaseRedPacket(redPacketId);
            UserRedPacket userRedPacket=new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("redpacket-"+redPacketId);

            // 将红包信息存入数据库
            int result=userRedPacketDao.grapRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    /**
     * 通过共享锁实现并发处理
     * @param redPacketId
     * @param userId
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int grapRedPacketForVersion(long redPacketId,long userId){
        /** 非重入锁-由于版本号限制存在红包抢不完情况
        // 获取红包信息
        RedPacket redPacket=redPacketDao.getRedPacket(redPacketId);
        int oldVersion=redPacket.getVersion();
        int stock=redPacket.getStock();
        if(stock>0){
            // 再次传入线程保存的version值给sql判断,是否有其他线程更改过数据
            int update=redPacketDao.decreaseRedPacketForVersion(redPacketId,oldVersion);

            if(update==0) return FAILED;
            UserRedPacket userRedPacket=new UserRedPacket();
            userRedPacket.setUserId(userId);
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("redpacket-"+redPacketId);

            //将红包信息写入红包记录表
            int result=userRedPacketDao.grapRedPacket(userRedPacket);

            return result;
        }
        return FAILED;
         */
        //记录业务处理开始时间
        long start=System.currentTimeMillis();
        //无限循环,等待成功或时间满100毫秒
        while (true) {
            //获取循环当前时间
            long end=System.currentTimeMillis();
            // 如果业务处理超时则返回失败
            if(end-start>100) return FAILED;
            // 获取红包信息
            RedPacket redPacket=redPacketDao.getRedPacket(redPacketId);
            // 保存当前红包version
            int oldVersion=redPacket.getVersion();
            // 获取红包库存
            int stock=redPacket.getStock();
            if(stock>0){
                // 尝试更新红包库存
                int update=redPacketDao.decreaseRedPacketForVersion(redPacketId,oldVersion);
                if(0==update) continue;
                UserRedPacket userRedPacket=new UserRedPacket();
                userRedPacket.setUserId(userId);
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("RedPacket-"+redPacketId);
                // 记录红包信息
                int result=userRedPacketDao.grapRedPacket(userRedPacket);
                return result;
            }else{
                return FAILED;
            }
        }
    }
}
