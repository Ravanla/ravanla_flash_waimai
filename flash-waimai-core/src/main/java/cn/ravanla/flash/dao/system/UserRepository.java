package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.User;
import cn.ravanla.flash.dao.BaseRepository;

/**
 * Created  on 2020/3/21 0021.
 *
 *@Author ravanla
 */
public interface UserRepository extends BaseRepository<User,Long> {
    User findByAccount(String account);

    User findByAccountAndStatusNot(String account, Integer status);
}
