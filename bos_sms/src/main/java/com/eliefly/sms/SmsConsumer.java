package com.eliefly.sms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**
 * ClassName:SmsConsumer <br/>
 * Function: <br/>
 * Date: 2017年12月12日 上午12:55:43 <br/>
 */

@Component
public class SmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {

        MapMessage mapMessage = (MapMessage) message;

        try {
            String telephone = mapMessage.getString("telephone");
            String content = mapMessage.getString("content");

            System.out.println(telephone + "====" + content);

        } catch (JMSException e) {

            e.printStackTrace();
        }

    }

}
