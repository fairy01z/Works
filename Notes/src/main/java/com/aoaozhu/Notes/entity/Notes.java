package com.aoaozhu.Notes.entity;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Notes implements Serializable {
        private Long id;
        private String noteTitle;
        private String introduction;
        private String noteMes;
        private Date updateTime;
        private int sta;
        private long userId;
}
