package com.health.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.health.dao.MemberDAO;
import com.health.dao.ReviewDAO;
import com.health.dto.CustomInfo;
import com.health.dto.ReviewDTO;
import com.health.util.MyUtil;

@Controller
public class ReviewController {
	// Test
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;

	@Autowired
	@Qualifier("reviewDAO")
	ReviewDAO review_dao;

	@Autowired
	MyUtil myUtil;

	//이용 후기 페이지 접근
	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public String review(HttpServletRequest req) throws Exception {
	
		String cp = req.getContextPath();
		
		String pageNum = req.getParameter("pageNum");
		String searchKey = req.getParameter("searchKey");
	    String searchValue = req.getParameter("searchValue");
		
	    if(searchValue == null) {
	        searchKey = "title";
	        searchValue = "";
	    } else if(req.getMethod().equalsIgnoreCase("GET")) {
	        searchValue = URLDecoder.decode(searchValue, "UTF-8");
	    }
	    
		int currentPage = 1;
		
		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		int dataCount = review_dao.getDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if (currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage - 1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		List<ReviewDTO> lists = review_dao.getLists(start, end, searchKey, searchValue);
		
		String listUrl = cp + "/review.do?searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); 
		
		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		String articleUrl = cp + "/reviewArticle.do?pageNum=" + currentPage + "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		
		req.setAttribute("lists", lists);
		req.setAttribute("pageIndexList", pageIndexList);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		
		return "/health/review";
	}
	
	//이용 후기 작성 페이지 진입
	@RequestMapping(value = "/reviewWritePage", method = RequestMethod.GET)
	public String reviewWritePage(ReviewDTO dto, HttpServletRequest req) {
		
		return "/health/reviewWrite";
	}
	
	
	
	
	//이용 후기 작성
	@RequestMapping(value = "/reviewWrite", method = RequestMethod.POST)
	public String reviewWrite(ReviewDTO dto, HttpServletRequest req) {

		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		dto.setMember_no(info.getMember_no());
		
		int maxNum = review_dao.getMaxNum();
		dto.setReview_id(maxNum + 1);

		review_dao.insertData(dto);

		return "redirect:/review.do";
	}
	

	
	@RequestMapping(value = "/reviewArticle.do", method = {RequestMethod.GET})
	public ModelAndView article(ReviewDTO dto,HttpServletRequest req,
	HttpServletResponse resp) throws Exception{
	
	String cp = req.getContextPath();
	
	String pageNum = req.getParameter("pageNum");
	String searchKey = req.getParameter("searchKey");
	String searchValue = req.getParameter("searchValue");
	
	if(searchValue!=null) {				
		if(req.getMethod().equalsIgnoreCase("GET")){
			searchValue = 
					URLDecoder.decode(searchValue, "UTF-8");
		}				
	}else {
		searchKey = "title";
		searchValue = "";
	}
	
	review_dao.updateHitCount(dto.getReview_id());

	dto = review_dao.getReadData(dto.getReview_id());
	
	if (dto==null) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/review.do");
		
		return mav;
	}
	
	dto.setContent(dto.getContent().replaceAll("\n\r", "\n"));
	
	String param = "pageNum=" + pageNum;
	if(searchValue!=null && !searchValue.equals("")) {
		param += "&searchKey=" + searchKey;
		param += "&searchValue=" + 
				URLEncoder.encode(searchValue, "UTF-8");
	}
	ModelAndView mav = new ModelAndView();
	
	mav.addObject("dto", dto);
	mav.addObject("params", param);
	mav.addObject("searchKey", searchKey);
	mav.addObject("searchValue", searchValue);
	mav.addObject("pageNum", pageNum);		
	
	mav.setViewName("/health/reviewArticle");	
	
	return mav;
	
	}
	
	//이용 후기 삭제
	@RequestMapping(value = "/reviewDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String reviewDelete(ReviewDTO dto, HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		String pageNum = req.getParameter("pageNum");
	    String searchKey = req.getParameter("searchKey");
	    String searchValue = req.getParameter("searchValue");
		
		review_dao.deleteData(dto.getReview_id());
		
		String param = "pageNum=" + pageNum + "&review_id=" + dto.getReview_id();
		
		if(searchValue!=null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + 
					URLEncoder.encode(searchValue, "UTF-8");
		}
		
		return "redirect:/review.do?" + param;
	}

