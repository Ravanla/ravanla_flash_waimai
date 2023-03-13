package cn.ravanla.flash.dao.system;

import cn.ravanla.flash.bean.entity.front.Restaurant;
import cn.ravanla.flash.core.utils.factory.Page;
import cn.ravanla.flash.dao.BaseRepository;
import cn.ravanla.flash.dao.MongoRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.awt.print.Pageable;

//@Repository
//public interface RestaurantRepository extends BaseRepository<Restaurant, String> {
////    Page<Restaurant> findByLocationNearAndNameRegex(Point location, String name, Pageable pageable);
////    List restaurants = restaurantRepository.query(name, location, distance, pageable);
////    Page<Restaurant> restaurantPage = new PageImpl<>(restaurants, pageable, restaurants.size());
////    restaurantPage.getTotalElements(); // 此处调用 getTotalElements 方法不会再报错了
//
//}
