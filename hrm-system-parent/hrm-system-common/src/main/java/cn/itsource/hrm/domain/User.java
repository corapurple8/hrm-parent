package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_user")
public class User {
    /**
     * 描述该表的主键 主键类型自动
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    //@TableField("username")属性名称和列名相同可以不写
    private String  username;
    private String password;
    /**
     * 该字段不在数据库表中
     * 告诉mybatisplus直接忽略掉
     */
    @TableField(exist = false)
    private List<String> hobbies = new ArrayList<>();
}
