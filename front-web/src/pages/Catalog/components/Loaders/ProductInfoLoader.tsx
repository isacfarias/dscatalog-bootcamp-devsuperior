import React from 'react';
import ContentLoader from 'react-content-loader';

const ProductInfoLoader = () => (
  <ContentLoader 
    speed={1}
    width="100%"
    height={500}
    backgroundColor="#f1f2f2"
    foregroundColor="#E1E1E1"
  >
    <rect x="0" y="467" rx="2" ry="2" width="256" height="24" /> 
    <rect x="0" y="0" rx="2" ry="2" width="1200" height="400" /> 
    <rect x="0" y="423" rx="2" ry="2" width="351" height="24" />
  </ContentLoader>
)

export default ProductInfoLoader;