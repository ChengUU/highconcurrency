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
        RedPacket redPacket=redPacketDao.getRedPacket(redPacketId);
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
}
