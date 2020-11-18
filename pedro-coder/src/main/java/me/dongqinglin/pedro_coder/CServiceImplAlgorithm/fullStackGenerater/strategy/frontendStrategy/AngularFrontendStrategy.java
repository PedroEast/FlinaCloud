package me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.frontendStrategy;


import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DStringApi.MyStrUtil;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.util.ArrayList;
import java.util.List;


public class AngularFrontendStrategy implements FrontendStrategy {

    @Override
    public List<FileDefined> generateFiles(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        List<FileDefined> results = new ArrayList<>();
        FileDefined entityFile = generateEntity(underscroeTableName, underscroeColumns);
        FileDefined serviceFile = generateService(underscroeTableName, underscroeColumns);
        FileDefined componentTsFile = generateComponentTs(underscroeTableName, underscroeColumns);
        FileDefined componentHtmlFile = generateComponentHtml(underscroeTableName, underscroeColumns);
        FileDefined componentScssFile = generateComponentScss(underscroeTableName, underscroeColumns);
        results.add(entityFile);
        results.add(serviceFile);
        results.add(componentHtmlFile);
        results.add(componentScssFile);
        results.add(componentTsFile);
        return results;
    }

    private FileDefined generateComponentScss(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String linkedTableName = MyStrUtil.underscroeToLinked(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = linkedTableName + ".component.scss";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/frontend_angular_component_scss.template");
        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateComponentHtml(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String linkedTableName = MyStrUtil.underscroeToLinked(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = linkedTableName + ".component.html";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/frontend_angular_component_html.template");

        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateComponentTs(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String linkedTableName = MyStrUtil.underscroeToLinked(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String selector = "\"app-"+ linkedTableName +"\"";
        String templateUrl = "\"./" + linkedTableName + ".component.html\"";
        String styleUrls = "[\"./"+ linkedTableName +".component.scss \"]";
        String caseDate = getCaseDate(generateEntity(underscroeTableName, underscroeColumns));
        String fileName = linkedTableName + ".component.ts";
        String fileData = "";

        fileData = fileSystem.view(STATIC_ROOT + "template/frontend_angular_component_ts.template");
        fileData = fileData.replaceAll("`selector`", selector);
        fileData = fileData.replaceAll("`templateUrl`", templateUrl);
        fileData = fileData.replaceAll("`styleUrls`", styleUrls);
        fileData = fileData.replaceAll("`caseDate`", caseDate);
        fileData = fileData.replaceAll("`tablename_link`", linkedTableName);
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);

        result = new FileDefined(fileName, fileData);
        return result;
    }


    private FileDefined generateService(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + ".service.ts";
        String fileData = "";

        fileData = fileSystem.view(STATIC_ROOT + "template/frontend_angular_service.template");

        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        fileData = fileData.replaceAll("`tableNameHump`", littleHumpTableName);
        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateEntity(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + ".ts";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/frontend_angular_entity.template");
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        String temp = "";
        for (int i = 0; i < underscroeColumns.size(); i++) {
            String columnName = underscroeColumns.get(i).getColumnName();
            columnName = MyStrUtil.underscroeToLittleHump(columnName);
            String columnSqlType = underscroeColumns.get(i).getColumnType();
            String columnTsType = convertSqlTypeToTs(columnSqlType);
            temp += "\t" + columnName + ": " + columnTsType + ";\n";
        }
        fileData = fileData.replaceAll("`variables`", temp);
        result = new FileDefined(fileName, fileData);
        return result;
    }
    private String getCaseDate(FileDefined generateEntity) throws Exception {
        if (generateEntity == null ) throw new Exception("getCaseDate参数非法");
        String result = "";
        String entityDataStr = generateEntity.getFileData();
        String[] lines = entityDataStr.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if(line.contains("Date")){
                line = line.trim();
                line = line.replaceAll(": Date;", "");
                line = "\tcase \"" + line + "\":\n";
                result += line;
            }
        }
        return result;
    }


    private String convertSqlTypeToTs(String sqlType) throws Exception {
        if(sqlType.contains("bit")){
            return "boolean";
        }else if(sqlType.contains("tinyint")){
            return "number";
        }else if(sqlType.contains("bigint")){
            return "number";
        }else if(sqlType.contains("int")){
            return "number";
        }else if(sqlType.contains("char")) {
            return "string";
        }else if(sqlType.contains("text")) {
            return "string";
        }else if(sqlType.contains("double")) {
            return "number";
        }else if(sqlType.contains("binary")) {
            return "number[]";
        }else if(sqlType.contains("datetime")) {
            return "Date";
        }else if(sqlType.contains("date")) {
            return "Date";
        }else if(sqlType.contains("timestamp")) {
            return "Date";
        }else if(sqlType.contains("time")) {
            return "Date";
        }else if(sqlType.contains("decimal")) {
            return "number";
        }else {
            throw new Exception("未知的类型");
        }
        
    }
}
