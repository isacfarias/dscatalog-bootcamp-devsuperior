import React from "react"
import ContentLoader from "react-content-loader"
import { generatorList } from "../../../../core/utils/list"

type Props = {
  listPerPage: number;
}
const ProductCardLoader = ({listPerPage}: Props ) => {
  const loadItens = generatorList(listPerPage);
  return (
    <>
      {
        loadItens.map(item =>
          <ContentLoader
            key={item}
            speed={1}
            width={250}
            height={335}
            viewBox="0 0 250 335"
            backgroundColor="#f1f2f2"
            foregroundColor="#E1E1E1"
          >
            <rect x="0" y="0" rx="10" ry="10" width="250" height="335" />
          </ContentLoader>
        )}
    </>
  );
}

export default ProductCardLoader

