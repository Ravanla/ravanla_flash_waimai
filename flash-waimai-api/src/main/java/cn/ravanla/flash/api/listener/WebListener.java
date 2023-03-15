package cn.ravanla.flash.api.listener;

import cn.ravanla.flash.bean.entity.front.*;
import cn.ravanla.flash.bean.entity.front.*;
import cn.ravanla.flash.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author ravanla
 * @date ：Created in 2021/10/25 11:24
 */
/*
*
* 用于初始化MongoDB数据库，根据@Value注解得到的init变量的值，如果init变量值为true，
* 则执行清空Shop.class、Food.class、Menu.class、Address.class、Ratings.class、Order.class、Carts.class、sesions、users、userinfos
* 等MongoDB表的操作。
* */
@Component
public class WebListener implements CommandLineRunner {
    @Autowired
    private MongoRepository mongoRepository;
    @Value("${flash.waimai.mongodb.init}")
    private Boolean init;

    /**
     * 初始化mongodb 数据
     */
    public  void initMongoData(){

        if(init) {
            //删除全部mongodb测试数据
            mongoRepository.clear(Shop.class);
            mongoRepository.clear(Food.class);
            mongoRepository.clear(Menu.class);
            mongoRepository.clear(Address.class);
            mongoRepository.clear(Ratings.class);
            mongoRepository.clear(Order.class);
            mongoRepository.clear(Carts.class);
            mongoRepository.clear("sesions");
            mongoRepository.clear("users");
            mongoRepository.clear("userinfos");
        }
    }
    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initMongoData();
            }
        });
        thread.start();
    }
}
