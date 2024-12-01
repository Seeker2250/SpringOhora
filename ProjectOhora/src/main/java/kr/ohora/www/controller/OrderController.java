package kr.ohora.www.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.ohora.www.domain.order.OrderDTO;
import kr.ohora.www.domain.security.CustomerUser;
import kr.ohora.www.service.order.OrderService;
import lombok.extern.log4j.Log4j;
@Controller
@Log4j
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/order/result")
    public String order(
       //String rname,//이름
	   //String rzipcode1, //우편번호
	   String raddr1,// 주소
	   String raddr2,// 상세 주소
	   String rphone2_1,//번호1
	   String rphone2_2, //번호2
	   String rphone2_3,//번호3
	   //String email1,//이메일
	   //String addrPaymethod,//결제 수단
	  // String[] pdtNames,//상품 이름
	   //int[] pdtCounts,//상품 수량
	   //int[] pdtAmounts, //상품 원가
	   ////int[] pdtDcAmounts,//상품 할인가
	   //int[] pdtIds, //상품 아이디들
	   //int totalSum, //원가 총합
	   //int discountSum,//할인가 총합
	   //@RequestParam(value = "icpnId", required = false, defaultValue = "0") int icpnId, // 쿠폰 아이디
       //@RequestParam(value = "icpnDc", required = false, defaultValue = "0") int icpnDc, // 쿠폰 할인율
      //// @RequestParam(value = "input_point", required = false, defaultValue = "0") int inputPoint, // 적립금
       @ModelAttribute OrderDTO order, 
       Principal principal,
       RedirectAttributes rttr) {
    	CustomerUser customUser = (CustomerUser) ((Authentication) principal).getPrincipal();
	    log.info("userId는 뭐냐면 " + customUser.getUser().getUserId());
	    int userId = customUser.getUser().getUserId();
	    order.setUserId(userId);
	    
        try {
        	//주소 합쳐서 재구성
        	 String address = raddr1 + " " + raddr2;
        	 order.setAddress(address);
        	 //번호 합쳐서 다시 만드러
        	 String phone = rphone2_1 + "-" + rphone2_2 + "-" + rphone2_3;
        	 order.setPhone(phone);
        	 // 배송비 계산
             int deliFee = (order.getTotalSum() - order.getDiscountSum() - order.getIcpnDc()) > 50000 ? 0 : 3000;
             order.setDeliveryFee(deliFee);
            
            //String order(int userId, int icpnId, String ordName, String address, String zipCode,
               //     String phone, String email, String password, int totalAmount, int cpnDiscount,
                 //   int pdtDiscount, int usePoint, String payOption, int deliveryFee,
                   // String[] pdtNames, int[] pdtCounts, int[] pdtAmounts, int[] pdtDcAmounts, int[] pdtIds);
            
            String orderId = orderService.order(order);
            
            //주문 시간 형식 정해놔
            LocalDateTime orderTime = LocalDateTime.now();
            String formattedTime = orderTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            //한 번만 쓰고 db에 넣으면 돼
            rttr.addAttribute("orderId", orderId);
            rttr.addAttribute("orderTime", formattedTime);
            
            return "redirect:/order/complete";
            
        } catch (Exception e) {
            log.error("주문 처리 실패", e);
            return "redirect:/order/fail";
        }
    }
}