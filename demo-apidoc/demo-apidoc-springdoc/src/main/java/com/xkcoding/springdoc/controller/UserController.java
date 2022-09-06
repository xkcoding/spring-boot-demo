package com.xkcoding.springdoc.controller;

import com.xkcoding.common.model.viewmodel.Response;
import com.xkcoding.springdoc.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 测试接口
 *
 * @author 一珩（沈扬凯 yk.shen@tuya.com）
 * @date 2022-09-06 22:36
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理")
public class UserController {
    @GetMapping
    @Operation(summary = "条件查询（DONE）", description = "备注")
    @Parameters({
        @Parameter(name = "username", description = "用户名", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class), required = true)
    })
    public Response<User> getByUserName(String username) {
        log.info("多个参数用  @Parameters");
        return Response.ofSuccess(new User(1, username, "JAVA"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "主键查询（DONE）", description = "备注")
    @Parameters({
        @Parameter(name = "id", description = "用户编号", in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class), required = true)
    })
    public Response<User> get(@PathVariable Integer id) {
        log.info("单个参数用  @Parameter");
        return Response.ofSuccess(new User(id, "u1", "p1"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户（DONE）", description = "备注")
    @Parameter(name = "id", description = "用户编号", in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class), required = true)
    public void delete(@PathVariable Integer id) {
        log.info("单个参数用 Parameter");
    }

    @PostMapping
    @Operation(summary = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PostMapping("/multipar")
    @Operation(summary = "添加用户（DONE）")
    public List<User> multipar(@RequestBody List<User> user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PostMapping("/array")
    @Operation(summary = "添加用户（DONE）")
    public User[] array(@RequestBody User[] user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @Parameter 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }

    @PostMapping("/{id}/file")
    @Operation(summary = "文件上传（DONE）")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }
}
