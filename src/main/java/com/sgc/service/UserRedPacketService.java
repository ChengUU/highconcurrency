package com.sgc.service;

public interface UserRedPacketService {
    /**
     * 保存抢到的红包
     * @param redPacketId 所抢到的红包id
     * @param userId 抢到红包用户id
     * @return
     */
    public int grapRedPacket(long redPacketId,long userId);
}
