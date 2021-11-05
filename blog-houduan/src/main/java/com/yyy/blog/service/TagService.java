package com.yyy.blog.service;

import com.yyy.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {
    // 保存tag
    Tag saveType(Tag tag);
    //查询tag
    Tag getType(Long id);
    //通过名称查询tag
    Tag getTagByName(String name);
    //分页查询
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);
    //修改tag，先根据id查到对象，再修改
    Tag updateTag(Long id,Tag tag);
    //删除
    void deleteTag(Long id);
}
