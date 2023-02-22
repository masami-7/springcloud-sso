package com.yl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    /**
     * 查询资源
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:res:list')")
    @GetMapping
    public String doSelect() {
        return "Select Resource ok";
    }

    /**
     * 创建资源
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:res:create')")
    @PostMapping
    public String doCreate() {
        return "Create Resource OK";
    }

    /**
     * 修改资源
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sys:res:update')")
    @PutMapping
    public String doUpdate() {
        return "Update Resource OK";
    }

    /**
     * 删除资源
     *
     * @return
     */
    @DeleteMapping
    public String doDelete() {
        return "Delete resource ok";
    }

}
