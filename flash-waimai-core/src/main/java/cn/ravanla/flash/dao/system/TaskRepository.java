
package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.Task;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.List;

public interface TaskRepository extends BaseRepository<Task,Long> {

    long countByNameLike(String name);

    List<Task> findByNameLike(String name);
    List<Task> findAllByDisabled(boolean disable);
}
