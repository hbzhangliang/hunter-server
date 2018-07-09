package cn.com.cubic.platform.utils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/9.
 **/

public class XmlReader {

    public static List<Element> getElementList(String path){
        try{
            path=ResourceUtils.getURL("classpath:").getPath()+path;
            List<Element> list=new ArrayList<>(10);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(path));
            Element foo = doc.getRootElement();
            List allChildren = foo.getChildren();
            for(int i=0;i<allChildren.size();i++){
                list.add((Element)(allChildren.get(i)));
            }
            for(Element item:list){
                System.out.println(item.getChild("name").getText());
            }
            return list;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
