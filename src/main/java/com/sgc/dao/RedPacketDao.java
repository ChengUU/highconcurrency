package com.sgc.dao;

import com.sgc.pojo.RedPacket;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 使用排他锁查询红包信息
     * @param id 红包ID
     * @return 受影响数据条数
     */
    public RedPacket getRedPacketForUpdate(long id);

    public int decreaseRedPacketForVersion(@Param("id")long id,@Param("version") int version);
}
