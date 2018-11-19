package com.sgc.service;

import com.sgc.pojo.RedPacket;
import org.springframework.stereotype.Service;

public interface RedPacketService {
    /**
     * 获取红包
     * @param id 红包编号
     * @return 红包详细
     */
    public RedPacket getRedPacket(long id);

    /**
     * 扣减红包
     * @param id
     * @return
     */
    public int decreaseRedpacket(long id);
}
