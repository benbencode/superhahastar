package com.superhahastar.benben.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;

public class FileUtils {


    /**
     * 移动文件
     * @param srcFilePath 源文件
     * @param targetFilePath 目标路径
     */
    public static void moveFile(String srcFilePath, String targetFilePath) {
        try {
            int i = targetFilePath.lastIndexOf("\\");
            File srcfile = new File(srcFilePath);
            File targetfile = new File(targetFilePath.substring(0,i));

            if (srcfile.exists()&&targetfile.exists()){
                Files.move(Paths.get(srcFilePath), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 向文件写数据
     * @param data 数据
     * @param local_path 本地文件路径
     */
    public static void writeData(String data, String local_path){
        FileWriter fw ;
        try {
            fw = new FileWriter(local_path,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data+"\r\n");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()){
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 创建json文件
     * @param jsonData
     * @param filePath
     * @return
     */
    public static boolean createJsonFile(Map<String, String> tableAllColumns,Object jsonData, String filePath) {


        JSONArray array = (JSONArray)jsonData;
        for (int i = 0; i < array.size(); i++) {
            JSONObject data = array.getJSONObject(i);
            Set<String> keySet = data.keySet();
            for (String key:keySet) {
                Object v = data.get(key);
                if ( v instanceof JSONArray){
                    if ("VARCHAR(65533)".equalsIgnoreCase(tableAllColumns.get(key))){
                        data.replace(key,v,((JSONArray) v).toString());
                        array.remove(i);
                        array.add(i,data);
                    }
                }
            }
        }

        String content = JSON.toJSONString(array, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        boolean flag = true;
        // 生成json格式文件
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        moveFile("E:\\tmp\\kafkadata_SDFD1.txt","E:\\sdb\\kafkadata_SDFD1.txt");

        System.out.println("name".lastIndexOf("e"));
    }
}
