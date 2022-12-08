package com.aoaozhu.Notes.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private Integer status;
    private String geQ;
    private Date birthDate;

}
