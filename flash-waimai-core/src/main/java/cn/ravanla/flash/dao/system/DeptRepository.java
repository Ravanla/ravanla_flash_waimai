package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.Dept;
import cn.ravanla.flash.dao.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created  on 2020/3/21 0021.
 *
 *@Author ravanla
 */
public interface DeptRepository  extends BaseRepository<Dept, Long> {
    List<Dept> findByPidsLike(String pid);
    @Query(nativeQuery = true,value = "SELECT id, pid AS pId, simplename AS NAME, ( CASE WHEN (pId = 0 OR pId IS NULL) THEN 'true' ELSE 'false' END ) AS isOpen FROM t_sys_dept")
    List tree();

    List<Dept> findBySimplenameLikeOrFullnameLike(String name,String name2);
}
