package kr.ohora.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ohora.www.domain.product.ProductDTO;
import kr.ohora.www.service.ProductService;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class ProductController {
	
	@Autowired
    private ProductService productService;

    @GetMapping("/prdList.htm")
    public String prdList(
        @RequestParam(defaultValue = "1") int currentPage,
        @RequestParam(defaultValue = "12") int numberPerPage,
        @RequestParam(defaultValue = "0") int categoryNumber,
        Model model) {
        // 상품 리스트와 총 레코드 수 가져오기
        List<ProductDTO> productList = productService.getProductList(currentPage, numberPerPage, categoryNumber);
        int totalRecords = productService.getTotalRecords(categoryNumber);

        // 모델에 데이터 추가
        model.addAttribute("list", productList);
        model.addAttribute("totalRecords", totalRecords);

        return "product.prdList"; // `WEB-INF/views/product/prdList.jsp`
    }
}
