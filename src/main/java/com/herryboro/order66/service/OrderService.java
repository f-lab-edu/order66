package com.herryboro.order66.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herryboro.order66.dto.order.Cart;
import com.herryboro.order66.dto.order.CartItem;
import com.herryboro.order66.dto.order.Order;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartMapper cartMapper;

    /* order 테이블 insert */
    @Transactional
    public void registerCart(Cart cart) {
        // 장바구니 추가 시 생성되어 있는 장바구니가 있는지 없는지 먼저 판별.
        // 장바구니가 없으면 cart를 새로 생성, 있으면 이미 존재하는 cart이므로 생성x (더 담으러 가기로 메뉴 추가 시 하위의 order itme, item option만 추가)
        // 장바구니 생성 후 다른 store의 상품 추가x
        Long storeId = cart.getStoreId();
        Cart existCart = cartMapper.checkHaveCart(cart.getClientId());

        if (existCart == null) {
            Long cartId = cartMapper.insertCart(cart);
            cart.setId(cartId);
        } else {
            cart.setId(existCart.getId());

            if (storeId != existCart.getStoreId()) {
                throw new InvalidInputException("장바구니에는 같은 가게의 메뉴만 담을수 있습니다.");
            }
        }

        /*
            ▣ 장바구니 생성 후 더 담으러 가기
             - 동일한 메뉴를 추가하면 cartitem에 수량만 증가
              - 여기서 말하는 동일한 메뉴란 메뉴만 같은게 아니라 옵션 조건까지 동일해야됨
              - 메뉴가 같더라도 옵션이 동일하지 않다면 다른 cartItem임.
              - UI 참고
         */

        CartItem exsitCartItem;

        // 장바구니에 메뉴를 담을때 옵션이 있는 경우 vs 옵션이 없는 경우
        if (cart.getOptions() == null || cart.getOptions().isEmpty()) {
            exsitCartItem = cartMapper.checkCartItemWithoutOptions(cart);
        } else {
            exsitCartItem = cartMapper.checkCartItemWithOptions(cart, cart.getOptions().size());
        }

        if (exsitCartItem == null) {
            Long cartItemId = cartMapper.insertCartItem(cart);
            cart.setCartItemId(cartItemId);

            if(cart.getOptions().size() != 0) {
                cartMapper.insertCartItemOption(cart);
            }
        } else {
            // 기존 장바구니 항목의 수량 업데이트
            cartMapper.updateCartItemQuantity(exsitCartItem.getId(), exsitCartItem.getItemQuantity() + cart.getPurchaseQuantity());
        }
    }

    @Transactional(rollbackFor = JsonProcessingException.class)
    public void registerOrder(Order order) {
        String orderOption = order.getOrderItemList().get(0).getOrderOption();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map map = objectMapper.readValue(orderOption, Map.class);
            String orderMenu = (String)map.get("orderMenu");
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("json 파싱 에러");
        }

    }
}
