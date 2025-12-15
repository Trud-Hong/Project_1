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

import com.health.dao.BoardDAO;
import com.health.dao.MemberDAO;
import com.health.dto.BoardDTO;
import com.health.dto.CustomInfo;
import com.health.dto.ReviewDTO;
import com.health.util.MyUtil;

@Controller
public class CommunityController {
	// Test1
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;

	@Autowired
	@Qualifier("boardDAO")
	BoardDAO board_dao;

	@Autowired
	MyUtil myUtil;

	//커뮤니티 페이지 접근
	@RequestMapping(value = "/community", method = RequestMethod.GET) 
	public String community(HttpServletRequest req) throws Exception {
		
		 HttpSession session = req.getSession();
		    CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

		    String cp = req.getContextPath();
		    String pageNum = req.getParameter("pageNum");
		    String category = req.getParameter("category");
		    String searchKey = req.getParameter("searchKey");
		    String searchValue = req.getParameter("searchValue");

		    if(category == null) {
		    	category = "공지사항";
		    	System.out.println("카테고리: " + category);
		    }
		    if(searchValue == null) {
		        searchKey = "title";
		        searchValue = "";
		    } else if(req.getMethod().equalsIgnoreCase("GET")) {
		        searchValue = URLDecoder.decode(searchValue, "UTF-8");
		    }

		    int currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
		    int dataCount = board_dao.getDataCount(category, searchKey, searchValue);

		    int numPerPage = 5;
		    int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		    if(currentPage > totalPage) currentPage = totalPage;

		    int start = (currentPage - 1) * numPerPage + 1;
		    int end = currentPage * numPerPage;

		    List<BoardDTO> lists = board_dao.getLists(start, end, category, searchKey, searchValue);

		    String listUrl = cp + "/community.do?category=" + URLEncoder.encode(category, "UTF-8") +
		                     "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		    String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);

		    String articleUrl = cp + "/communityArticle.do?pageNum=" + currentPage + "&category=" + URLEncoder.encode(category, "UTF-8") +
		                        "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		    System.out.println("listUrl: " + listUrl);
		    req.setAttribute("lists", lists);
		    req.setAttribute("pageIndexList", pageIndexList);
		    req.setAttribute("dataCount", dataCount);
		    req.setAttribute("articleUrl", articleUrl);

		    return "/health/community"; 
	}

	// 게시글 작성 페이지 진입
	@RequestMapping(value = "/communityWrite", method = RequestMethod.GET)
	public String communityWrite(HttpServletRequest req) {

		return "/health/communityWrite";
	}

	// 게시글 작성
	@RequestMapping(value = "/communityWrite_ok", method = RequestMethod.POST)
	public String communityWrite_ok(BoardDTO dto, HttpServletRequest req) {

		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

		dto.setMember_no(info.getMember_no());

		int maxNum = board_dao.getMaxNum();

		dto.setBoard_id(maxNum + 1);

		board_dao.insertData(dto);

		return "redirect:/community.do";
	}

	
	//커뮤니티 게시글 상세 페이지 진입
//	@RequestMapping(value = "/communityArticle.do", method = {RequestMethod.GET})
//	public String article(BoardDTO dto, HttpServletRequest req,
//	HttpServletResponse resp) throws Exception{
//	
//	String cp = req.getContextPath();
//	
//	String pageNum = req.getParameter("pageNum");
//	
//	//board_dao.updateHitCount(dto.getBoard_id());
//	
//	dto = board_dao.getReadData(dto.getBoard_id());
//	
//	if (dto==null) {
//		return "redirect:/community.do";
//	}
//	
//	dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));
//	
//	String param = "pageNum=" + pageNum;
//	
//	req.setAttribute("dto",dto); req.setAttribute("params", param);
//	req.setAttribute("pageNum", pageNum);
//	
//	return "/health/communityArticle"; 
//	
//	}
	
	@RequestMapping(value = "/communityArticle.do", method = {RequestMethod.GET})
	public ModelAndView article(BoardDTO dto,HttpServletRequest req,
	HttpServletResponse resp) throws Exception{
	
	String cp = req.getContextPath();
	
	String pageNum = req.getParameter("pageNum");
	String category = req.getParameter("category");
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
	
	dto = board_dao.getReadData(dto.getBoard_id());
	
	if (dto==null) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/community.do");
		
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
	mav.addObject("category", category);
	mav.addObject("pageNum", pageNum);		
	
	mav.setViewName("/health/communityArticle");	
	
	return mav;
	
	}
	
	
	//이용 후기 삭제
	@RequestMapping(value = "/communityDelete.do", method = {RequestMethod.GET,
	RequestMethod.POST}) public String reviewDelete(BoardDTO dto,
	HttpServletRequest req, HttpServletResponse resp) throws Exception{
	
	String pageNum = req.getParameter("pageNum");
	
	board_dao.deleteData(dto.getBoard_id());
	
	String param = "pageNum=" + pageNum;
	
	return "redirect:/community.do?" + param; }
	
	//게시글 수정 페이지 진입
//	@RequestMapping(value = "/communityEdit.do", method = {RequestMethod.GET})
//	public String reviewEdit(BoardDTO dto, HttpServletRequest req,
//	HttpServletResponse resp) throws Exception{
//	
//	HttpSession session = req.getSession(); CustomInfo info = (CustomInfo)
//	session.getAttribute("customInfo");
//	
//	String pageNum = req.getParameter("pageNum");
//	
//	dto = board_dao.getReadData(dto.getBoard_id());
//	
//	if (dto == null) { return "redirect:/community.do"; }
//	
//	String param = "pageNum=" + pageNum;
//	
//	req.setAttribute("dto", dto); req.setAttribute("pageNum", pageNum);
//	req.setAttribute("params", param);
//	
//	return "/health/communityEdit"; }
	
	@RequestMapping(value = "/communityEdit.do", method = {RequestMethod.GET})
	public String reviewEdit(BoardDTO dto, HttpServletRequest req,
	HttpServletResponse resp) throws Exception{
	
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
	
	dto = board_dao.getReadData(dto.getBoard_id());
	
	if (dto == null || dto.getBoard_id() == 0) {
		
		return "redirect:/community.do"; 
	}
	
	//작성자만 수정가능
	if(dto.getMember_no() != info.getMember_no()) {
	    return "redirect:/community.do";
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
	
	return "/health/communityEdit"; 
	}
	
	
	//이용 후기 수정
	@RequestMapping(value = "/communityEdit_ok", method = RequestMethod.POST) 
	public String communityEdit_ok(BoardDTO dto, HttpServletRequest req) throws Exception{
	
	String pageNum = req.getParameter("pageNum");
	String searchKey = req.getParameter("searchKey");
	String searchValue = req.getParameter("searchValue");
	
	HttpSession session = req.getSession(); 
	CustomInfo info = (CustomInfo)
	session.getAttribute("customInfo");
	
	dto.setMember_no(info.getMember_no());
	
	board_dao.updateData(dto);
	
	String param = "pageNum=" + pageNum + "&board_id=" + dto.getBoard_id();
	
	if(searchValue!=null && !searchValue.equals("")) {
		param += "&searchKey=" + searchKey;
		param += "&searchValue=" + 
				URLEncoder.encode(searchValue, "UTF-8");
	}
	
	return "redirect:/communityArticle.do?" + param; }
	

}
