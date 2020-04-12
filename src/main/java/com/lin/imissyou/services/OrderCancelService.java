/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-04-05 21:30
 */
package com.lin.imissyou.services;

import com.lin.imissyou.bo.OrderMessageBO;
import com.lin.imissyou.exception.http.ServerErrorException;
import com.lin.imissyou.model.Order;
import com.lin.imissyou.repository.OrderRepository;
import com.lin.imissyou.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderCancelService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;


    @Transactional
    @EventListener
    public void cancel(OrderMessageBO messageBO) {
        if (messageBO.getOrderId()<=0){
            throw new ServerErrorException(9999);
        }
        try {
            this.cancel(messageBO.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cancel(Long oid) {
        Optional<Order> orderOptional = orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(()-> new ServerErrorException(9999));
        int res = orderRepository.cancelOrder(oid);
        if (res != 1) {
            return;
        }
        order.getSnapItems().forEach(i->{
            skuRepository.recoverStock(i.getId(), i.getCount());
        });
    }
}
