import React, { useState } from 'react';
import { useCart } from '../context/CartContext';

export default function ProductCard({ product }) {
  const { dispatch } = useCart();
  const [added, setAdded] = useState(false);

  const handleAdd = () => {
    dispatch({ type: 'ADD_ITEM', payload: product });
    setAdded(true);
    setTimeout(() => setAdded(false), 1200);
  };

  const stars = '★'.repeat(Math.floor(product.rating)) + (product.rating % 1 >= 0.5 ? '½' : '') + '☆'.repeat(5 - Math.ceil(product.rating));

  return (
    <div className="product-card">
      {product.badge && <span className="badge">{product.badge}</span>}
      <div className="product-emoji">{product.image}</div>
      <div className="product-info">
        <span className="product-category">{product.category}</span>
        <h3 className="product-name">{product.name}</h3>
        <p className="product-desc">{product.desc}</p>
        <div className="product-rating">
          <span className="stars">{stars.slice(0,5)}</span>
          <span className="reviews">({product.reviews})</span>
        </div>
        <div className="product-footer">
          <span className="product-price">${product.price}</span>
          <button className={`add-btn ${added ? 'added' : ''}`} onClick={handleAdd}>
            {added ? '✓ Added' : '+ Cart'}
          </button>
        </div>
      </div>
    </div>
  );
}
