package com.health.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.HabitDAO;
import com.health.dao.MemberDAO;
import com.health.dao.ReviewDAO;
import com.health.dto.CustomInfo;
import com.health.dto.HabitDTO;
import com.health.dto.MemberDTO;
import com.health.dto.ReviewDTO;
import com.health.util.MyUtil;

@Controller
public class HabitController {
	// Test
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;

	@Autowired
	@Qualifier("habitDAO")
	HabitDAO habit_dao;

	@Autowired
	MyUtil myUtil;

	private static String toHm(String time) {
		if (time == null || time.length() < 5) return null;
		int space = time.indexOf(' ');
		if (space >= 0 && time.length() >= space + 6) return time.substring(space + 1, space + 6);
		return time.substring(0, 5); // "12:30:00" → "12:30"
	}
	
	
	@RequestMapping(value = "/habit", method = RequestMethod.GET)
	public String habit(HabitDTO dto, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
	 	CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
	 	
		if (info == null) {
			return "redirect:/login.do";
		}

		int member_no = info.getMember_no(); 
		
		List<HabitDTO> lists = habit_dao.getLists(member_no);
		System.out.println("member_no: " + member_no + ", list size: " + lists.size());
		
		req.setAttribute("lists", lists);
		
		HabitDTO dtoHb = lists.isEmpty() ? null : lists.get(0);
		req.setAttribute("dtoHb", dtoHb);

		if (dtoHb != null) {
			// 문자열 원본이 "20250912 12:30:00" 또는 "12:30:00" 모두 처리
			req.setAttribute("breakfastTimeStr", toHm(dtoHb.getBreakfast_time()));
			req.setAttribute("lunchTimeStr", toHm(dtoHb.getLunch_time()));
			req.setAttribute("dinnerTimeStr", toHm(dtoHb.getDinner_time()));
		}
		return "/health/habit";
	}

