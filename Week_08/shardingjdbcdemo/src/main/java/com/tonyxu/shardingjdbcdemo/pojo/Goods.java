package com.tonyxu.shardingjdbcdemo.pojo;

import lombok.Data;

/**
 * Created on 2020/12/3.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
//@Data注解是lombok的注解，简化实体类编写，自动生成get/set以及toString等方法
@Data
public class Goods {
    private Long gid;
    private String gname;
    private Long userId;
    private String gstatus;
}
