package me.dongqinglin.pedro_coder.BController;

import me.dongqinglin.pedro_coder.ZBean.ColumnDefined;
import me.dongqinglin.pedro_coder.ZBean.DatabaseDefindProd;
import me.dongqinglin.pedro_coder.ZBean.DatabaseDefinedDev;
import me.dongqinglin.pedro_coder.ZBean.TableDefined;
import me.dongqinglin.pedro_coder.CService.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("database")
public class DatabaseController {
    @Autowired
    private TableService tableService;

    @GetMapping("onGetTableList")
    public ResponseEntity<?> onGetTable(){
        List<DatabaseDefinedDev> result;
        result = tableService.getTables();
        return ResponseEntity.ok(result);
    }


    @GetMapping("onGetTableDefinedList")
    public ResponseEntity<?> onGetTableDefined(){
        List<TableDefined> result = new ArrayList<>();
        List<DatabaseDefindProd> tableNames = tableService.getTables();
        // System.out.println(tableNames);
        for(DatabaseDefindProd tableName : tableNames){
            String tableNameStr = tableName.getTables_in_flina();
            List<ColumnDefined> columns = tableService.getColumns(tableNameStr);
            List createTable = tableService.getCreateTableStr(tableNameStr);
            TableDefined newTable = new TableDefined(tableNameStr, columns, createTable);
            result.add(newTable);
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("onGetColumn")
    public ResponseEntity<?> onGetColumn(@RequestParam String tableName){
        List result;
        result = tableService.getColumns(tableName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("OnGetCreateTableStr")
    public ResponseEntity<?> OnGetCreateTableStr(@RequestParam String tableName){
        List result;
        result = tableService.getCreateTableStr(tableName);
        return ResponseEntity.ok(result);
    }



    @GetMapping("OnGetData")
    public ResponseEntity<?> OnGetData(@RequestParam String tableName){
        List result;
        result = tableService.findAll(tableName);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("OnDeleteTable")
    public ResponseEntity<?> OnDeleteTable(@RequestParam String tableName){
        List result;
        tableService.drop(tableName);
        result = new ArrayList();
        result.add("success");
        return ResponseEntity.ok(result);
    }
}
