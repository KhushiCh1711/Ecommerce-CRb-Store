import React from 'react';

export default function AboutPage({ setPage }) {
  return (
    <div className="about-page">
      <section className="about-hero">
        <div className="about-bg">
          <div className="orb orb1" />
          <div className="orb orb2" />
        </div>
        <h1>Our <span className="gradient-text">Story</span></h1>
        <p className="about-tagline">Built on passion. Driven by quality. Defined by you.</p>
      </section>

      <section className="about-mission">
        <div className="mission-text">
          <h2>Who We Are</h2>
          <p>LuxeStore was founded in 2020 by a group of product enthusiasts who believed that premium quality shouldn't come with a premium compromise. We curate only the finest products — from precision-crafted watches to cutting-edge tech accessories — so you never have to settle.</p>
          <p>Every item on our platform is hand-picked, quality-tested, and backed by our 30-day happiness guarantee. We're not just a store — we're a lifestyle upgrade.</p>
        </div>
        <div className="mission-visual">
          {[['◆','Quality First'],['⚡','Fast Shipping'],['♻️','Sustainable'],['💎','Exclusive']].map(([icon, label]) => (
            <div key={label} className="mission-card">
              <span>{icon}</span>
              <p>{label}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="team-section">
        <h2 className="section-title">Meet the <span className="gradient-text">Team</span></h2>
        <div className="team-grid">
          {[
            { name:'Aryan Sharma', role:'Founder & CEO', emoji:'👨‍💼' },
            { name:'Priya Kapoor', role:'Head of Curation', emoji:'👩‍🎨' },
            { name:'Rahul Mehta', role:'Lead Engineer', emoji:'👨‍💻' },
            { name:'Neha Singh', role:'Customer Success', emoji:'👩‍💼' },
          ].map(m => (
            <div key={m.name} className="team-card">
              <div className="team-avatar">{m.emoji}</div>
              <h3>{m.name}</h3>
              <p>{m.role}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="values-section">
        <h2 className="section-title">Our <span className="gradient-text">Values</span></h2>
        <div className="values-grid">
          {[
            { icon:'🎯', title:'Precision', desc:'Every product is chosen with care and tested for quality.' },
            { icon:'🤝', title:'Trust', desc:'Transparent pricing, honest reviews, genuine products always.' },
            { icon:'🌱', title:'Sustainability', desc:'We partner with brands committed to reducing their footprint.' },
            { icon:'🚀', title:'Innovation', desc:'We\'re always searching for the next great product to offer you.' },
          ].map(v => (
            <div key={v.title} className="value-card">
              <span>{v.icon}</span>
              <h3>{v.title}</h3>
              <p>{v.desc}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="cta-banner">
        <div className="cta-content">
          <h2>Ready to experience the <span className="gradient-text">difference?</span></h2>
          <p>Shop our curated collection and discover why 10,000+ customers love LuxeStore.</p>
          <button className="primary-btn" onClick={() => setPage('shop')}>Start Shopping →</button>
        </div>
      </section>
    </div>
  );
}
