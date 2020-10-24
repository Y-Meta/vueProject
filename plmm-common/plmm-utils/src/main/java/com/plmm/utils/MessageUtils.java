package com.plmm.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取i18n资源文件
 *
 * @author kongjw
 */
public class MessageUtils {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        Locale locale = null;
        if (!StringUtils.isEmpty(args) && args.length > 0) {
            String lang = args[0].toString();
            if (lang.contains("_")) {
                String[] split = lang.split("_");
                locale = new Locale(split[0], split[1]);
            } else if (lang.contains("en")) {
                locale = Locale.ENGLISH;
            }
        } else {
            locale = LocaleContextHolder.getLocale();
        }
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, locale);
    }


    public static String message(String code,String lang, Object... args) {
        Locale locale = null;
        if (StringUtils.isNotEmpty(lang)) {
            if (lang.contains("_")) {
                String[] split = lang.split("_");
                locale = new Locale(split[0], split[1]);
            } else if (lang.contains("en")) {
                locale = Locale.ENGLISH;
            }
        } else {
            locale = LocaleContextHolder.getLocale();
        }
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, locale);
    }
}
