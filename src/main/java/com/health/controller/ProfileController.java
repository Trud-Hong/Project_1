package com.health.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.MemberRecordDAO;
import com.health.dto.CustomInfo;
import com.health.dto.MemberDTO;
import com.health.dto.WeightDTO;

@Controller
public class ProfileController {
	

    @Autowired
    MemberRecordDAO memberRecordDao;
	
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(MemberDTO dto, HttpServletRequest req) throws Exception {
	
        HttpSession session = req.getSession();
        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
        if (info == null) {
            return "redirect:/login.do";
        }

        // ✅ 체중/BMI 기록 불러오기
        List<WeightDTO> lists = memberRecordDao.getWeightLogs(info.getMember_no());
        req.setAttribute("lists", lists);

        return "/health/profile";
    }
}
