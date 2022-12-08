package com.aoaozhu.Notes.service.impl;


import com.aoaozhu.Notes.entity.Notes;

import com.aoaozhu.Notes.mapper.NotesMapper;
import com.aoaozhu.Notes.service.NotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements NotesService {

}
