package cn.ravanla.flash.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

/**
 * 封装基础的dao接口
 *
 * @Author ravanla
 * @date ：Created in 2021/6/29 12:50
 */
@NoRepositoryBean

/*
* 当我们使用Spring Data JPA时，通常会定义一个继承于 JpaRepository 的接口来操作数据库表的数据。
* JpaRepository 提供了基本的 CRUD 操作方法，但在某些情况下，我们可能需要更高级的查询和操作，
* 例如分页、排序和条件查询。这时，我们可以继承 PagingAndSortingRepository 和 JpaSpecificationExecutor 接口，
* 以获取更多的查询方法。

* 这段代码中，BaseRepository 是一个自定义接口，
* 继承了三个接口：JpaRepository、PagingAndSortingRepository、JpaSpecificationExecutor。
* JpaRepository 是 Spring Data JPA 提供的基础 Repository，用于定义基本的 CRUD 操作，包括保存、更新、删除和查询等。
* PagingAndSortingRepository 扩展了 JpaRepository 接口，添加了分页和排序功能。
* JpaSpecificationExecutor 则提供了更加灵活的查询方式，可以基于各种不同的条件组合来查询数据。

* 通过继承这些接口，我们就可以在自定义的 Repository 中使用它们提供的各种方法来操作数据库表的数据了。
* 例如，我们可以使用 JpaRepository 提供的 save() 方法来保存实体对象，
* 使用 PagingAndSortingRepository 提供的 findAll(Pageable pageable) 方法来查询分页数据，
* 使用 JpaSpecificationExecutor 提供的 findAll(Specification<T> spec) 方法来执行基于条件的查询。
* */

// 这个对象的类型由 BaseRepository 接口的泛型 T 决定。
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>
        , PagingAndSortingRepository<T, ID>
        , JpaSpecificationExecutor<T> {
    /**
     * 根据原生sql语句查询数据列表
     * @param sql
     * @return
     */
    //  Object[] 是一行数据，包含了多个列。
    List<Object[]> queryBySql(String sql);

    /**
     * 根据原生sql查询对象列表
     * @param sql
     * @return
     */
    // T 是一个对象，代表一行数据。
    List<T> query(String sql);

    /**
     * 根据原生sql查询数组对象
     * @param sql
     * @return
     */
    Object getBySql(String sql);

    /**
     * 根据原生sql查询对象
     * @param sql
     * @return
     */
    T get(String sql);
    T getOne(ID id);

    /**
     * 执行sql
     * @param sql
     * @return
     */
    int execute(String sql);

    /**
     * 获取数据类型
     * @return
     */
    Class<T> getDataClass();
}
