package com.yyy.blog.web.admin;


import com.yyy.blog.po.Type;
import com.yyy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    //@PageableDefault 可以指定一些参数
    //进入types页面
    public String list(@PageableDefault(size = 10,sort ={"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page", typeService.listType(pageable));
        // System.out.println("Pageable");
        // for (Type type : typeService.listType(pageable).getContent()) {
        //     System.out.println(type);
        // }
        return "admin/types";
    }
    //点击新增
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }
    //编辑
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }
    //前端的form中有一个name对象，提交后会自动将name封装到type对象里面
    //types-input页，新增页的表单提交
    //BindingResult参数必须加在要校验的参数后面，必须挨着，否则没效果
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        //如果查询到了typeByName，则说明已经存在一个名称相同的type
        if (typeByName!=null){
            result.rejectValue("name", "nameError","不能重复添加相同分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t==null){
            //如果没保存成功
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            //如果保存成功
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    //编辑页面的提交，与新增页公用一个页面，但调用方法不同
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        //如果查询到了typeByName，则说明已经存在一个名称相同的type
        if (typeByName!=null){
            result.rejectValue("name", "nameError","不能重复添加相同分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t==null){
            //如果没保存成功
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            //如果保存成功
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
