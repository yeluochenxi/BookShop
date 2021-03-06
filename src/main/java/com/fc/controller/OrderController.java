package com.fc.controller;

import com.fc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller

public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("books_buy")
    @ResponseBody
    public String buyBook(Integer bid, HttpSession session) {
        return orderService.buyBook(bid, session);
    }

    @RequestMapping("books_lessen")
    @ResponseBody
    public String lessenBook(Integer bid, HttpSession session) {
        return orderService.lessenBook(bid, session);
    }

    @RequestMapping("order_list")
    public ModelAndView find(ModelAndView mv, HttpSession session) {
        return orderService.findAll(mv, session);

    }

    @RequestMapping("order_confirm")
    public ModelAndView confirm(HttpSession session, ModelAndView mv, @RequestParam Integer opaytype, String opayType) {
        return orderService.insert(session, mv, opaytype);
    }

    @RequestMapping("order_submit")
    public ModelAndView submit(ModelAndView mv, HttpSession session) {
        return orderService.submit(mv, session);
    }

    @RequestMapping("books_delete")
    @ResponseBody
    public String deleteBooks(Integer bid, HttpSession session) {
        return orderService.deleteBooks(bid, session);
    }
}
