package com.fc.service.impl;

import com.fc.dao.BookMapper;
import com.fc.dao.RecommendMapper;
import com.fc.entity.Book;
import com.fc.entity.Recommend;
import com.fc.service.AdminManageBookService;
import com.fc.util.FileUploadUtil;
import com.fc.vo.PageVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AdminManageBookServiceImpl implements AdminManageBookService {
    @Autowired
    private BookMapper mapper;
    @Autowired
    private RecommendMapper recommendMapper;

    @Override
    public String findById(Integer bid, HttpSession session) {
        Book book = mapper.findById(bid);
        session.setAttribute("g", book);
        return "admin/book_edit";
    }

    @Override
    public String insert(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        //拿到普通字段
        Book book = new Book();
        book.setBisbn(req.getParameter("bisbn"));
        book.setBname(req.getParameter("bname"));
        book.setBauthor(req.getParameter("bauthor"));
        book.setBpublisher(req.getParameter("bpublisher"));
        book.setBprice(Float.parseFloat(req.getParameter("bprice")));
        book.setBtid(Integer.valueOf(req.getParameter("btid")));
        book.setBstock(Integer.valueOf(req.getParameter("bstock")));
        book.setBmark(req.getParameter("bmark"));
        //上传图片
        String path = req.getServletContext().getRealPath("/images");
        MultipartFile cover = req.getFileMap().get("bcover");
        MultipartFile image1 = req.getFileMap().get("bimage1");
        MultipartFile image2 = req.getFileMap().get("bimage2");
        if (cover != null && cover.getOriginalFilename() != null && cover.getOriginalFilename().length() > 0) {
            book.setBcover(FileUploadUtil.fileUpload(cover, path));
        }
        if (image1 != null && image1.getOriginalFilename() != null && image1.getOriginalFilename().length() > 0) {
            book.setBimage1(FileUploadUtil.fileUpload(image1, path));

        }
        if (image2 != null && image2.getOriginalFilename() != null && image2.getOriginalFilename().length() > 0) {
            book.setBimage2(FileUploadUtil.fileUpload(image2, path));
        }

        mapper.insert(book);
        return "redirect:book_list?pageNumber=1&rtype=0";
    }

    //图书列表
    @Override
    public String getList(Integer pageNumber, Integer pageSize, Integer rtype, HttpServletRequest request) {
        List<Book> list;
        PageHelper.startPage(pageNumber, pageSize);
        if (rtype == null || rtype == 0) {
            list = mapper.findAll();
        } else {
            list = mapper.findAllByRecommendType(rtype);
        }
        //确定此图书的推荐类型
        for (Book book : list) {
            List<Recommend> Recommends = recommendMapper.findAllByBookId(book.getBid());
            for (Recommend recommend : Recommends) {
                Integer type = recommend.getRtype();
                switch (type) {
                    case 1:
                        book.setScroll(true);
                        break;
                    case 2:
                        book.setHot(true);
                        break;
                    case 3:
                        book.setNew(true);
                }
            }
        }

        PageVO<Book> pageVO = new PageVO<>(list);
        request.setAttribute("rtype", rtype);
        request.setAttribute("p", pageVO);
        return "/admin/book_list";
    }


    @Override
    public String update(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        //拿到普通字段
        Book book = new Book();
        book.setBid(Integer.valueOf(req.getParameter("bid")));
        book.setBisbn(req.getParameter("bisbn"));
        book.setBname(req.getParameter("bname"));
        book.setBauthor(req.getParameter("bauthor"));
        book.setBpublisher(req.getParameter("bpublisher"));
        book.setBprice(Float.parseFloat(req.getParameter("bprice")));
        book.setBtid(Integer.valueOf(req.getParameter("btid")));
        book.setBstock(Integer.valueOf(req.getParameter("bstock")));
        book.setBmark(req.getParameter("bmark"));
        //上传图片
        String path = req.getServletContext().getRealPath("/images");
        MultipartFile cover = req.getFileMap().get("bcover");
        MultipartFile image1 = req.getFileMap().get("bimage1");
        MultipartFile image2 = req.getFileMap().get("bimage2");
        if (cover != null && cover.getOriginalFilename() != null && cover.getOriginalFilename().length() > 0) {
            book.setBcover(FileUploadUtil.fileUpload(cover, path));
        }
        if (image1 != null && image1.getOriginalFilename() != null && image1.getOriginalFilename().length() > 0) {
            book.setBimage1(FileUploadUtil.fileUpload(image1, path));
        }
        if (image2 != null && image2.getOriginalFilename() != null && image2.getOriginalFilename().length() > 0) {
            book.setBimage2(FileUploadUtil.fileUpload(image2, path));
        }
        mapper.update(book);
        return "redirect:book_list?pageNumber=1&rtype=0";
    }

    //删除图书以及推荐类型的所有记录
    @Override
    public String delete(Integer bid) {
        if (bid == null) {
            return "redirect:book_list?pageNumber=1&rtype=0";
        }
        mapper.delete(bid);
        //根据bid删除推荐类型表中所有记录
        recommendMapper.deleteAllByBookId(bid);
        return "redirect:book_list?pageNumber=1&rtype=0";
    }

    @Override
    public String change(Integer bid, Integer rtype, String method, Integer page) {
        if (method == null || method.length() < 1) {
            return "redirect:book_list?pageNumber=1&rtype=" + page;
        }
        if (method.equals("add")) {
            insert(rtype, bid);
        } else {
            delete(rtype, bid);
        }
        return "redirect:book_list?pageNumber=1&rtype=" + page;
    }

    public int insert(Integer type, Integer id) {
        return recommendMapper.insert(type, id);
    }

    //删除图书的推荐类型
    public int delete(Integer type, Integer id) {
        return recommendMapper.delete(type, id);
    }


}
