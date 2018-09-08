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


    /**
     * 人才表类型
     */
    public static enum TalentType{
        Candidate("候选人"),Linkman("联系人"),ColdCall("Cold Call");
        private String desc;
        private TalentType(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    public static enum TalentAttachType{
        Primitive("原始简历"),Recommend("推荐报告"),Background("背景调查"),Health("体检报告"),OfferLetter("Offer Letter");
        private String desc;
        private TalentAttachType(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }



    public static enum MarryStatus{
        NoMarry("未婚"),Married("已婚"),NoKnow("未知"),KeepSecret("保密");
        private String desc;
        private MarryStatus(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }



    //学历
    public static enum EducationLevel{
        Junior("专科"),Regular("本科"),Master("硕士"),Doctor("博士"),PostDoctor("博士后"),MBA("MBA"),OtherEdu("其他");
        private String desc;
        private EducationLevel(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }


    //语言类别
    public static enum LanguageList{
        Chinese("中文:普通话"),ChineseYueYu("中文:粤语"),English("英语"),Franch("法语"),German("德语"),Italian("意大利语"),Japan("日语"),Korean("韩语"),Spanish("西班牙语"),Russian("俄罗斯语");
        private String desc;
        private LanguageList(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    //语言熟练程度
    public static enum LanguageLevel{
        MotherLanguage("母语"),Fluency("流利"),ProfessCommuni("专业交流"),ConCommuni("正常交流"),GeneralCommuni("一般会话"),SimpleCommuni("简单会话");
        private String desc;
        private LanguageLevel(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }


}
