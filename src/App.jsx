import React, { useState } from 'react';
import { CartProvider } from './context/CartContext';
import Navbar from './components/Navbar';
import CartDrawer from './components/CartDrawer';
import HomePage from './pages/HomePage';
import ShopPage from './pages/ShopPage';
import CheckoutPage from './pages/CheckoutPage';
import AboutPage from './pages/AboutPage';
import './App.css';

export default function App() {
  const [page, setPage] = useState('home');
  const [cartOpen, setCartOpen] = useState(false);

  const renderPage = () => {
    switch (page) {
      case 'home': return <HomePage setPage={setPage} />;
      case 'shop': return <ShopPage />;
      case 'checkout': return <CheckoutPage setPage={setPage} />;
      case 'about': return <AboutPage setPage={setPage} />;
      default: return <HomePage setPage={setPage} />;
    }
  };

  return (
    <CartProvider>
      <div className="app">
        <Navbar page={page} setPage={setPage} setCartOpen={setCartOpen} />
        <CartDrawer open={cartOpen} onClose={() => setCartOpen(false)} setPage={setPage} />
        <main className="main-content">
          {renderPage()}
        </main>
      </div>
    </CartProvider>
  );
}
