package cn.com.cubic.platform.hunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/
@Validated
@Controller
@RequestMapping(value = "/corp",produces = "application/json; charset=utf-8")
@ResponseBody
public class CorpController {
}
