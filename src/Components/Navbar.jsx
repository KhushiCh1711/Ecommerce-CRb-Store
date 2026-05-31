import React, { useState, useEffect } from 'react';
import { useCart } from '../context/CartContext';

export default function Navbar({ page, setPage, setCartOpen }) {
  const { count } = useCart();
  const [scrolled, setScrolled] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const fn = () => setScrolled(window.scrollY > 20);
    window.addEventListener('scroll', fn);
    return () => window.removeEventListener('scroll', fn);
  }, []);

  return (
    <nav className={`navbar ${scrolled ? 'scrolled' : ''}`}>
      <div className="nav-inner">
        <div className="logo" onClick={() => setPage('home')}>
          <span className="logo-icon">◆</span>
          <span className="logo-text">LUXE<span>STORE</span></span>
        </div>
        <div className={`nav-links ${menuOpen ? 'open' : ''}`}>
          {['home','shop','about'].map(p => (
            <button key={p} className={`nav-link ${page===p?'active':''}`}
              onClick={() => { setPage(p); setMenuOpen(false); }}>
              {p.charAt(0).toUpperCase()+p.slice(1)}
            </button>
          ))}
        </div>
        <div className="nav-actions">
          <button className="cart-btn" onClick={() => setCartOpen(true)}>
            <span className="cart-icon">🛍️</span>
            {count > 0 && <span className="cart-badge">{count}</span>}
          </button>
          <button className="menu-toggle" onClick={() => setMenuOpen(!menuOpen)}>
            {menuOpen ? '✕' : '☰'}
          </button>
        </div>
      </div>
    </nav>
  );
}
