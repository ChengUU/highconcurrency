package com.sgc.service.impl;

import com.sgc.dao.RedPacketDao;
import com.sgc.pojo.RedPacket;
import com.sgc.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;


@Service
public class RedPacketServiceImpl implements RedPacketService {
    @Autowired
    private RedPacketDao redPacketDao;

    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public RedPacket getRedPacket(long id) {
        return redPacketDao.getRedPacket(id);
    }

    @Override
    @Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
    public int decreaseRedpacket(long id) {
        return redPacketDao.decreaseRedPacket(id);
    }
}
