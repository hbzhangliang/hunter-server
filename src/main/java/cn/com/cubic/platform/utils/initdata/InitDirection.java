package cn.com.cubic.platform.utils.initdata;

import cn.com.cubic.platform.hunter.controller.TestController;
import cn.com.cubic.platform.utils.XmlReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/9.
 **/

public class InitDirection {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    public static void main(String[] args){
        String url= "initdata/direction.xml";
        List<Element> list= XmlReader.getElementList(url);
        for(Element item:list){
            log.info("[{}]",item);
        }
    }


}
