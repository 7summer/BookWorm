package cn.BookWorm.cart.service;

import cn.BookWorm.cart.dao.CartItemDao;
import cn.BookWorm.cart.domain.CartItem;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class CartItemService {
    private CartItemDao cartItemDao = new CartItemDao();

    /**
     * 添加条目
     * @param cartItem
     */
    public void add(CartItem cartItem)
    {
        try {
            CartItem cartItem1 = cartItemDao.findByUserIdAndBookId(cartItem.getUser().getUserId(),
                    cartItem.getBook().getBookId());

            if(cartItem1 == null)
            {
                //添加条目
                cartItem.setCartItemId(UUID.randomUUID().toString().replaceAll("-",""));
                cartItemDao.addCartItem(cartItem);
            }
            else
            {
                //更新条目的数量
                int quantity = cartItem.getQuantity() + cartItem1.getQuantity();
                cartItemDao.updateCartItemQuantity(cartItem1.getCartItemId(), quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过用户编号查找购物车条目
     * @param userId
     * @return
     */
    public List<CartItem> findByUserId(String userId)
    {
        try {
            return cartItemDao.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除购物车条目
     * @param cartItemIdArray
     */
    public void batchDeleteCartItem(Object[] cartItemIdArray)
    {
        try {
            cartItemDao.batchDeleteCartItem(cartItemIdArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新指定购物车条目的数量
     * @param cartItem
     */
    public void updateQuantity(CartItem cartItem)
    {
        try {
            cartItemDao.updateCartItemQuantity(cartItem.getCartItemId(), cartItem.getQuantity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载购物车条目，我的订单需要
     * @param cartItemArray
     * @return
     */
    public List<CartItem> loadCartItems(Object[] cartItemArray)
    {
        try {
            return cartItemDao.loadCartItems(cartItemArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
