package me.dongqinglin.pedro_coder.DDaoApi;

import me.dongqinglin.pedro_coder.DDaoApi.TableDao;

import me.dongqinglin.pedro_coder.ZBean.ColumnDefined;
import me.dongqinglin.pedro_coder.ZBean.DatabaseDefindProd;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TableDaoImpl implements TableDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DatabaseDefindProd> getTables() {
        List resultList;
        String showTableSql = "show tables;";
        Query query = entityManager.createNativeQuery(showTableSql);
        query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(DatabaseDefindProd.class));
        resultList = query.getResultList();
        // System.out.println(resultList);
        return resultList;
    }

    @Override
    public List<ColumnDefined> getColumns(String tableName) {
        List<ColumnDefined> resultList;
        String showTableColumnsSql = "show columns from " + tableName + ";";
        Query query = entityManager.createNativeQuery(showTableColumnsSql);

        query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(ColumnDefined.class));
        resultList = query.getResultList();
        // System.out.println(resultList);
        return resultList;
    }

    @Override
    public List getCreateTableStr(String tableName) {
        String showTableColumnsSql = "show create table " + tableName + ";";
        Query query = entityManager.createNativeQuery(showTableColumnsSql);

        List resultList = query.getResultList();
        // System.out.println(resultList);
        return resultList;
    }

    @Override
    public List findAll(String tableName) {

        String showTableColumnsSql = "select * from " + tableName + ";";
        Query query = entityManager.createNativeQuery(showTableColumnsSql);

        List resultList = query.getResultList();
        return resultList;
    }

    @Override
    @Transactional
    public void drop(String tableName) {
        try {
            String dropTableSql = "drop table " + tableName + ";";
            System.out.println(dropTableSql);
            Query query = entityManager.createNativeQuery(dropTableSql);

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}