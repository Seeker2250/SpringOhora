package kr.ohora.www.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ohora.www.domain.AddrDTO;
import kr.ohora.www.domain.UserDTO;
import kr.ohora.www.domain.product.ProductDTO;
import kr.ohora.www.persistence.OrderPageMapper;

@Service
public class OrderPageServiceImpl implements OrderPageService{
	 @Autowired
	 private OrderPageMapper orderMapper;
	 
	@Override
	public List<AddrDTO> getAddrList(int userId) {
		return orderMapper.selectAddrList(userId);
	}

	/*
	 * @Override public List<CouponDTO> getCouponList(int userId) { return
	 * orderMapper.selectCouponList(userId); }
	 */

	@Override
	public List<ProductDTO> getProductList(int[] pdtIds) {
		return orderMapper.selectProductList(pdtIds);
	}

	@Override
	public UserDTO getUserInfo(int userId) {
		return orderMapper.selectUserInfo(userId);
	}

}