	//이용 후기 수정 페이지 진입
	@RequestMapping(value = "/reviewEdit.do", method = {RequestMethod.GET})
	public String reviewEdit(ReviewDTO dto, HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		String pageNum = req.getParameter("pageNum");
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		if(searchValue!=null) {
			if(req.getMethod().equalsIgnoreCase("GET")){
				searchValue = 
						URLDecoder.decode(searchValue, "UTF-8");
			}	
		}else {
			searchKey = "title";
			searchValue = "";
		}
		
		dto = review_dao.getReadData(dto.getReview_id());
		
		if (dto == null) {
			return "redirect:/review.do";
		}
		
		//작성자만 수정가능
		if(dto.getMember_no() != info.getMember_no()) {
		    return "redirect:/review.do";
		}
		
		String param = "pageNum=" + pageNum;
		
		if(searchValue!=null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + 
					URLEncoder.encode(searchValue, "UTF-8");
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("params", param);
		req.setAttribute("searchKey", searchKey);
		req.setAttribute("searchValue", searchValue);	
		
		return "/health/reviewEdit";
	}
	
	
	//이용 후기 수정
	@RequestMapping(value = "/reviewEdit_ok", method = RequestMethod.POST)
	public String reviewEdit_ok(ReviewDTO dto, HttpServletRequest req) throws Exception{

		String pageNum = req.getParameter("pageNum");
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		dto.setMember_no(info.getMember_no());
		
		review_dao.updateData(dto);
		
		String param = "pageNum=" + pageNum + "&review_id=" + dto.getReview_id();
		
		if(searchValue!=null && !searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + 
					URLEncoder.encode(searchValue, "UTF-8");
		}
		
		return "redirect:/reviewArticle.do?" + param;
	}
	
	
	
	
	/*
	 * @RequestMapping(value = "/myPageEdit", method = RequestMethod.GET) public
	 * String myPageEdit() {
	 * 
	 * 
	 * return "/health/myPageEdit"; }
	 * 
	 * @RequestMapping(value = "/myPageEdit_ok", method = RequestMethod.POST) public
	 * String myPageEdit_ok(MemberDTO dto, HttpServletRequest req) {
	 * 
	 * try { // 업데이트 실행 member_dao.updateData(dto);
	 * 
	 * // 세션의 정보도 업데이트 HttpSession session = req.getSession(); CustomInfo info =
	 * (CustomInfo) session.getAttribute("customInfo"); if (info != null) { // BMI
	 * 재계산 double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) *
	 * 10000; dto.setBmi(Math.round(bmi * 10) / 10.0);
	 * 
	 * info.setName(dto.getName()); info.setPassword(dto.getPassword());
	 * info.setHeight(dto.getHeight()); info.setWeight(dto.getWeight());
	 * info.setBmi(dto.getBmi()); info.setBirth(dto.getBirth());
	 * info.setGoal(dto.getGoal());
	 * 
	 * session.setAttribute("customInfo", info); }
	 * 
	 * 
	 * } catch (Exception e) { System.out.println("업데이트 중 오류 발생: " +
	 * e.getMessage()); e.printStackTrace(); }
	 * 
	 * 
	 * // 리다이렉트로 변경 (POST-Redirect-GET 패턴) return "redirect:/myPage.do";
	 * 
	 * 
	 * System.out.println("Controller 시작"); System.out.println("DAO 호출 전: " +
	 * dto.getMember_id()); member_dao.updateData(dto);
	 * 
	 * System.out.println("DAO 호출 후"); System.out.println("Controller 끝"); return
	 * "health/myPage";
	 * 
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/delete_ok", method = {RequestMethod.POST}) public
	 * String delete_ok(HttpServletRequest req, HttpServletResponse resp) {
	 * 
	 * int member_no = Integer.parseInt(req.getParameter("member_no")); String
	 * member_id = req.getParameter("member_id"); String password =
	 * req.getParameter("password");
	 * 
	 * MemberDTO dto = member_dao.getReadData(member_id);
	 * 
	 * if (password == "") {
	 * 
	 * req.setAttribute("message", "비밀번호를 입력해주세요.");
	 * 
	 * return "/health/myPage";
	 * 
	 * }
	 * 
	 * if(!dto.getPassword().equals(password)) {
	 * 
	 * req.setAttribute("message", "비밀번호를 정확히 입력해주세요");
	 * 
	 * return "/health/myPage"; }
	 * 
	 * member_dao.deleteData(member_no);
	 * 
	 * HttpSession session = req.getSession();
	 * session.removeAttribute("customInfo"); session.invalidate();
	 * 
	 * return "redirect:/main.do";
	 * 
	 * }
	 * 
	 * 
	 * @RequestMapping(value = "/goalUpdate", method = RequestMethod.POST) public
	 * String goalUpdate(MemberDTO dto, HttpServletRequest req) {
	 * 
	 * try { // 업데이트 실행 member_dao.updateGoal(dto);
	 * 
	 * // 세션의 정보도 업데이트 HttpSession session = req.getSession(); CustomInfo info =
	 * (CustomInfo) session.getAttribute("customInfo"); if (info != null) {
	 * 
	 * info.setGoal(dto.getGoal());
	 * 
	 * session.setAttribute("customInfo", info); }
	 * 
	 * 
	 * } catch (Exception e) { System.out.println("업데이트 중 오류 발생: " +
	 * e.getMessage()); e.printStackTrace(); }
	 * 
	 * // 리다이렉트로 변경 (POST-Redirect-GET 패턴) return "redirect:/main.do";
	 * 
	 * }
	 */

}
