package com.yyy.blog.service;

import com.yyy.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    // 保存type
    Type saveType(Type type);
    //查询type
    Type getType(Long id);
    //通过名称查询type
    Type getTypeByName(String name);
    //分页查询
    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
    //修改type，先根据id查到对象，再修改
    Type updateType(Long id,Type type);
    //删除
    void deleteType(Long id);
}
