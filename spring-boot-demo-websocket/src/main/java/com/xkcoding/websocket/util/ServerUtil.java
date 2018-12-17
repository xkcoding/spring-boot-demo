package com.xkcoding.websocket.util;

import cn.hutool.core.lang.Dict;
import com.xkcoding.websocket.model.Server;
import com.xkcoding.websocket.payload.ServerVO;

/**
 * <p>
 * 服务器转换工具类
 * </p>
 *
 * @package: com.xkcoding.websocket.util
 * @description: 服务器转换工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-17 10:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class ServerUtil {
    /**
     * 包装成 ServerVO
     *
     * @param server server
     * @return ServerVO
     */
    public static ServerVO wrapServerVO(Server server) {
        ServerVO serverVO = new ServerVO();
        serverVO.create(server);
        return serverVO;
    }

    /**
     * 包装成 Dict
     *
     * @param serverVO serverVO
     * @return Dict
     */
    public static Dict wrapServerDict(ServerVO serverVO) {
        Dict dict = Dict.create()
                .set("cpu", serverVO.getCpu().get(0).getData())
                .set("mem", serverVO.getMem().get(0).getData())
                .set("sys", serverVO.getSys().get(0).getData())
                .set("jvm", serverVO.getJvm().get(0).getData())
                .set("sysFile", serverVO.getSysFile().get(0).getData());
        return dict;
    }
}