	@RequestMapping(value = "/habitUpdate", method = RequestMethod.POST)
	public String habitUpdate(HabitDTO dto, HttpServletRequest req) {
		
		try {
	        if (dto.getBreakfast_time() != null && dto.getBreakfast_time().trim().isEmpty()) {
	            dto.setBreakfast_time(null);
	        }
	        if (dto.getLunch_time() != null && dto.getLunch_time().trim().isEmpty()) {
	            dto.setLunch_time(null);
	        }
	        if (dto.getDinner_time() != null && dto.getDinner_time().trim().isEmpty()) {
	            dto.setDinner_time(null);
	        }
			
			// 세션의 정보도 업데이트
			HttpSession session = req.getSession();
			CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
			dto.setMember_no(info.getMember_no());
			habit_dao.insertData(dto);
			System.out.println("실행 성공");
			if (info != null) {
				
				info.setRecord_id(dto.getRecord_id());
				info.setHabit_record_date(dto.getRecord_date());
				info.setBreakfast_time(dto.getBreakfast_time());
				info.setLunch_time(dto.getLunch_time());
				info.setDinner_time(dto.getDinner_time());
				
				session.setAttribute("customInfo", info);
			}
			System.out.println("세션 업데이트 성공");
			
			
		} catch (Exception e) {
			System.out.println("업데이트 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		
		return "redirect:/main.do";
	}
	
	
	
	
	
	
	
	
	
	
	
//	//이용 후기 페이지 접근
//	@RequestMapping(value = "/review", method = RequestMethod.GET)
//	public String review(HttpServletRequest req) throws Exception {
//	
//		String cp = req.getContextPath();
//		
//		String pageNum = req.getParameter("pageNum");
//		
//		int currentPage = 1;
//		
//		if (pageNum != null) {
//			currentPage = Integer.parseInt(pageNum);
//		}
//		
//		int dataCount = review_dao.getDataCount();
//		
//		int numPerPage = 5;
//		
//		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
//		
//		if (currentPage>totalPage) {
//			currentPage = totalPage;
//		}
//		
//		int start = (currentPage - 1) * numPerPage + 1;
//		int end = currentPage * numPerPage;
//		
//		List<ReviewDTO> lists = review_dao.getLists(start, end);
//		
//		String listUrl = cp + "/review.do";
//		
//		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
//		
//		String articleUrl = cp + "/reviewArticle.do?pageNum=" + currentPage;
//		
//		req.setAttribute("lists", lists);
//		req.setAttribute("pageIndexList", pageIndexList);
//		req.setAttribute("dataCount", dataCount);
//		req.setAttribute("articleUrl", articleUrl);
//		
//		return "/health/review";
//	}
//	
//	//이용 후기 작성 페이지 진입
//	@RequestMapping(value = "/reviewWritePage", method = RequestMethod.GET)
//	public String reviewWritePage(ReviewDTO dto, HttpServletRequest req) {
//		
//		return "/health/reviewWrite";
//	}
//	
//	
//	
//	
//	//이용 후기 작성
//	@RequestMapping(value = "/reviewWrite", method = RequestMethod.POST)
//	public String reviewWrite(ReviewDTO dto, HttpServletRequest req) {
//
//		HttpSession session = req.getSession();
//		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
//		
//		dto.setMember_no(info.getMember_no());
//		
//		int maxNum = review_dao.getMaxNum();
//		dto.setReview_id(maxNum + 1);
//
//		review_dao.insertData(dto);
//
//		return "redirect:/review.do";
//	}
//	
//	//이용 후기 상세 페이지 진입
//	@RequestMapping(value = "/reviewArticle.do", method = {RequestMethod.GET})
//	public String article(ReviewDTO dto, HttpServletRequest req, HttpServletResponse resp) throws Exception{
//		
//		String cp = req.getContextPath();
//		
//		String pageNum = req.getParameter("pageNum");
//
//
//		review_dao.updateHitCount(dto.getReview_id());
//
//		dto = review_dao.getReadData(dto.getReview_id());
//		
//		if (dto==null) {
//
//			return "redirect:/review.do";
//		}
//		
//		dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));
//		
//		String param = "pageNum=" + pageNum;
//
//		req.setAttribute("dto",dto);
//		req.setAttribute("params", param);
//		req.setAttribute("pageNum", pageNum);
//		
//		return "/health/reviewArticle";
//	}
//	
//	//이용 후기 삭제
//	@RequestMapping(value = "/reviewDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
//	public String reviewDelete(ReviewDTO dto, HttpServletRequest req, HttpServletResponse resp) throws Exception{
//		
//		String pageNum = req.getParameter("pageNum");
//		
//		review_dao.deleteData(dto.getReview_id());
//		
//		String param = "pageNum=" + pageNum;
//		
//		return "redirect:/review.do?" + param;
//	}
//
//	//이용 후기 수정 페이지 진입
//	@RequestMapping(value = "/reviewEdit.do", method = {RequestMethod.GET})
//	public String reviewEdit(ReviewDTO dto, HttpServletRequest req, HttpServletResponse resp) throws Exception{
//		
//		HttpSession session = req.getSession();
//		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
//		
//		String pageNum = req.getParameter("pageNum");
//		
//		dto = review_dao.getReadData(dto.getReview_id());
//		
//		if (dto == null) {
//			return "redirect:/review.do";
//		}
//		
//		String param = "pageNum=" + pageNum;
//		
//		req.setAttribute("dto", dto);
//		req.setAttribute("pageNum", pageNum);
//		req.setAttribute("params", param);
//		
//		return "/health/reviewEdit";
//	}
//	
//	
//	//이용 후기 수정
//	@RequestMapping(value = "/reviewEdit_ok", method = RequestMethod.POST)
//	public String reviewEdit_ok(ReviewDTO dto, HttpServletRequest req) {
//
//		String pageNum = req.getParameter("pageNum");
//		
//		HttpSession session = req.getSession();
//		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
//		
//		dto.setMember_no(info.getMember_no());
//		
//		review_dao.updateData(dto);
//		
//		String param = "pageNum=" + pageNum + "&review_id=" + dto.getReview_id();
//		
//		return "redirect:/reviewArticle.do?" + param;
//	}
	
	
	
	

}
