/**
 * SmartWall(2015)
 */
package link.smartwall.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 网络相关工具类
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年7月24日 lexloo * 建立
 * </pre>
 */
public final class NetUtils {
    /**
     * 
     */
    private NetUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 获取 MAC
     * 
     * @return mac Address
     */
    public static String getLocalMACAddress() {

        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        try {
            // 获取本地IP对象
            InetAddress ia = InetAddress.getLocalHost();

            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    /**
     * 获取 MAC
     * 
     * @return mac Address
     */
    public static String getOSName() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName;
    }

    /**
     * 
     * 
     */
    public static void killCurrProcessId() {
        try {
            String os = getOSName();
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String pid = name.split("@")[0];

            if (os.startsWith("windows")) {
                Runtime.getRuntime().exec("cmd.exe /c taskkill /f /pid " + pid);
            }
            if (os.startsWith("linux")) {
                final String[] array = {"/bin/sh", "-c", "kill -9 " + pid};
                Runtime.getRuntime().exec(array);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
