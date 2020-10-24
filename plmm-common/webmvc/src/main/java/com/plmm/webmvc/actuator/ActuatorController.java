package com.plmm.webmvc.actuator;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import com.plmm.localcache.service.ICacheContextService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plmm.utils.Captcha;

@Controller
@RequestMapping("actuator")
public class ActuatorController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
//	private JedisCluster jedisCluster;
            ICacheContextService cacheContextService;

	@GetMapping("f5")
	public @ResponseBody String f5() {
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		return "ok";
	}

	@GetMapping("setLang")
	public @ResponseBody String setLocale(@RequestParam(value = "lang") String lang) {
		if (StringUtils.isNotBlank(lang)) {
			if (lang.equals("en")) {
				Locale.setDefault(new Locale("en", "US"));
			} else if (lang.equals("zh")) {
				Locale.setDefault(new Locale("zh", "CN"));
			} else
				return "Failed";
		}
		return "Success";
	}

	@PostMapping(value = "/getCheckCode")
	@ResponseBody
	public String generate() throws IOException {
		Captcha captcha = new Captcha(4, 200, 80);
		String generateChars = captcha.generateChars();
		String codeKey = UUID.randomUUID().toString();
		codeKey = "codeKey_" + codeKey;
		logger.info("codeKey:" + codeKey + ",checkCode:" + generateChars);
		cacheContextService.set(codeKey, generateChars);
		cacheContextService.expire(codeKey, 300); // 300s
		return codeKey + "|" + captcha.outputImage(generateChars);
	}
}
