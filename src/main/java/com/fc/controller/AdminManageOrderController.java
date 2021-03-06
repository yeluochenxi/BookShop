package com.fc.controller;

import com.fc.entity.Order;
import com.fc.service.AdminManageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("admin")
public class AdminManageOrderController {
    @Autowired
    private AdminManageOrderService orderListService;
    @RequestMapping("order_list")
    public ModelAndView list( ModelAndView mv, @RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "8") Integer pageSize, Integer ostatus){
        return orderListService.list(mv,pageNumber,pageSize,ostatus);
    }
    @GetMapping("order_delete")
    public ModelAndView delete(ModelAndView mv, String oid, String ostatus){
        return orderListService.delete(mv,oid,ostatus);
    }
    @RequestMapping("order_status_change")
    public ModelAndView change(ModelAndView mv, Order order){
        return orderListService.change(mv,order);
    }
}
