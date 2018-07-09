package cn.com.cubic.platform.utils.initdata;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/9.
 **/

public class InitDirection {

    public static void main(String[] args){
        try{
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File("initdata/direction.xml"));
            Element foo = doc.getRootElement();
            List allChildren = foo.getChildren();
            for(int i=0;i<allChildren.size();i++){
                Element element=(Element)allChildren.get(i);
                System.out.println(element);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
