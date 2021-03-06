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
        public static ShareType getShareType(String str){
            for(ShareType item:ShareType.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return account;
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



    public static enum TreeType{
        BusinessTree,CareerTree,CityTree,ShareTree;
        public static TreeType getTreeType(String str){
            for(TreeType item:TreeType.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return null;
        }
    }


    /**
     * 人才的删除状态
     * 正常  伪删除  删除
     */
    public static enum TalentDelStatus{
        Normal,Faked,Deleted;
        public static TalentDelStatus TalentDelStatus(String str){
            for(TalentDelStatus item: TalentDelStatus.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return null;
        }
    }






    //company表的相应枚举
    public static enum CorpType{
        Common("普通公司"),Develop("开发中公司"),Signed("已签约公司"),Terminated("终止合作");
        private String desc;
        private CorpType(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }


    public static enum CorpMtype{
        None("无"),ChildCorp("子公司"),MotherCorp("母公司");
        private String desc;
        private CorpMtype(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    public static enum CorpFinace{
        A("A轮"),B("B轮"),C("C轮"),D("D轮"),E("E轮"),F("F轮"),S("上市");
        private String desc;
        private CorpFinace(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }


    public static enum CompanyDelStatus{
        Normal,Faked,Deleted;
        public static CompanyDelStatus CompanyDelStatus(String str){
            for(CompanyDelStatus item: CompanyDelStatus.values()){
                if(str.equalsIgnoreCase(item.toString())){
                    return item;
                }
            }
            return null;
        }
    }


    //project
    public static enum ProjectPriority{
        Level1("第一等级(最高)"),Level2("第二等级(次高)"),Level3("第三等级(一般)"),Level4("第四等级(次低)"),Level5("第五等级(最低)");
        private String desc;
        private ProjectPriority(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

    public static enum ProcessStatus{
        Process("进展中"),Success("已成功的"),Pause("暂停的"),NoEffective("失效的"),Canceled("已取消");
        private String desc;
        private ProcessStatus(String desc){
            this.desc=desc;
        }
        public String getDesc(){
            return desc;
        }
    }

}
