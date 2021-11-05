package com.yyy.blog.web.admin;

import com.yyy.blog.po.Tag;
import com.yyy.blog.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    //@PageableDefault 可以指定一些参数
    //进入tags页面
    public String list(@PageableDefault(size = 10,sort ={"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        // System.out.println("Pageable");
        // for (Tag tag : typeService.listType(pageable).getContent()) {
        //     System.out.println(tag);
        // }
        return "admin/tags";
    }
    //点击新增
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }
    //编辑
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getType(id));
        return "admin/tags-input";
    }
    //前端的form中有一个name对象，提交后会自动将name封装到tag对象里面
    //tags-input页，新增页的表单提交
    //BindingResult参数必须加在要校验的参数后面，必须挨着，否则没效果
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag typeByName = tagService.getTagByName(tag.getName());
        //如果查询到了typeByName，则说明已经存在一个名称相同的type
        if (typeByName!=null){
            result.rejectValue("name", "nameError","不能重复添加相同标签");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveType(tag);
        if (t==null){
            //如果没保存成功
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            //如果保存成功
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    //编辑页面的提交，与新增页公用一个页面，但调用方法不同
    @PostMapping("/tags/{id}")
    public String editpost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Tag typeByName = tagService.getTagByName(tag.getName());
        //如果查询到了tagByName，则说明已经存在一个名称相同的tag
        if (typeByName!=null){
            result.rejectValue("name", "nameError","不能重复添加相同标签");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t==null){
            //如果没保存成功
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            //如果保存成功
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
