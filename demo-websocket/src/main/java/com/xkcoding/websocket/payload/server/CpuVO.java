package com.xkcoding.websocket.payload.server;

import com.google.common.collect.Lists;
import com.xkcoding.websocket.model.server.Cpu;
import com.xkcoding.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * CPU相关信息实体VO
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 17:27
 */
@Data
public class CpuVO {
    List<KV> data = Lists.newArrayList();

    public static CpuVO create(Cpu cpu) {
        CpuVO vo = new CpuVO();
        vo.data.add(new KV("核心数", cpu.getCpuNum()));
        vo.data.add(new KV("CPU总的使用率", cpu.getTotal()));
        vo.data.add(new KV("CPU系统使用率", cpu.getSys() + "%"));
        vo.data.add(new KV("CPU用户使用率", cpu.getUsed() + "%"));
        vo.data.add(new KV("CPU当前等待率", cpu.getWait() + "%"));
        vo.data.add(new KV("CPU当前空闲率", cpu.getFree() + "%"));
        return vo;
    }
}
