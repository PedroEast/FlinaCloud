package me.dongqinglin.pedro_coder.DStringApi;

public class MyStrUtil {
    public static String removeTheQuotes(String str) {
        if(str.contains("`")) str = str.replaceAll("`", "");
        if(str.contains("'")) str = str.replaceAll("`", "");
        if(str.contains("\"")) str = str.replaceAll("\"", "");
        return str;
    }

    public static String underscroeToLittleHump(String underscroeStr) throws Exception {
        if (underscroeStr == null || underscroeStr.trim().isEmpty()) throw new Exception("参数下划线风格字符串不合法，为空值或不存在");
        if (!underscroeStr.trim().contains("_")) return underscroeStr;
        String result = "";
        while (underscroeStr.contains("_")) {
            int underScoreIndex = underscroeStr.indexOf("_");
            result += underscroeStr.substring(0, underScoreIndex);
            char littleLetter = underscroeStr.charAt(underScoreIndex + 1);
            underscroeStr = MyStrUtil.littleLetterToBig(littleLetter) + underscroeStr.substring(underScoreIndex + 2);
        }
        result += underscroeStr;
        return result;
    }

    public static String littleHumpToBig(String littleHumpStr) throws Exception {
        if (littleHumpStr == null || littleHumpStr.trim().isEmpty()) throw new Exception("参数小驼峰字符串不合法");
        char firstLetter = littleHumpStr.charAt(0);
        firstLetter = MyStrUtil.littleLetterToBig(firstLetter);
        String result = firstLetter + littleHumpStr.substring(1);
        return result;
    }

    private static char littleLetterToBig(char littleLetter) throws Exception {
        if( littleLetter < 'a' || littleLetter > 'z') throw new Exception("参数小写字母不合法");
        int sub = ('A'- 'a');
        char bigLetter = ((char) (littleLetter + sub));
        return bigLetter;
    }

    public static String underscroeToLinked(String underscroeStr) {
        return underscroeStr.replaceAll("_", "-");
    }
}
