package com.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Long id;
  private String name;
  private Integer age;
  private String email;

  //创建时间
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  //添加时间
  //@TableField(fill = FieldFill.UPDATE)
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
  @TableLogic
  private Integer deleted;
}
