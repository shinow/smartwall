package com.itfsm.grid.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 王博
 */
public class CompressedFileUtilTest {

    /**
     * @throws Exception e
     */
//    @Test
    public void testCompressFiles() throws Exception {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("c:\\temp\\1a.txt");
        list.add("c:\\temp\\1b.txt");
        list.add("c:\\temp\\1c.txt");
        map.put("1", list);

        list = new ArrayList<>();
        list.add("c:\\temp\\2a.txt");
        list.add("c:\\temp\\2b.txt");
        list.add("c:\\temp\\2c.txt");
        map.put("2", list);

        list = new ArrayList<>();
        list.add("c:\\temp\\3a.txt");
        list.add("c:\\temp\\3b.txt");
        list.add("c:\\temp\\3c.txt");
        map.put("3", list);

        compressFiles(map, "test.zip");
    }

    /**
     * 新奥燃气导出图片。
     *
     * @param map         以colGroupBy为类别，进行导出图片归类。
     * @param zipFileName 最终的压缩文件名称。
     * @return zip文件的路径。
     */
    public static String compressFiles(Map<String, List<String>> map, String zipFileName) {
        ZipOutputStream out = null;
        try {
            FileOutputStream outputStream = new FileOutputStream("f:\\" + zipFileName);
            out = new ZipOutputStream(new BufferedOutputStream(outputStream));

            for (String key : map.keySet()) {
                List<String> files = map.get(key);
                out.putNextEntry(new ZipEntry(key + File.separator));
                for (String file : files) {

                    // 文件输入流
                    File f = new File(file);
                    if (f.exists()) {
                        FileInputStream fis = new FileInputStream(f);

                        out.putNextEntry(new ZipEntry(key + File.separator + f.getName()));
                        // 进行写操作
                        int j = 0;
                        byte[] buffer = new byte[1024];
                        while ((j = fis.read(buffer)) > 0) {
                            out.write(buffer, 0, j);
                        }
                        fis.close();
                    }
                }
            }

            return "f:\\" + zipFileName;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}