package cn.itsource.hrm.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MessageListener {
    public static final String EXCHANGE_NAME_DIRECT="hrm-exchange-direct";
    public static final String QUEUE_NAME_SMS="hrm-queue-sms";
    public static final String QUEUE_NAME_EMAIL="hrm-queue-email";
    public static final String QUEUE_NAME_SYSTEM_MESSAGE="hrm-queue-system-message";

    @RabbitListener(queues = {QUEUE_NAME_SMS})
    public void listenSMS(String msg, Message message, Channel channel){
        //直接处理
        //模拟发送短信
        //先把消息转换回集合
        List<Map<String,String>> msgList = (List<Map<String, String>>) JSONArray.parse(msg);
        List<String> names = msgList.stream().map(course -> {
            return course.get("name");
        }).collect(Collectors.toList());
        String courseNames = String.join(",",names);
        System.out.println(courseNames);
        System.out.println("上线了");
    }

}
