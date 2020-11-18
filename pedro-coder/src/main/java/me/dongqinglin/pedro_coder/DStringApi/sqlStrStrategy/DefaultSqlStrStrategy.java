package me.dongqinglin.pedro_coder.DStringApi.sqlStrStrategy;


import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DStringApi.MyStrUtil;

import java.util.ArrayList;
import java.util.List;

public class DefaultSqlStrStrategy implements SqlStrStrategy {
    @Override
    public String getUnderscroeTableName(String createTableStr) throws Exception {
        if (createTableStr == null || createTableStr.trim().equals("")) throw new Exception("建表语句不能为空");
        int leftRoundBracketIndex = createTableStr.indexOf("(");
        if (leftRoundBracketIndex == -1) throw new Exception("建表语句不合法，左圆括号`(`");
        String tempStr = createTableStr.substring(0, leftRoundBracketIndex).trim();
        int lastBlankIndex = tempStr.lastIndexOf(" ");
        if(lastBlankIndex == -1) throw new Exception("建表语句不合法，没有空格分隔`");
        String result = tempStr.substring(lastBlankIndex + 1);
        // 默认去除反引号
        result = MyStrUtil.removeTheQuotes(result);
        System.out.println(result);
        return result;
    }

    @Override
    public List<ColumnDefined> getUnderscroeColumns(String createTableStr) throws Exception {
        if (createTableStr == null || createTableStr.trim().equals("")) throw new Exception("建表语句不能为空");
        int firstLeftRoundBracketIndex = createTableStr.indexOf("(");
        int lastRightRoundBracketIndex = createTableStr.lastIndexOf(")");
        if (firstLeftRoundBracketIndex == -1 || lastRightRoundBracketIndex == -1 ) throw new Exception("建表语句不合法，没有左圆括号`(`或右圆括号`)`");
        // Map<String, String> resultMap = new HashMap<>();
        List<ColumnDefined> results = new ArrayList<>();
        String[] tempStrArr = getDefinedColumns(createTableStr, firstLeftRoundBracketIndex, lastRightRoundBracketIndex);
        String tempStr;
        ColumnDefined columnDefined;
        for (int i = 0; i < tempStrArr.length; i++) {
            // 排除索引的干扰，index， key
            tempStr = tempStrArr[i].trim();
            // System.out.println("1"+tempStr);
            if(tempStr.trim().equals("")) continue;
            if (tempStr.contains("index ") || tempStr.contains(" key ")) continue;
            // String[] columnDefineArray = tempStr.split(" ");
            // String columnName = columnDefineArray[0];
            // columnName = MyStrUtil.removeTheQuotes(columnName);
            // String sqlType = columnDefineArray[1];
            String columnName = getColumnDefinedColumnNameFromStr(tempStr);
            String columnType = getColumnDefinedColmunTypeFromStr(tempStr);
            String columnDefinition = getColumnDefinedColmunTypeFromStr(tempStr);
            boolean nullable = getColumnDefinedNullableFromStr(tempStr);
            String indexType = getColumnDefinedIndexTypeFromStr(columnName, tempStrArr);
            // System.out.println(columnName + indexType);
            String extra = getColumnDefinedExtraFromStr(columnName, tempStrArr);
            columnDefined = new ColumnDefined();
            columnDefined.setColumnName(columnName).setColumnType(columnType).setNullable(nullable).setIndexType(indexType).setExtra(extra);
            results.add(columnDefined);
            // resultMap.put(columnName, sqlType);
        }
        // System.out.println(resultMap);
        System.out.println(results);
        return results;
    }

    private String getColumnDefinedExtraFromStr(String columnName, String[] tempStrArr) {
        String extra = null;
        String tempStr;
        for (int i = 0; i < tempStrArr.length; i++) {
            tempStr = tempStrArr[i].trim();
            if (tempStr.contains("index ") || tempStr.contains(" key ")) {
                int firstLeftRoundBracketIndex = tempStr.indexOf("(");
                int lastRightRoundBracketIndex = tempStr.substring(firstLeftRoundBracketIndex).indexOf(")") + firstLeftRoundBracketIndex;
                String tempColumnName = tempStr.substring(firstLeftRoundBracketIndex + 1, lastRightRoundBracketIndex);
                tempColumnName = MyStrUtil.removeTheQuotes(tempColumnName);
                if (columnName.equals(tempColumnName)) {
                    if (tempStr.contains("foreign")) {
                        if(tempStr.endsWith(",")) tempStr = tempStr.substring(0, tempStr.length() - 1);
                        String[] foreignArr = tempStr.split(" ");
                        extra = foreignArr[foreignArr.length-1];
                        // System.out.println(" 1"+ columnName +": "+ tempStr + " "+ extra);
                    }
                    break;
                }
            }
        }
        return extra;
    }


