package com.sgc.dao;

import com.sgc.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {

    /**
     * 将用户抢到的红包信息插入数据库
     * @param userRedPacket {@link UserRedPacket} Object
     * @return 受影响记录条数,成功：1, 失败：0
     */
    public int grapRedPacket(UserRedPacket userRedPacket);
}
