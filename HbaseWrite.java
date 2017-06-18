package HbaseData;

import HbaseData.FileUtilClass.FileSize;
import HbaseData.FileUtilClass.Writelog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseWrite {

    long totalsize = 0;
    long updatasize = 0;
    String logpath ="F:\\固态\\";

    public void sendhbase(String path, int x) throws IOException {
        File file = new File(path);
        FileSize filesize = new FileSize();
        if (file.isFile()) {
            try {
                if (Post(file, x)) {
                    System.out.println("文件" + file.getPath() + "上传成功");
                    Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件" + file.getPath() + "上传成功");
                } else {
                    System.out.println("文件" + file.getPath() + "上传失败");
                    Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件" + file.getPath() + "上传失败");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            totalsize = filesize.getfilesize(path);
            System.out.println("文件目录:" + path);
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件" + "文件目录:" + path);
            File[] fd = file.listFiles();
            System.out.println("文件总个数" + fd.length);
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件总个数" + fd.length);
            System.out.println("文件总大小" + totalsize);
            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件总大小" + totalsize);
            for (File fd1 : fd) {
                System.out.println("已上传" + updatasize / 1024 + "总大小" + totalsize / 1024 + "已上传比例" + updatasize/(totalsize/100)+"%");
                if (fd1.isFile()) {
                    try {
                        if (Post(fd1, x)) {
                            updatasize += fd1.length();
                            System.out.println("文件" + fd1.getPath() + "上传成功");
                            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件" + fd1.getPath() + "上传成功");
                        } else {
                            System.out.println("文件" + fd1.getPath() + "上传失败");
                            Writelog.writeLog(logpath, "writeandreadhbaselog.log", "文件" + fd1.getPath() + "上传失败");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    sendhbase(fd1.getPath(), x);
                }
            }
            
            HBaseUtil.close();
            updatasize = 0;
        }

    }

    public boolean Post(File file, int x) throws InterruptedException, IOException {
        String tempString = null;
        int rate;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String st = file.getName().substring(0, 12);
        String[] s = st.split("_");
        String msg;
        if (x == 1) {
            try {
                if ((tempString = reader.readLine()) == null) {
                    System.out.println("文件为空");
                } else {

                    String[] str = tempString.split(" ");
                    switch (str[0].length()) {
                        case 12:
                            rate = 1;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write1(msg, tempString, reader);

                        case 10:
                            rate = 2;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write2(msg, tempString, reader);

                        case 8:
                            rate = 3;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write3(msg, tempString, reader);

                        case 14:
                            rate = 0;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write0(msg, tempString, reader);

                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (x == 2) {
            try {
                if ((tempString = reader.readLine()) == null) {
                    System.out.println("文件为空");
                } else {
                    String[] str = tempString.split(" ");
                    switch (str[0].length()) {
                        case 12: {
                            rate = 1;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write_1(msg, tempString, reader);

                        }
                        case 10: {
                            rate = 2;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write_1(msg, tempString, reader);

                        }
                        case 8: {
                            rate = 3;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write_1(msg, tempString, reader);

                        }
                        case 14: {
                            rate = 0;
                            msg = s[0] + s[1] + s[2] + rate;
                            return Write_1(msg, tempString, reader);

                        }
                        default:
                            break;
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /*
    参数是行键台站+测点+测向+采样率
     */
    public boolean Write1(String rate, String tempString, BufferedReader reader) {
        String TableName = "testearthauakedate";
        String[] str = tempString.split(" ");
        String strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":" + str[0].substring(10, 12) + ":" + "00";
        Put put = new Put(Bytes.toBytes(rate));
        Timestamp ts = Timestamp.valueOf(strr);
        put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(Long.toString(ts.getTime())), Bytes.toBytes(str[1]));
        try {
            while ((tempString = reader.readLine()) != null) {
                str = tempString.split(" ");
                strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":" + str[0].substring(10, 12) + ":" + "00";
                put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(Long.toString(ts.getTime())), Bytes.toBytes(str[1]));
                if (!put.isEmpty() && put.size() == 100000) {
                    if ((HBaseUtil.inserts(TableName, put)) == true) {
                        put = null;
                    } else {
                        return false;
                    }
                    put = new Put(Bytes.toBytes(rate));
                }
            }
            if (!put.isEmpty()) {
                if ((HBaseUtil.inserts(TableName, put)) == true) {
                    put = null;
                } else {
                    return false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean Write2(String rate, String tempString, BufferedReader reader) {
        String TableName = "testearthauakedate";
        String[] str = tempString.split(" ");
        String strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":00:00";
        Put put = new Put(Bytes.toBytes(rate));
        Timestamp ts = Timestamp.valueOf(strr);
        put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(Long.toString(ts.getTime())), Bytes.toBytes(str[1]));
        try {
            while ((tempString = reader.readLine()) != null) {
                str = tempString.split(" ");
                strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":00:00";
                put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(str[0]), Bytes.toBytes(str[1]));
                if (!put.isEmpty() && put.size() == 100000) {
                    if ((HBaseUtil.inserts(TableName, put)) == true) {
                        put = null;
                    } else {
                        return false;
                    }
                    put = new Put(Bytes.toBytes(rate));
                }
            }
            if (!put.isEmpty()) {
                if ((HBaseUtil.inserts(TableName, put)) == true) {
                    put = null;
                } else {
                    return false;
                }
                put = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean Write3(String rate, String tempString, BufferedReader reader) {
        String TableName = "testearthauakedate";
        String[] str = tempString.split(" ");
        String strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " 00:00:00";
        Put put = new Put(Bytes.toBytes(rate));
        Timestamp ts = Timestamp.valueOf(strr);
        put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(Long.toString(ts.getTime())), Bytes.toBytes(str[1]));
        try {
            while ((tempString = reader.readLine()) != null) {
                str = tempString.split(" ");
                strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " 00:00:00";
                put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(str[0]), Bytes.toBytes(str[1]));
                if (!put.isEmpty() && put.size() == 100000) {
                    if (HBaseUtil.inserts(TableName, put)) {
                        put = null;
                    } else {
                        return false;
                    }
                    put = new Put(Bytes.toBytes(rate));
                }
            }
            if (!put.isEmpty()) {
                if (HBaseUtil.inserts(TableName, put)) {
                    put = null;
                } else {
                    return false;
                }
                put = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean Write0(String rate, String tempString, BufferedReader reader) {
        String TableName = "testearthauakedate";
        String[] str = tempString.split(" ");
        String strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":" + str[0].substring(10, 12) + ":" + str[0].substring(12, 14);
        Put put = new Put(Bytes.toBytes(rate));
        Timestamp ts = Timestamp.valueOf(strr);
        put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(Long.toString(ts.getTime())), Bytes.toBytes(str[1]));
        try {
            while ((tempString = reader.readLine()) != null) {
                str = tempString.split(" ");
                strr = str[0].substring(0, 4) + "-" + str[0].substring(4, 6) + "-" + str[0].substring(6, 8) + " " + str[0].substring(8, 10) + ":" + str[0].substring(10, 12) + ":" + str[0].substring(12, 14);
                put.addColumn(Bytes.toBytes("date"), Bytes.toBytes(str[0]), Bytes.toBytes(str[1]));
                if (!put.isEmpty() && put.size() == 100000) {
                    if (HBaseUtil.inserts(TableName, put)) {
                        put = null;
                    } else {
                        return false;
                    }
                    put = new Put(Bytes.toBytes(rate));
                }
            }
            if (!put.isEmpty()) {
                if (HBaseUtil.inserts(TableName, put)) {
                    put = null;
                } else {
                    return false;
                }
                put = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean Write_1(String rate, String tempString, BufferedReader reader) {
        String TableName = "tearth";
        List<Put> puts = new ArrayList<>();
        String[] str = tempString.split(" ");
        Put put = new Put(Bytes.toBytes(rate + str[0]));
        put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("value"), Bytes.toBytes(str[1]));
        puts.add(put);
        try {
            while ((tempString = reader.readLine()) != null) {
                str = tempString.split(" ");
                put = new Put(Bytes.toBytes(rate + str[0]));
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("value"), Bytes.toBytes(str[1]));
                puts.add(put);

                if (puts != null && puts.size() == 100000) {
                    if (HBaseUtil.inserts(TableName, puts)) {
                        puts.clear();
                    } else {
                        return false;
                    }
                }
            }
            if (!puts.isEmpty()) {
                if (HBaseUtil.inserts(TableName, puts)) {
                    puts.clear();
                } else {
                    return false;
                }
                put = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hbasewrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }
}
