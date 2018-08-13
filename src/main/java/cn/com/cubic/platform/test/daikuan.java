package cn.com.cubic.platform.test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Liang.Zhang on 2018/8/13.
 **/

public class daikuan {
    public static void main(String[] args){
        dk();
    }

    public static void dk(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        double floor=0.049;
        double lilv=floor*(1+0.15);

        double benjin=500000;
        int count=360;
        double shangye=0,gongji=1305.62;
        DecimalFormat df = new DecimalFormat(".00");
        Calendar calendar=Calendar.getInstance();

        try{
            calendar.setTime(sdf.parse("2018-08-11"));
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        for(int i=0;i<count;i++){
            shangye=benjin/count+(benjin-i*benjin/count)*(lilv/12);
            String str=String.format("时间:%s,商贷:%s,公积金贷:%s,总额:%s",sdf.format(calendar.getTime()),df.format(shangye),gongji,df.format(shangye+gongji));
            System.out.println( str);
            calendar.add(Calendar.MONTH,1);
        }
    }

}
