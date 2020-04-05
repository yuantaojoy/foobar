package com.geek.foobar.io.fileoperate;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 曲元涛
 * @date 2020/4/5 17:34
 */
@Slf4j
public class FileOperate {

    private final String srcFile = "/Users/quyuantao/Downloads/runoob.txt";
    private final String tarFile = "/Users/quyuantao/Downloads/runoob1.txt";

    /**
     * 持续读取键盘输入保存到文件，这里使用的是字符输入输出流 Reader Writer
     *
     * @author 曲元涛
     * @date 2020/4/5 17:55
     */
    @Test
    public void writeFile(String filePath) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(filePath));
            String line = "";
            while (!StringUtils.isEmpty(line = input.readLine())) {
                output.write(line);
                output.newLine();
                output.flush();
            }
            output.close();
            input.close();
            System.out.println("saved system input to disk!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lambda 方式读取文件
     *
     * @author 曲元涛
     * @date 2020/4/5 18:28
     */
    @Test
    public void readFile(String filePath) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            System.out.println(
                    input.lines()
                            .collect(Collectors.joining(System.lineSeparator()))
            );
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteFile() {
        File file = new File("/Users/quyuantao/Downloads/runoob.txt");
        if (file.delete()) {
            System.out.println("文件已被成功删除");
        } else {
            System.out.println("文件删除失败");
        }
    }

    @Test
    public void copyFile() {
        try {
            // 读取键盘输入写入文件
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = new BufferedWriter(new FileWriter(srcFile));
            String line;
            while (!StringUtils.isEmpty(line = input.readLine())) {
                output.write(line);
                output.newLine();
                output.flush();
            }
            System.out.println("我文件已写完，你可以开始拷贝了");
            input.close();
            output.close();

            // 读出文件写入目标文件
            BufferedReader reader = new BufferedReader(new FileReader(srcFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tarFile));
            String lines = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            writer.write(lines);
            writer.flush();
            reader.close();
            writer.close();

            // 读取目标文件，打印到控制台
            BufferedReader resultReader = new BufferedReader(new FileReader(tarFile));
            System.out.println(resultReader.lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void appendFile() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = new BufferedWriter(new FileWriter(tarFile, true));
            String line;
            while (!StringUtils.isEmpty(line = input.readLine())) {
                output.write(line);
                output.newLine();
                output.flush();
            }
            input.close();

            BufferedReader reader = new BufferedReader(new FileReader(tarFile));
            System.out.println(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTmpFile() {
        try {
            File tmpFile = File.createTempFile("tmp_runoob", ".txt", new File("/Users/quyuantao/Downloads"));
            log.info("file absolute path:" + tmpFile.getAbsolutePath());
            tmpFile.deleteOnExit();
            tmpFile = File.createTempFile("tmp_runoob", null, new File("/Users/quyuantao/Downloads"));
            log.info("file path: " + tmpFile.getAbsolutePath());
            tmpFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改文件最后更新时间
     *
     * @author 曲元涛
     * @date 2020/4/5 23:35
     */
    @Test
    public void lastModified() {
        File fileToChange = new File(srcFile);
        try {
            if (fileToChange.createNewFile()) {
                log.info("创建新文件成功");
            } else {
                log.info("文件已存在，创建失败");
            }
            Date fileTime = new Date(fileToChange.lastModified());
            System.out.println(DateUtil.formatAsDatetime(fileTime));
            System.out.println(fileToChange.setLastModified(System.currentTimeMillis()));
            fileTime = new Date(fileToChange.lastModified());
            System.out.println(DateUtil.formatAsDatetime(fileTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过File.length计算文件大小单位字节，1KB=1024Byte(B)
     *
     * @author 曲元涛
     * @date 2020/4/5 23:37
     */
    @Test
    public void getFileSize() {
        File file = new File(tarFile);
        if (!file.exists() || !file.isFile()) {
            System.out.println(-1);
            return;
        }
        System.out.println(file.length());
    }

    /**
     * 文件重命名
     *
     * @author 曲元涛
     * @date 2020/4/5 23:39
     */
    @Test
    public void rename() {
        File oldFile = new File("/Users/quyuantao/Downloads/runoob-rn.txt");
        File newFile = new File("tarFile");
        if (oldFile.renameTo(newFile)) {
            log.info("重命名成功");
        } else {
            log.info("重命名失败");
        }
        try {
            BufferedReader input = new BufferedReader(new FileReader(newFile));
            System.out.println(input.lines().collect(Collectors.joining("\n")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置文件只读
     *
     * @author 曲元涛
     * @date 2020/4/5 23:45
     */
    @Test
    public void setReadonly() {
        File file = new File(tarFile);
        if (file.setReadOnly()) {
            log.info("设置文件只读成功");
        } else {
            log.info("设置文件只读失败");
        }
        System.out.println(file.canWrite());
        System.out.println(file.setWritable(true));
        System.out.println(file.canWrite());
    }

    @Test
    public void isExists() {
        File file = new File(tarFile);
        if (!file.exists()) {
            System.out.println("文件不存在，创建文件!");
            this.writeFile(tarFile);
        } else {
            this.readFile(tarFile);
        }
    }

    @Test
    public void comparePath() {
        File file1 = new File(srcFile);
        File file2 = new File(tarFile);
        if (file1.compareTo(file2) != 0) {
            System.out.println("路径不一致");
        } else {
            System.out.println("路径一致");
        }
    }

    public static void main(String[] args) {
        FileOperate fileOperate = new FileOperate();
        fileOperate.comparePath();
    }
}
