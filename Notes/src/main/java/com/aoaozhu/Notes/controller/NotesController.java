package com.aoaozhu.Notes.controller;
import com.aoaozhu.Notes.common.R;
import com.aoaozhu.Notes.entity.Notes;
import com.aoaozhu.Notes.service.NotesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Slf4j
@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    //分页查询
    @GetMapping("/page")
    public  R<Page> page(int page, int pageSize, String name,Long id) {
        log.info("page={},pageSize={},name={},id={}",page, pageSize,name,id);
        //构造分页条件
        Page pageInfo = new Page(page, pageSize);

        //构造条件
        LambdaQueryWrapper<Notes> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.eq(Notes::getUserId,id);

        if(name!=null){
        queryWrapper.like(Notes::getNoteTitle, name);
        }
        //排序
        queryWrapper.orderByDesc(Notes::getUpdateTime);

        //执行查询
        notesService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    //获取笔记具体内容
    @GetMapping("/{id}")
    public R<Notes> getDetails(@PathVariable Long id){
        log.info("根据id查询笔记信息。。。");
        Notes notes=notesService.getById(id);
        System.out.println(id);
        if(notes!=null){
            return R.success(notes);}
        return R.error("no found");
    }
    //根据id修改内容
    @PostMapping("/edit/{id}")
    public R<String> update(HttpServletRequest request,@RequestBody Notes notes,@PathVariable Long id){
        log.info(notes.toString());
        notesService.updateById(notes);
        return R.success("信息修改成功");
    }

    //新添笔记
    @PutMapping("/addNote")
    public R<String> addNote(@RequestBody Notes notes){
        log.info("新增笔记，信息：{}",notes.toString());
        LambdaQueryWrapper<Notes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Notes::getId).last("limit 1");
        Notes n=notesService.getOne(queryWrapper);
        Long id=n.getId();
        //System.out.println("**********"+id);
        int ID=id.intValue();
        ID++;
        //System.out.println("**********"+ID);
        Long noteID=(long)ID;
        //设置笔记id
        notes.setId(noteID);
        //设置时间
        LocalDateTime localDateTime=LocalDateTime.now();
        java.util.Date date= Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        notes.setUpdateTime(date);
        //获得当前用户id
        //notes.setUserId(userId);
        notes.setSta(1);
        notesService.save(notes);
        return R.success("新增笔记成功");
    }

    //删除笔记
    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable Long id){
        log.info("删除笔记，信息：{}");
        //设置时间
        notesService.removeById(id);
        return R.success("删除成功");
    }
}
