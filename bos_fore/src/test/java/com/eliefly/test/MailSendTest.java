package com.eliefly.test;

import com.eliefly.utils.MailUtils;

/**
 * ClassName:MailSendTest <br/>
 * Function: <br/>
 * Date: 2017年12月6日 下午4:24:40 <br/>
 */
public class MailSendTest {

    public static void main(String[] args) {

        MailUtils.sendMail("jack@store.com", "激活邮件", "请激活您的账号");

    }

}
