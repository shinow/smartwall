///**
// * SmartWall(2014)
// */
//package link.smartwall.grid.util;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * 文件压缩辅助类
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤管家3.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2014-9-5 lexloo
// *        </pre>
// */
//public final class CompressedFileUtil {
////    /**
////     * 压缩文件缓存路径
////     */
////    public static final String TEMP_FILE_DIR =
////            GlobalStatic.XSB_HOME_DATA_DIR + GlobalStatic.DATACACHE_DIR + File.separator + "temp" + File.separator;
////
////    /**
////     * 临时文件获取地址
////     */
////    public static final String TEMP_FILE_URL = Global.DATACACHE_URL + File.separator + "temp" + File.separator;
//
//    /**
//     * 默认构造函数
//     */
//    private CompressedFileUtil() {
//        // --
//    }
//
//    /**
//     * 压缩文件，并返回文件名
//     * 
//     * @param fileList 需要压缩的文件列表
//     * @return 最终的压缩文件名
//     */
//    public static String compressFiles(List<String> fileList) {
//        ZipOutputStream out = null;
//        try {
//            if (fileList.size() > 0) {
//                String zipFileName = UUID.randomUUID().toString().replace("-", "") + ".zip";
//                FileOutputStream outputStream = new FileOutputStream(TEMP_FILE_DIR + zipFileName);
//                out = new ZipOutputStream(new BufferedOutputStream(outputStream));
//
//                for (String fileName : fileList) {
//                    // 文件输入流
//                    File f = new File(fileName);
//                    if (f.exists()) {
//                        FileInputStream fis = new FileInputStream(f);
//
//                        out.putNextEntry(new ZipEntry(f.getName()));
//                        // 进行写操作
//                        int j = 0;
//                        byte[] buffer = new byte[1024];
//                        while ((j = fis.read(buffer)) > 0) {
//                            out.write(buffer, 0, j);
//                        }
//                        fis.close();
//                    }
//                }
//
//                return TEMP_FILE_URL + zipFileName;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 新奥燃气导出图片。
//     *
//     * @param map 以colGroupBy为类别，进行导出图片归类。
//     * @param zipFileName 最终的压缩文件名称。
//     * @return zip文件的路径。
//     */
//    public static String compressFiles(Map<String, List<String>> map, String zipFileName) {
//        ZipOutputStream out = null;
//        try {
//            FileOutputStream outputStream = new FileOutputStream(TEMP_FILE_DIR + zipFileName);
//            out = new ZipOutputStream(new BufferedOutputStream(outputStream));
//
//            for (String key : map.keySet()) {
//                List<String> files = map.get(key);
//                out.putNextEntry(new ZipEntry(key + File.separator));
//                for (String file : files) {
//
//                    // 文件输入流
//                    File f = new File(file);
//                    if (f.exists()) {
//                        FileInputStream fis = new FileInputStream(f);
//
//                        out.putNextEntry(new ZipEntry(key + File.separator + f.getName()));
//                        // 进行写操作
//                        int j = 0;
//                        byte[] buffer = new byte[1024];
//                        while ((j = fis.read(buffer)) > 0) {
//                            out.write(buffer, 0, j);
//                        }
//                        fis.close();
//                    }
//                }
//            }
//
//            return TEMP_FILE_URL + zipFileName;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//}
