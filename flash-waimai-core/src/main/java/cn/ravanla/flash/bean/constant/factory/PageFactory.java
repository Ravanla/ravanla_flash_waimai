package cn.ravanla.flash.bean.constant.factory;

import cn.ravanla.flash.bean.constant.state.Order;
import cn.ravanla.flash.utils.HttpKit;
import cn.ravanla.flash.utils.ToolUtil;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2021-04-05 22:25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        // 获取请求
        HttpServletRequest request = HttpKit.getRequest();

        // 获取每页多少条数据
        int limit = Integer.valueOf(request.getParameter("limit"));

        // 获取当前页码
        String pageNum = request.getParameter("page");

        // 每页的偏移量（本页当前有多少条）
        int offset = 0;

        // 如果当前页码不为空，则计算偏移量
        if (StringUtils.isNotEmpty(pageNum)) {
            offset = (Integer.valueOf(pageNum) - 1) * limit;
        } else {
        // 如果当前页码为空，则从请求参数中获取偏移量
            offset = Integer.valueOf(request.getParameter("offset"));
        }

        // 获取排序字段名称
        String sortName = request.getParameter("sort");

        // 获取升序或降序（asc或desc）
        String order = request.getParameter("order");

        // 如果排序字段名称为空，则返回Page对象
        if (ToolUtil.isEmpty(sortName)) {
            Page page = new Page<>((offset / limit + 1), limit);
            return page;
        } else {
            // 如果排序字段名称不为空，则返回带有排序字段名称的Page对象
            Page page = new Page<>((offset / limit + 1), limit, sortName);

            // 如果为升序，则设置排序方式为升序
            if (Order.ASC.getDes().equals(order)) {
                Sort sort = Sort.by(Sort.Direction.ASC, order);
                page.setSort(sort);
            } else {
                // 如果为降序，则设置排序方式为降序
                Sort sort = Sort.by(Sort.Direction.DESC, order);
                page.setSort(sort);
            }
            return page;
        }
    }
}
