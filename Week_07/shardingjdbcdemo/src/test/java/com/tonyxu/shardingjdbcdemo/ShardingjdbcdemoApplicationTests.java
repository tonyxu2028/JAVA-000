package com.tonyxu.shardingjdbcdemo;

import com.tonyxu.shardingjdbcdemo.mapper.GoodsMapper;
import com.tonyxu.shardingjdbcdemo.pojo.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 2020/12/3.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    @Autowired
    GoodsMapper goodsMapper;

//    @Test
//    void addGoods() {                     // 分表操作验证
//        for (int i = 0; i < 10; i++){
//            Goods good = new Goods();
//            good.setGname("小米手机" + i);
//            good.setUserId(100L);
//            good.setGstatus("已发布");
//            goodsMapper.insert(good);
//        }
//    }

    @Test
    void addGoods02(){                      // 分库,分表操作验证
        Goods good = new Goods();
        good.setGname("华为手机");
        good.setUserId(100L);
        good.setGstatus("已发布");
        goodsMapper.insert(good);

        Goods good1 = new Goods();
        good1.setGname("华为手机");
        good1.setUserId(101L);
        good1.setGstatus("已发布");
        goodsMapper.insert(good1);
    }

}
