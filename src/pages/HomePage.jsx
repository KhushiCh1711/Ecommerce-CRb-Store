import React from 'react';

export default function HomePage({ setPage }) {
  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero">
        <div className="hero-bg">
          <div className="orb orb1" />
          <div className="orb orb2" />
          <div className="orb orb3" />
          <div className="grid-lines" />
        </div>
        <div className="hero-content">
          <div className="hero-tag">✦ New Collection 2025</div>
          <h1 className="hero-title">
            Elevate Your<br />
            <span className="gradient-text">Everyday</span><br />
            Essentials
          </h1>
          <p className="hero-subtitle">
            Premium products curated for those who demand quality,
            style, and purpose in everything they own.
          </p>
          <div className="hero-actions">
            <button className="primary-btn" onClick={() => setPage('shop')}>
              Shop Now →
            </button>
            <button className="secondary-btn" onClick={() => setPage('about')}>
              Our Story
            </button>
          </div>
          <div className="hero-stats">
            {[['10K+','Happy Customers'],['500+','Premium Products'],['4.9★','Average Rating']].map(([n,l]) => (
              <div key={l} className="stat">
                <span className="stat-num">{n}</span>
                <span className="stat-label">{l}</span>
              </div>
            ))}
          </div>
        </div>
        <div className="hero-visual">
          <div className="floating-card card1">
            <span>⌚</span>
            <div><strong>Obsidian Watch</strong><br /><small>$299</small></div>
          </div>
          <div className="floating-card card2">
            <span>🎧</span>
            <div><strong>Lumina Buds</strong><br /><small>$149</small></div>
          </div>
          <div className="floating-card card3">
            <span>⌨️</span>
            <div><strong>Drift Keyboard</strong><br /><small>$219</small></div>
          </div>
          <div className="hero-circle">
            <div className="inner-circle">◆</div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="features">
        {[
          { icon:'🚚', title:'Free Shipping', desc:'On all orders over $99' },
          { icon:'🔄', title:'Easy Returns', desc:'30-day hassle-free returns' },
          { icon:'🔒', title:'Secure Payment', desc:'256-bit SSL encryption' },
          { icon:'💬', title:'24/7 Support', desc:'Always here to help you' },
        ].map(f => (
          <div key={f.title} className="feature-card">
            <span className="feature-icon">{f.icon}</span>
            <div>
              <h4>{f.title}</h4>
              <p>{f.desc}</p>
            </div>
          </div>
        ))}
      </section>

      {/* Categories */}
      <section className="categories-section">
        <h2 className="section-title">Shop by <span className="gradient-text">Category</span></h2>
        <div className="categories-grid">
          {[
            { icon:'⌚', name:'Watches', count:12 },
            { icon:'🎧', name:'Audio', count:8 },
            { icon:'👟', name:'Footwear', count:15 },
            { icon:'⌨️', name:'Tech', count:20 },
            { icon:'🎒', name:'Bags', count:10 },
            { icon:'🕶️', name:'Accessories', count:18 },
          ].map(c => (
            <div key={c.name} className="category-card" onClick={() => setPage('shop')}>
              <div className="cat-icon">{c.icon}</div>
              <h3>{c.name}</h3>
              <p>{c.count} items</p>
              <span className="cat-arrow">→</span>
            </div>
          ))}
        </div>
      </section>

      {/* CTA Banner */}
      <section className="cta-banner">
        <div className="cta-content">
          <h2>Ready to discover something <span className="gradient-text">extraordinary?</span></h2>
          <p>Join over 10,000 customers who trust LuxeStore for their premium needs.</p>
          <button className="primary-btn" onClick={() => setPage('shop')}>Explore All Products →</button>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-inner">
          <div className="footer-logo">◆ LUXESTORE</div>
          <p>© 2025 LuxeStore. All rights reserved.</p>
          <div className="footer-links">
            {['Privacy Policy','Terms of Service','Contact Us'].map(l => (
              <a key={l} href="#">{l}</a>
            ))}
          </div>
        </div>
      </footer>
    </div>
  );
}
