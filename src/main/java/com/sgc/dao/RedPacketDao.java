package com.sgc.dao;

import com.sgc.pojo.RedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {
    /**
     * 获取红包信息
     *
     * @param id 红包id
     * @return 红包详细信息
     */
    public RedPacket getRedPacket(long id);

    /**
     * 扣减红包数
     *
     * @param id 红包id
     * @return 更新记录条数
     */
    public int decreaseRedPacket(long id);
}
