package cn.ravanla.flash.bean.vo.node;

import cn.ravanla.flash.bean.entity.system.Dept;

import java.util.ArrayList;
import java.util.List;

/**
 * DeptNode
 *
 *@Author ravanla
 * @version 2020/9/15 0015
 */
public class DeptNode extends Dept {

    private List<DeptNode> children = new ArrayList<>(10);

    public List<DeptNode> getChildren() {
        return children;
    }

    public void setChildren(List<DeptNode> children) {
        this.children = children;
    }
}
