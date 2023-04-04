package cn.ravanla.flash.dao;

import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQLStorageEngine;

/*
* MySQLDialect.java是一个Java类，它继承了Hibernate框架中的MySQL5Dialect类，
* 并且重写了getDefaultMySQLStorageEngine()方法。
* 该类的作用是为Hibernate框架提供MySQL数据库的方言（Dialect），
* 以便Hibernate可以正确地生成SQL语句和执行数据库操作。

具体来说，MySQLDialect.java类中重写的getDefaultMySQLStorageEngine()方法
* 用于设置Hibernate在使用MySQL数据库时默认的存储引擎，
* 这里设置为InnoDBStorageEngine.INSTANCE。
* 这样可以确保Hibernate生成的SQL语句和执行的数据库操作与使用InnoDB存储引擎相关的特性相兼容。
* */
public class MySQLDialect extends MySQL5Dialect {
    @Override
    protected MySQLStorageEngine getDefaultMySQLStorageEngine() {
        return InnoDBStorageEngine.INSTANCE;
    }
}
