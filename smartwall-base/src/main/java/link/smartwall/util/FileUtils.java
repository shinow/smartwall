package link.smartwall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

/**
 * 文件工具类
 * 
 * @version 1.0
 * @author <a href="ttan_style@sina.cn">ttan</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014年7月16日  ttan
 * </pre>
 */
public final class FileUtils {

    /**
     * 默认构造器
     */
    private FileUtils() {
        // 默认构造器
    }

    /**
     * 将内容写入到文本
     * 
     * @param filePath 文件路径
     * @param content 文件内容
     * @param append 是否追加
     */
    public static void writeToFile(String filePath, String content, boolean append) {
        File file = createFile(filePath);

        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8");
            writer.write(content);

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件(包含文件夹)
     * 
     * @param filePath 文件路径
     * @return 返回创建的文件
     */
    public static File createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            mkDir(file.getParentFile());
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.setWritable(true);

        return file;
    }

    /**
     * 创建文件夹
     * 
     * @param file 文件
     */
    private static void mkDir(File file) {
        if (!file.getParentFile().exists()) {
            mkDir(file.getParentFile());
        }

        file.mkdir();
    }

    /**
     * 拷贝旧文件路径的所有的文件和文件夹到新的目录下
     * 
     * @param oldPath 旧文件路径
     * @param newPath 新文件路径
     * @return 结果
     */
    public static boolean copyFoldersAndFiles(String oldPath, String newPath) {
        try {
            // 如果文件夹不存在 则建立新文件夹
            (new File(newPath)).mkdirs();

            File oldPathFile = new File(oldPath);
            String[] file = oldPathFile.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                // 如果是文件
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output =
                            new FileOutputStream(newPath + File.separator + (temp.getName()).toString());

                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                // 如果是子文件夹
                if (temp.isDirectory()) {
                    copyFoldersAndFiles(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * 
     * @param dir 将要删除的文件目录
     * @return 结果
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 保存二进制流到指定的文件
     * 
     * @param folder 文件存储目录
     * @param fileName 文件名，包括后缀
     * @param bytes 二进制流
     */
    public static void writeFile(String folder, String fileName, byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("数据流参数为空");
        }

        File ff = new File(folder);
        if (!ff.exists()) {
            ff.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(folder + fileName)) {
            fos.write(bytes);
        } catch (Exception e) {
            throw new IllegalStateException("保存文件发生错误");
        }
    }

    /**
     * 生成唯一文件名
     * 
     * @param ext 文件后缀名
     * @return 文件名
     */
    public static String genUUIDFileName(String ext) {
        if (ext.startsWith(".")) {
            return UUID.randomUUID().toString().replace("-", "") + ext;
        } else {
            return UUID.randomUUID().toString().replace("-", "") + "." + ext;
        }
    }
}
