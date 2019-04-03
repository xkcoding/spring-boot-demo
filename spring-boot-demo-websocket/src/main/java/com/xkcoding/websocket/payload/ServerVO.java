package com.xkcoding.websocket.payload;

import com.google.common.collect.Lists;
import com.xkcoding.websocket.model.Server;
import com.xkcoding.websocket.payload.server.*;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 服务器信息VO
 * </p>
 *
 * @package: com.xkcoding.websocket.payload
 * @description: 服务器信息VO
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 17:25
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class ServerVO {
    List<CpuVO> cpu = Lists.newArrayList();
    List<JvmVO> jvm = Lists.newArrayList();
    List<MemVO> mem = Lists.newArrayList();
    List<SysFileVO> sysFile = Lists.newArrayList();
    List<SysVO> sys = Lists.newArrayList();

    public ServerVO create(Server server) {
        cpu.add(CpuVO.create(server.getCpu()));
        jvm.add(JvmVO.create(server.getJvm()));
        mem.add(MemVO.create(server.getMem()));
        sysFile.add(SysFileVO.create(server.getSysFiles()));
        sys.add(SysVO.create(server.getSys()));
        return null;
    }
}
