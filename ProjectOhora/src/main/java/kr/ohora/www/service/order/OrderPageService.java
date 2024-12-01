package kr.ohora.www.service.order;

import java.util.List;

import kr.ohora.www.domain.AddrDTO;
import kr.ohora.www.domain.UserDTO;
import kr.ohora.www.domain.product.ProductDTO;


public interface OrderPageService {
    UserDTO getUserInfo(int userId);
    List<AddrDTO> getAddrList(int userId);
   // List<CouponDTO> getCouponList(int userId);
    List<ProductDTO> getProductList(int[] pdtIds);
}

