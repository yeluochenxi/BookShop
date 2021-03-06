package com.fc.service;

import com.fc.entity.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public interface AdminManageUserService {

    ModelAndView userAdd(ModelAndView mv,  User user);

    ModelAndView userEditShow(Integer uid, HttpSession session, ModelAndView mv);

    ModelAndView user_list(ModelAndView mv, Integer pageNumber, Integer pageSize, HttpSession session);

    ModelAndView delete(Integer uid, ModelAndView mv);

    ModelAndView update(User user, HttpSession session, ModelAndView mv);

    ModelAndView updatePassword(User user, ModelAndView mv, HttpSession session);
}
