package me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.backendStrategy;


import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DStringApi.MyStrUtil;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.util.ArrayList;
import java.util.List;


public class SpringBootBackendStrategy implements BackendStrategy {


    @Override
    public List<FileDefined> generateFiles(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        List<FileDefined> results = new ArrayList<>();
        FileDefined entityFile = generateEntity(underscroeTableName, underscroeColumns);
        FileDefined controllerFile = generateController(underscroeTableName);
        FileDefined serviceFile = generateService(underscroeTableName);
        FileDefined serviceImplFile = generateServiceImpl(underscroeTableName);
        results.add(entityFile);
        results.add(controllerFile);
        results.add(serviceFile);
        results.add(serviceImplFile);
        return results;
    }

    private FileDefined generateServiceImpl(String underscroeTableName) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + "ServiceImpl.java";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/backend_springboot_service_imp.template");
        // System.out.println(fileData);
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        // System.out.println(fileData);
        fileData = fileData.replaceAll("`tableNameHump`", littleHumpTableName);
        // System.out.println(fileData);
        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateService(String underscroeTableName) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + "Service.java";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT+ "template/backend_springboot_service.template");
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        fileData = fileData.replaceAll("`tableNameHump`", littleHumpTableName);
        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateController(String underscroeTableName) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + "Controller.java";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/backend_springboot_controller.template");
        // System.out.println(fileData);
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        fileData = fileData.replaceAll("`tableNameHump`", littleHumpTableName);
        result = new FileDefined(fileName, fileData);
        return result;
    }

    private FileDefined generateEntity(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + ".java";
        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/backend_springboot_entity.template");
        fileData = fileData.replaceAll("`table_name`", underscroeTableName);
        fileData = fileData.replaceAll("`TableNameBigHump`", bigHumpTableName);
        String temp = "";
        for (int i = 0; i < underscroeColumns.size(); i++) {
            ColumnDefined column = underscroeColumns.get(i);
            String underscroeColumnName = column.getColumnName();
            String columnSqlType = column.getColumnType();
            String columnJavaType = convertSqlTypeToJava(columnSqlType);
            String indexName = column.getIndexType();
            boolean nullable = column.isNullable();
            String extra = column.getExtra();
            temp += genernateClassMemberVar(underscroeColumnName, columnJavaType, indexName, nullable, columnSqlType, extra);

        }
        // System.out.println(temp);
        // System.out.println(fileData);
        // System.out.println(temp);
        fileData = fileData.replaceAll("`variables`", temp);
        temp = genernateClassMemberMethod(underscroeColumns, bigHumpTableName);
        fileData = fileData.replaceAll("`methods`", temp);

        result = new FileDefined(fileName, fileData);
        return result;
    }

    private String genernateClassMemberMethod(List<ColumnDefined> underscroeColumns, String bigHumpTableName) throws Exception {
        String result = "";
        for (int i = 0; i < underscroeColumns.size(); i++) {
            ColumnDefined column = underscroeColumns.get(i);
            String underscroeColumnName = column.getColumnName();
            String littleHumpColumnName = MyStrUtil.underscroeToLittleHump(underscroeColumnName);
            String bigHumpColumnName = MyStrUtil.littleHumpToBig(littleHumpColumnName);
            String columnSqlType = column.getColumnType();
            String columnJavaType = convertSqlTypeToJava(columnSqlType);
            String extra = column.getExtra();
            String indexType = column.getIndexType();
            if(indexType != null && indexType.equals("MUL")){
                String foreignType = getForeignType(extra);
                result += "\tpublic "+ bigHumpTableName +" set"+ bigHumpColumnName +"("+ foreignType +" "+ littleHumpColumnName +"){this."+ littleHumpColumnName +" = "+ littleHumpColumnName +";return this;}\n";
                result += "\tpublic "+ foreignType +" get"+ bigHumpColumnName +"(){return this."+ littleHumpColumnName +";}\n";
            }else{
                result += "\tpublic "+ bigHumpTableName +" set"+ bigHumpColumnName +"("+ columnJavaType +" "+ littleHumpColumnName +"){this."+ littleHumpColumnName +" = "+ littleHumpColumnName +";return this;}\n";
                result += "\tpublic "+ columnJavaType +" get"+ bigHumpColumnName +"(){return this."+ littleHumpColumnName +";}\n";
            }

        }
        return result;
    }

    private String genernateClassMemberVar(String underscroeColumnName, String columnJavaType, String indexName, boolean nullable, String columnDefinition, String extra) throws Exception {
        String result = "";
        String littleHumpColumnName = MyStrUtil.underscroeToLittleHump(underscroeColumnName);
        String bigHumpColumnName = MyStrUtil.littleHumpToBig(littleHumpColumnName);
        String nullableStr = nullable ? "true" : "false";
        if (indexName == null) {
            result +=    "\t@Column(name=\""+ underscroeColumnName +"\", nullable="+ nullableStr +", insertable=true, updatable=true, columnDefinition=\""+ columnDefinition +"\")\n\t" +
                    "private "+ columnJavaType +" "+ littleHumpColumnName +";\n";
        }else {
            switch (indexName) {
                case "PRI":
                    // id
                    result +=   "\t@Id\n\t" +
                                "@GeneratedValue(strategy= GenerationType.AUTO)\n\t" +
                                "// GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。\n\t" +
                                "private "+ columnJavaType + " " + littleHumpColumnName + ";\n";
                    break;
                case "UNI":
                    result +=   "\t@Column(name=\""+ underscroeColumnName +"\", unique=true, insertable=true, updatable=true)\n\t" +
                                "private "+ columnJavaType +" "+ littleHumpColumnName +";\n";
                    break;
                case "MUL":
                    String foreignType = getForeignType(extra);
                    result +=  "\t@ManyToOne\n\t@JoinColumn(name = \""+ underscroeColumnName +"\")\n\t" +
                                "private "+ foreignType + " "+ littleHumpColumnName +";\n";
                    break;
            }
        }
        return result;
    }

    private String getForeignType(String extra) throws Exception {
        String foreignTypeStr = extra.substring(0, extra.indexOf("("));
        String littleHump = MyStrUtil.underscroeToLittleHump(foreignTypeStr);
        String bigHump = MyStrUtil.littleHumpToBig(littleHump);
        return bigHump;
    }

    private String convertSqlTypeToJava(String sqlType) throws Exception {
        if (sqlType == null || sqlType.trim().isEmpty()) throw new Exception("列sql类型不合法，为空或者不存在");
        if(sqlType.contains("bit")){
            return "Boolean";
        }else if(sqlType.contains("tinyint")){
            return "Byte";
        }else if(sqlType.contains("bigint")){
            return "Long";
        }else if(sqlType.contains("int")){
            return "Integer";
        }else if(sqlType.contains("char")) {
            return "String";
        }else if(sqlType.contains("text")) {
            return "String";
        }else if(sqlType.contains("double")) {
            return "Double";
        }else if(sqlType.contains("binary")) {
            return "byte[]";
        }else if(sqlType.contains("datetime")) {
            return "Timestamp";
        }else if(sqlType.contains("date")) {
            return "Date";
        }else if(sqlType.contains("timestamp")) {
            return "Timestamp";
        }else if(sqlType.contains("time")) {
            return "Time";
        }else if(sqlType.contains("decimal")) {
            return "BigDecimal";
        }else {
            throw new Exception("后端未发现的类型"+ sqlType);
        }
        
    }
}
