import React, { useState } from 'react';
import { useCart } from '../context/CartContext';

export default function CheckoutPage({ setPage }) {
  const { items, total, dispatch } = useCart();
  const [form, setForm] = useState({ name:'', email:'', address:'', city:'', zip:'', card:'', expiry:'', cvv:'' });
  const [step, setStep] = useState(1);
  const [ordered, setOrdered] = useState(false);
  const [errors, setErrors] = useState({});

  const validate = () => {
    const e = {};
    if (!form.name) e.name = 'Required';
    if (!form.email || !form.email.includes('@')) e.email = 'Valid email required';
    if (!form.address) e.address = 'Required';
    if (!form.city) e.city = 'Required';
    if (!form.zip) e.zip = 'Required';
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const validatePayment = () => {
    const e = {};
    if (!form.card || form.card.replace(/\s/g,'').length < 16) e.card = 'Valid card number required';
    if (!form.expiry) e.expiry = 'Required';
    if (!form.cvv || form.cvv.length < 3) e.cvv = 'Valid CVV required';
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const placeOrder = () => {
    if (!validatePayment()) return;
    setOrdered(true);
    dispatch({ type: 'CLEAR_CART' });
  };

  const f = (k, v) => setForm(prev => ({ ...prev, [k]: v }));

  if (ordered) return (
    <div className="checkout-page">
      <div className="order-success">
        <div className="success-icon">✅</div>
        <h1>Order Placed!</h1>
        <p>Thank you, <strong>{form.name}</strong>! Your order will arrive in 3–5 business days.</p>
        <p className="order-num">Order #LX{Math.floor(Math.random()*100000)}</p>
        <button className="primary-btn" onClick={() => setPage('home')}>Back to Home</button>
      </div>
    </div>
  );

  if (items.length === 0) return (
    <div className="checkout-page">
      <div className="order-success">
        <div className="success-icon">🛍️</div>
        <h1>Cart is Empty</h1>
        <button className="primary-btn" onClick={() => setPage('shop')}>Shop Now</button>
      </div>
    </div>
  );

  return (
    <div className="checkout-page">
      <h1 className="checkout-title">Checkout</h1>
      <div className="checkout-steps">
        {['Shipping','Payment','Review'].map((s, i) => (
          <div key={s} className={`step ${step === i+1 ? 'active' : step > i+1 ? 'done' : ''}`}>
            <div className="step-num">{step > i+1 ? '✓' : i+1}</div>
            <span>{s}</span>
          </div>
        ))}
      </div>

      <div className="checkout-layout">
        <div className="checkout-form">
          {step === 1 && (
            <div className="form-section">
              <h2>Shipping Details</h2>
              {[['name','Full Name','text'],['email','Email','email'],['address','Street Address','text'],['city','City','text'],['zip','ZIP Code','text']].map(([k,label,type]) => (
                <div key={k} className="form-group">
                  <label>{label}</label>
                  <input type={type} value={form[k]} onChange={e => f(k, e.target.value)} className={errors[k] ? 'error' : ''} placeholder={label} />
                  {errors[k] && <span className="error-msg">{errors[k]}</span>}
                </div>
              ))}
              <button className="primary-btn" onClick={() => { if (validate()) setStep(2); }}>Continue to Payment →</button>
            </div>
          )}

          {step === 2 && (
            <div className="form-section">
              <h2>Payment Details</h2>
              <div className="form-group">
                <label>Card Number</label>
                <input type="text" maxLength="19" placeholder="1234 5678 9012 3456"
                  value={form.card} className={errors.card ? 'error' : ''}
                  onChange={e => { let v = e.target.value.replace(/\D/g,'').match(/.{1,4}/g); f('card', v ? v.join(' ') : ''); }} />
                {errors.card && <span className="error-msg">{errors.card}</span>}
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label>Expiry Date</label>
                  <input type="text" placeholder="MM/YY" maxLength="5" value={form.expiry} className={errors.expiry ? 'error' : ''}
                    onChange={e => { let v = e.target.value.replace(/\D/g,''); if (v.length >= 2) v = v.slice(0,2)+'/'+v.slice(2); f('expiry', v); }} />
                  {errors.expiry && <span className="error-msg">{errors.expiry}</span>}
                </div>
                <div className="form-group">
                  <label>CVV</label>
                  <input type="text" placeholder="123" maxLength="4" value={form.cvv} className={errors.cvv ? 'error' : ''}
                    onChange={e => f('cvv', e.target.value.replace(/\D/g,''))} />
                  {errors.cvv && <span className="error-msg">{errors.cvv}</span>}
                </div>
              </div>
              <div className="btn-row">
                <button className="secondary-btn" onClick={() => setStep(1)}>← Back</button>
                <button className="primary-btn" onClick={() => { if (validatePayment()) setStep(3); }}>Review Order →</button>
              </div>
            </div>
          )}

          {step === 3 && (
            <div className="form-section">
              <h2>Review Order</h2>
              <div className="review-items">
                {items.map(item => (
                  <div key={item.id} className="review-item">
                    <span>{item.image} {item.name}</span>
                    <span>×{item.qty} = ${(item.price * item.qty).toFixed(2)}</span>
                  </div>
                ))}
              </div>
              <div className="review-info">
                <p><strong>Ship to:</strong> {form.name}, {form.address}, {form.city}</p>
                <p><strong>Email:</strong> {form.email}</p>
                <p><strong>Card:</strong> •••• {form.card.slice(-4)}</p>
              </div>
              <div className="btn-row">
                <button className="secondary-btn" onClick={() => setStep(2)}>← Back</button>
                <button className="primary-btn" onClick={placeOrder}>Place Order ✓</button>
              </div>
            </div>
          )}
        </div>

        <div className="order-summary">
          <h3>Order Summary</h3>
          {items.map(item => (
            <div key={item.id} className="summary-item">
              <span>{item.image} {item.name} ×{item.qty}</span>
              <span>${(item.price*item.qty).toFixed(2)}</span>
            </div>
          ))}
          <div className="summary-divider" />
          <div className="summary-row"><span>Subtotal</span><span>${total.toFixed(2)}</span></div>
          <div className="summary-row"><span>Shipping</span><span className="free">Free</span></div>
          <div className="summary-row total"><span>Total</span><span>${total.toFixed(2)}</span></div>
        </div>
      </div>
    </div>
  );
}
