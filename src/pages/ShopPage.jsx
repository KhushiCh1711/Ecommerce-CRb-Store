import React, { useState, useMemo } from 'react';
import ProductCard from '../components/ProductCard';
import { products, categories } from '../data/products';

export default function ShopPage() {
  const [activeCategory, setActiveCategory] = useState('All');
  const [search, setSearch] = useState('');
  const [sortBy, setSortBy] = useState('default');
  const [priceRange, setPriceRange] = useState(500);

  const filtered = useMemo(() => {
    let p = [...products];
    if (activeCategory !== 'All') p = p.filter(x => x.category === activeCategory);
    if (search) p = p.filter(x => x.name.toLowerCase().includes(search.toLowerCase()));
    p = p.filter(x => x.price <= priceRange);
    if (sortBy === 'price-asc') p.sort((a,b) => a.price - b.price);
    if (sortBy === 'price-desc') p.sort((a,b) => b.price - a.price);
    if (sortBy === 'rating') p.sort((a,b) => b.rating - a.rating);
    return p;
  }, [activeCategory, search, sortBy, priceRange]);

  return (
    <div className="shop-page">
      <div className="shop-header">
        <h1>Our <span className="gradient-text">Collection</span></h1>
        <p>{filtered.length} products found</p>
      </div>

      <div className="shop-layout">
        {/* Sidebar */}
        <aside className="sidebar">
          <div className="filter-section">
            <h3>Search</h3>
            <input
              className="search-input"
              placeholder="Search products..."
              value={search}
              onChange={e => setSearch(e.target.value)}
            />
          </div>

          <div className="filter-section">
            <h3>Categories</h3>
            <div className="category-list">
              {categories.map(c => (
                <button
                  key={c}
                  className={`cat-btn ${activeCategory === c ? 'active' : ''}`}
                  onClick={() => setActiveCategory(c)}
                >
                  {c}
                </button>
              ))}
            </div>
          </div>

          <div className="filter-section">
            <h3>Max Price: <span className="gradient-text">${priceRange}</span></h3>
            <input
              type="range" min="50" max="500" value={priceRange}
              onChange={e => setPriceRange(Number(e.target.value))}
              className="price-slider"
            />
            <div className="price-labels"><span>$50</span><span>$500</span></div>
          </div>

          <div className="filter-section">
            <h3>Sort By</h3>
            <select className="sort-select" value={sortBy} onChange={e => setSortBy(e.target.value)}>
              <option value="default">Default</option>
              <option value="price-asc">Price: Low → High</option>
              <option value="price-desc">Price: High → Low</option>
              <option value="rating">Top Rated</option>
            </select>
          </div>

          <button className="reset-btn" onClick={() => {
            setActiveCategory('All'); setSearch(''); setSortBy('default'); setPriceRange(500);
          }}>Reset Filters</button>
        </aside>

        {/* Products Grid */}
        <main className="products-main">
          {filtered.length === 0 ? (
            <div className="no-results">
              <div>😕</div>
              <h3>No products found</h3>
              <p>Try adjusting your filters</p>
            </div>
          ) : (
            <div className="products-grid">
              {filtered.map(p => <ProductCard key={p.id} product={p} />)}
            </div>
          )}
        </main>
      </div>
    </div>
  );
}