    private String getColumnDefinedIndexTypeFromStr(String columnName, String[] tempStrArr) {
        String indexType = null;
        String tempStr;
        for (int i = 0; i < tempStrArr.length; i++) {
            tempStr = tempStrArr[i].trim();
            if (tempStr.contains("index ") || tempStr.contains(" key ")) {
                int firstLeftRoundBracketIndex = tempStr.indexOf("(");
                int lastRightRoundBracketIndex = tempStr.substring(firstLeftRoundBracketIndex).indexOf(")") + firstLeftRoundBracketIndex;
                String tempColumnName = tempStr.substring(firstLeftRoundBracketIndex + 1, lastRightRoundBracketIndex);
                tempColumnName = MyStrUtil.removeTheQuotes(tempColumnName);
                // System.out.print(tempColumnName +": "+ columnName + ";");
                if (columnName.equals(tempColumnName)) {
                    // System.out.println(columnName +": "+ tempStr);
                    if (tempStr.contains("primary")) {
                        indexType = "PRI";
                    } else if (tempStr.contains("foreign")) {
                        indexType = "MUL";
                    } else if (tempStr.contains("unique")) {
                        indexType = "UNI";
                    }
                    break;
                }
            }
        }
        return indexType;
    }

    private boolean getColumnDefinedNullableFromStr(String tempStr) {
        int notNUllIndex = tempStr.indexOf("not null");
        // 没有找到not null，就认为可以为null，即nullable = true
        boolean nullable = notNUllIndex == -1 ? true : false;
        return nullable;
    }

    private String getColumnDefinedColmunTypeFromStr(String tempStr) {
        // System.out.println(tempStr);
        String columnType = "";
        String[] columnDefineArray = tempStr.split(" ");
        if(columnDefineArray.length > 2){
            columnType = columnDefineArray[1];
        }
        return columnType;

    }



    private String getColumnDefinedColumnNameFromStr(String tempStr) {
        String[] columnDefineArray = tempStr.split(" ");
        String columnName = columnDefineArray[0];
        columnName = MyStrUtil.removeTheQuotes(columnName);
        return columnName;
    }

    private String[] getDefinedColumns(String createTableStr, int firstLeftRoundBracketIndex, int lastRightRoundBracketIndex) {
        // 左圆括号位置加一是因为，substring方法包左不包右
        String tempStr = createTableStr.substring(firstLeftRoundBracketIndex + 1, lastRightRoundBracketIndex);
        // System.out.println(tempStr);
        // 默认建表语句为小写 而且过滤所有制表符和换行符
        // tempStr = tempStr.replaceAll("\t", "").replaceAll("\n", "").toLowerCase();
        // 过滤所有的连续空字符
        tempStr = tempStr.replaceAll("\\s+", " ").toLowerCase();
        // System.out.println(tempStr);
        return tempStr.split(",");
    }

   /* @Override
    public Map<String, String> getUnderscoreIndexs(String createTableStr) throws Exception {
        if (createTableStr == null || createTableStr.trim().equals("")) throw new Exception("建表语句不能为空");
        int firstLeftRoundBracketIndex = createTableStr.indexOf("(");
        int lastRightRoundBracketIndex = createTableStr.lastIndexOf(")");
        if (firstLeftRoundBracketIndex == -1 || lastRightRoundBracketIndex == -1 ) throw new Exception("建表语句不合法，没有左圆括号`(`或右圆括号`)`");
        // 左圆括号位置加一是因为，substring方法包左不包右
        Map<String, String> resultMap = new HashMap<>();
        String[] tempStrArr = getDefinedColumns(createTableStr, firstLeftRoundBracketIndex, lastRightRoundBracketIndex);
        String tempStr;
        for (int i = 0; i < tempStrArr.length; i++) {
            // 只处理索引，index， key
            tempStr = tempStrArr[i].trim();
            if (tempStr.contains("index ") || tempStr.contains(" key ")) {
                firstLeftRoundBracketIndex = tempStr.indexOf("(");
                lastRightRoundBracketIndex = tempStr.lastIndexOf(")");
                String columnName = tempStr.substring(firstLeftRoundBracketIndex + 1, lastRightRoundBracketIndex);
                columnName = MyStrUtil.removeTheQuotes(columnName);
                String indexType = "";
                if (tempStr.contains("primary")) {
                    indexType = "PRI";
                } else if (tempStr.contains("foreign")) {
                    indexType = "MUL";
                } else if (tempStr.contains("unique")) {
                    indexType = "UNI";
                }
                resultMap.put(columnName, indexType);
            }
        }
        System.out.println(resultMap);
        return resultMap;
    }*/
}
