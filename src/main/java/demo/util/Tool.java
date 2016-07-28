package demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wang on 16/7/28.
 */
public class Tool {
    /**
     * 格式化输出日期时间
     * @param timestrap long型时间,单位秒
     * @return
     *      格式化后的字符串24小时制，例如 2015-08-20 22:32:35
     */
    public static String formateDate(long timestrap) {
        if(timestrap < 1){
            return "0000-00-00";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestrap * 1000);
    }
}
