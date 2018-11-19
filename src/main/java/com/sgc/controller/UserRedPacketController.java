package com.sgc.controller;

import com.sgc.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {
    @Autowired
    private UserRedPacketService userRedPacketService;

    @RequestMapping("/grapRedPacket")
    @ResponseBody
    public Map<String,Object> grapUserRedPacket(long redPacketId, long userId){
        //抢红包
        int result=userRedPacketService.grapRedPacket(redPacketId,userId);
        Map<String,Object> retMap=new HashMap<>();
        boolean flag=result>0;
        retMap.put("success",flag);
        retMap.put("message",flag?"抢红包成功":"抢红包失败");
        return retMap;
    }

    @RequestMapping("/grapRedPacketForVersion")
    @ResponseBody
    public Map<String,Object> grapRedPacketForVersion(long redPacketId,long userId){
        //抢红包
        int result=userRedPacketService.grapRedPacketForVersion(redPacketId,userId);
        Map<String,Object> response=new HashMap<>();
        boolean flag=result>0;
        response.put("success",flag);
        response.put("message",flag?"抢红包成功":"抢红包失败");
        return response;
    }

}
