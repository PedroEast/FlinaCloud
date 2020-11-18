package me.dongqinglin.zuul_gateway.ZConfig;


import org.hibernate.dialect.MySQL57Dialect;
import org.springframework.stereotype.Component;

@Component
public class MySQLConfig extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
