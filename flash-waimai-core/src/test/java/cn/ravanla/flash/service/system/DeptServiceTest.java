package cn.ravanla.flash.service.system;

import cn.ravanla.flash.service.BaseApplicationStartTest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on 2020/4/5 0005.
 *
 *@Author ravanla
 */
public class DeptServiceTest  extends BaseApplicationStartTest {
    @Autowired
    private DeptService deptService;
    @Test
    public void tree() throws Exception {
        System.out.println(JSON.toJSON(deptService.tree()));
    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void deleteDept() throws Exception {

    }
}