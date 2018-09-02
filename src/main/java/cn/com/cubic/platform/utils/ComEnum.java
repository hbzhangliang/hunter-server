package cn.com.cubic.platform.utils;

/**
 * Created by Liang.Zhang on 2018/7/11.
 **/

public class ComEnum {


    public static enum accountStatus{

    }

    /**
     * 分享的类别， 人才分享，企业分享，项目分享，目录分享
     */
    public static enum ShareCategory{
        talent,company,project,doc,other;
        public static ShareCategory getShareCategory(String str){
            for(ShareCategory item:ShareCategory.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return other;
        }
    }

    /**
     * 分享的类型， 个人，团队，岗位，所有人
     */
    public static enum ShareType{
        account("个人"),position("岗位"),team("团队"),all("所有人");

        private String desc;
        private ShareType(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }


    /**
     * 文档类型
     */
    public static enum DocType{
        talent,company,project,other;
        public static DocType getDocType(String str){
            for(DocType item:DocType.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return other;
        }
    }



}
