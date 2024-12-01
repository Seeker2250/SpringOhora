package kr.ohora.www.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ohora.www.domain.AddrDTO;
import kr.ohora.www.domain.UserDTO;
import kr.ohora.www.domain.product.ProductDTO;
import kr.ohora.www.domain.security.CustomerUser;
import kr.ohora.www.service.order.OrderPageService;
import lombok.extern.log4j.Log4j;
@Log4j
@Controller
public class OrderPageController {
	@Autowired
	   private OrderPageService OrderService;
	   
	   @GetMapping("/order/page")
	   public String Order(
			   @RequestParam(required = false) int[] pdtId,
		       @RequestParam(required = false) int[] pdtCount,
		       Principal principal,
		       Model model) {
		       
		       System.out.println("OrderController...");
		       // Principal에서 userId 가져오기
		       CustomerUser customUser = (CustomerUser) ((Authentication) principal).getPrincipal();
		       log.info("userId : " + customUser.getUser().getUserId());
		       int userId = customUser.getUser().getUserId();
		    
		       
		       if (pdtId == null || pdtCount == null) {
		           return "redirect:/ohora/main";
		       }
		       
		      
		      
		       try {
		           // 회원 정보 조회
		           if (userId != 0) {
		               UserDTO userDTO = OrderService.getUserInfo(userId);
		               List<AddrDTO> addrList = OrderService.getAddrList(userId);
		               
		             //  List<CouponDTO> couponList = OrderService.getCouponList(userId);
		               
		               String email = userDTO.getUserEmail();
		               //String[] telArr = userDTO.getUserTel() != null ? 
		               //    userDTO.getUserTel().split("-") : new String[]{"", "", ""};
		               String tel = userDTO.getUserTel() != null ? userDTO.getUserTel() : "";
		               model.addAttribute("userDTO", userDTO);
		               model.addAttribute("addrList", addrList);
		              // model.addAttribute("couponList", couponList);
		               model.addAttribute("emailArr", email);
		               model.addAttribute("tel", tel);
		           }
		           
		           // 상품 정보 조회 
		           if (pdtId != null) {
		               List<ProductDTO> pdtList = OrderService.getProductList(pdtId);
		               model.addAttribute("pdtList", pdtList);
		           }
		           
		           model.addAttribute("pdtCount", pdtCount);
		           
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
		       
		       return "order.order";
	   }
}
