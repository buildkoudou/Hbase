/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HbaseData;

import HbaseData.FileUtilClass.Writelog;
import static HbaseData.FileUtilClass.Writelog.writeLog;

/**
 *
 * @author zhangbingxue
 */
public class TimeTestmain {

    static String tableName = "earthauakedate";

    public static void main(String[] are) throws Exception {
        long start, end;
        Hbasewrite hw = new Hbasewrite();
        String path = "F:\\固态\\TestData";
        String logpath = "F:\\固态\\";

        start = System.nanoTime();
        hw.sendhbase(path,1);
        end = System.nanoTime();
        System.out.println("行键为台站测点测项采样率列为时间的写入时间: " + (end - start) / 1.0e9);
       Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率列为时间的写入时间: " + (end - start) / 1.0e9);
        start = System.nanoTime();
        hw.sendhbase(path,2);
        end = System.nanoTime();
        System.out.println("行键为台站测点测项采样率时间的写入时间: " + (end - start) / 1.0e9);
         Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率时间的写入时间: " + (end - start) / 1.0e9);
         
           HBaseUtil HB = new HBaseUtil();
            System.out.println("读取百万数据: " );
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "读取百万数据");


Querymsg da = new Querymsg();
        da.setStation("53001");
        da.setStarttime("201001010000");
        da.setEndtime("201111310000");
        da.setItem("4112");
        da.setStationpoint("2");
        da.setZoom("1");
        int i = 0;  
        StringBuilder result;
        while (i < 20) {
            start = System.nanoTime();
            result = hbaseread.query(da);
            end = System.nanoTime();
         
            System.out.println("行键为台站测点测项采样率列为时间的读取时间: " + (end - start) / 1.0e9);
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率列为时间的读取时间: " + (end - start) / 1.0e9);
            i++;
        }
        i=1;
           while (i < 20) {
        start = System.nanoTime();
       result = hbaseread.query1(da);
        end = System.nanoTime();
    
        System.out.println("行键为台站测点测项采样率时间的读取时间: " + (end - start) / 1.0e9);
        Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率时间的读取时间: " + (end - start) / 1.0e9);
             i++;
           }
   
    
    
    
    System.out.println("读取万数据: " );
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "读取万数据");



        da.setStation("53001");
        da.setStarttime("201001010000");
        da.setEndtime("201001290000");
        da.setItem("4112");
        da.setStationpoint("2");
        da.setZoom("1");
        i = 0;  
       
        while (i < 20) {
            start = System.nanoTime();
            result = hbaseread.query(da);
            end = System.nanoTime();
            result.length();
            System.out.println("行键为台站测点测项采样率列为时间的读取时间: " + (end - start) / 1.0e9);
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率列为时间的读取时间: " + (end - start) / 1.0e9);
            i++;
        }
        i=1;
           while (i < 20) {
        start = System.nanoTime();
        result = hbaseread.query1(da);
        end = System.nanoTime();
        result.length();
        System.out.println("行键为台站测点测项采样率时间的读取时间: " + (end - start) / 1.0e9);
        Writelog.writeLog(logpath, "writeandreadhbaselog.log", "行键为台站测点测项采样率时间的读取时间: " + (end - start) / 1.0e9);
             i++;
           }
    }
}


