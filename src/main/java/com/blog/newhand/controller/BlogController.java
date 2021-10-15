package com.blog.newhand.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.newhand.common.lang.Result;
import com.blog.newhand.entity.Blog;
import com.blog.newhand.service.BlogService;
import com.blog.newhand.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-09-01
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {


        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);

        IPage<Blog> pageDate = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageDate);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {

        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除！");
        return Result.success(blog);
    }

    @PostMapping("/blog/edit")
    @RequiresAuthentication
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());

            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑！");

        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);

            BeanUtils.copyProperties(blog, temp, "id", "userId", "created", "status");
            blogService.saveOrUpdate(temp);
        }

        return Result.success(null);
    }
}
