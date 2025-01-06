package com.ecommerce.demo.mapper;

import com.ecommerce.demo.dto.OrderDTO;
import com.ecommerce.demo.model.UserOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO convertUserOrderToOrderDTO(UserOrder userOrder) {
        if(userOrder != null)
            return modelMapper.map(userOrder,OrderDTO.class);
        else
            return new OrderDTO();
    }

    public UserOrder convertOrderDTOToUserOrder(OrderDTO orderDTO) {
        if(orderDTO != null)
            return modelMapper.map(orderDTO, UserOrder.class);
        else
            return new UserOrder();
    }
}
