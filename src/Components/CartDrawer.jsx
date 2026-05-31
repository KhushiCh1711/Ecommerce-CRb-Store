import React from 'react';
import { useCart } from '../context/CartContext';

export default function CartDrawer({ open, onClose, setPage }) {
  const { items, total, count, dispatch } = useCart();

  const checkout = () => {
    onClose();
    setPage('checkout');
  };

  return (
    <>
      <div className={`cart-overlay ${open ? 'visible' : ''}`} onClick={onClose} />
      <div className={`cart-drawer ${open ? 'open' : ''}`}>
        <div className="cart-header">
          <h2>Your Cart <span className="cart-count">{count}</span></h2>
          <button className="close-btn" onClick={onClose}>✕</button>
        </div>
        {items.length === 0 ? (
          <div className="cart-empty">
            <div className="empty-icon">🛍️</div>
            <p>Your cart is empty</p>
            <button className="shop-btn" onClick={onClose}>Browse Products</button>
          </div>
        ) : (
          <>
            <div className="cart-items">
              {items.map(item => (
                <div key={item.id} className="cart-item">
                  <div className="cart-item-emoji">{item.image}</div>
                  <div className="cart-item-info">
                    <p className="cart-item-name">{item.name}</p>
                    <p className="cart-item-price">${item.price}</p>
                  </div>
                  <div className="cart-item-qty">
                    <button onClick={() => dispatch({ type:'UPDATE_QTY', payload:{ id:item.id, qty:item.qty-1 }})}>−</button>
                    <span>{item.qty}</span>
                    <button onClick={() => dispatch({ type:'UPDATE_QTY', payload:{ id:item.id, qty:item.qty+1 }})}>+</button>
                  </div>
                  <button className="remove-btn" onClick={() => dispatch({ type:'REMOVE_ITEM', payload:item.id })}>✕</button>
                </div>
              ))}
            </div>
            <div className="cart-footer">
              <div className="cart-total">
                <span>Total</span>
                <span>${total.toFixed(2)}</span>
              </div>
              <button className="checkout-btn" onClick={checkout}>Proceed to Checkout →</button>
              <button className="clear-btn" onClick={() => dispatch({ type:'CLEAR_CART' })}>Clear Cart</button>
            </div>
          </>
        )}
      </div>
    </>
  );
}
