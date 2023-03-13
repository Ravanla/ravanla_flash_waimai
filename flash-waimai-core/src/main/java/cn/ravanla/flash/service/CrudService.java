package cn.ravanla.flash.service;

/**
 *
 * @Author ravanla
 * @date ：Created in 2021/6/29 22:31
 */
public interface CrudService <T, ID> extends
        InsertService<T, ID>,
        UpdateService<T,ID>,
        DeleteService<ID>,
        SelectService<T, ID> {
}
