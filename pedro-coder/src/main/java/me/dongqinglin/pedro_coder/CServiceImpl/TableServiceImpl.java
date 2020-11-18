package me.dongqinglin.pedro_coder.CServiceImpl;

import me.dongqinglin.pedro_coder.DDaoApi.TableDao;
import me.dongqinglin.pedro_coder.ZBean.DatabaseDefinedDev;
import me.dongqinglin.pedro_coder.CService.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private TableDao tableDao;

    @Override
    public List<DatabaseDefinedDev> getTables() {
        return tableDao.getTables();
    }

    @Override
    public List getColumns(String tableName) {
        return tableDao.getColumns(tableName);
    }

    @Override
    public List getCreateTableStr(String tableName) {
        return tableDao.getCreateTableStr(tableName);
    }

    @Override
    public List findAll(String tableName) {
        return tableDao.findAll(tableName);
    }

    @Override
    public void drop(String tableName) {
        tableDao.drop(tableName);
    }
}
