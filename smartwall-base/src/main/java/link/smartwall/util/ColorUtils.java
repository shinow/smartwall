/**
 * ReportGear(2011)
 */
package link.smartwall.util;

import java.awt.Color;

/**
 * 颜色实用类
 * 
 * @version 1.0 2011-4-14
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public final class ColorUtils {
    /**
     * constructor
     */
    private ColorUtils() {
        // --
    }

    /**
     * CSS颜色转换
     * 
     * @param color 颜色
     * @return 颜色串
     */
    public static String toCssString(Color color) {
        String r, g, b;
        StringBuffer sb = new StringBuffer("#");
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        sb.append(r);
        sb.append(g);
        sb.append(b);

        return sb.toString();
    }

    /**
     * 十六进制颜色转换
     * 
     * @param color 颜色
     * @return 颜色串
     */
    public static String toHexEncoding(Color color) {
        String r, g, b, a;
        StringBuffer sb = new StringBuffer();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        a = Integer.toHexString(color.getAlpha());

        a = a.length() == 1 ? "0" + a : a;
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        sb.append(a);
        sb.append(r);
        sb.append(g);
        sb.append(b);

        return sb.toString();

    }

    /**
     * 把字符串表达的颜色值转换成java.awt.Color
     * 
     * @param c 颜色串
     * @return 颜色
     */
    public static Color parseToColor(final String c) {
        Color convertedColor = Color.BLACK;
        try {
            convertedColor = new Color((int) Long.parseLong(c, 16), true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // codes to deal with this exception
        }

        return convertedColor;
    }
}
