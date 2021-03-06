package com.fc.controller;

import com.fc.entity.User;
import com.fc.service.AdminManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequestMapping("admin")
@Controller
public class AdminManageUserController {

    @Autowired
    private AdminManageUserService adminManageUserService;

    @PostMapping("user_add")
    public ModelAndView userAdd(ModelAndView mv, User user) {
        mv = adminManageUserService.userAdd(mv, user);
        return mv;
    }

    @GetMapping("user_edit_show")
    public ModelAndView userEditShow(Integer uid, HttpSession session, ModelAndView mv) {
        mv = adminManageUserService.userEditShow(uid, session, mv);
        return mv;
    }

    @RequestMapping("user_list")
    public ModelAndView user_list(@RequestParam("pageNumber") Integer pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
                                  ModelAndView mv, HttpSession session) {
        mv = adminManageUserService.user_list(mv, pageNumber, pageSize, session);
        return mv;
    }

    @GetMapping("user_delete")
    public ModelAndView delete(Integer uid, ModelAndView mv) {
        mv = adminManageUserService.delete(uid, mv);
        return mv;
    }

    @PostMapping("user_update")
    public ModelAndView userUpdate(User user, HttpSession session, ModelAndView mv) {
        mv = adminManageUserService.update(user, session, mv);

        return mv;
    }

    @PostMapping("change_password")
    public ModelAndView changePassword(User user, ModelAndView mv, HttpSession session) {
        mv = adminManageUserService.updatePassword(user, mv, session);
        return mv;
    }

    @GetMapping("logout")
    public ModelAndView logout(ModelAndView mv, HttpSession session) {
        session.removeAttribute("user");
        mv.setViewName("redirect:/");
        return mv;
    }
}
