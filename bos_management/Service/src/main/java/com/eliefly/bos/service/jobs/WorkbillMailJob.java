package com.eliefly.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eliefly.bos.dao.take_delivery.WorkBillRepository;
import com.eliefly.bos.domain.take_delivery.WorkBill;
import com.eliefly.utils.MailUtils;

/**
 * ClassName:WorkbillMailJob <br/>
 * Function: <br/>
 * Date: 2017年12月16日 下午4:33:54 <br/>
 */
@Component
public class WorkbillMailJob {

    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendMail() {

        // 查询所有工单
        List<WorkBill> list = workBillRepository.findAll();
        String receiver = "jack@store.com";
        String subject = "工单信息";
        String emailBody = "工单编号\t工单类型\t取件状态\t快递员<br/>";
        // 拼接邮件内容
        for (WorkBill workBill : list) {
            emailBody += workBill.getId() + "\t" + workBill.getType() + "\t"
                    + workBill.getPickstate() + "\t" + workBill.getCourier().getName() + "<br/>";
        }

        System.out.println("发送工单邮件");

        // 发送邮件
        MailUtils.sendMail(receiver, subject, emailBody);

    }

}
