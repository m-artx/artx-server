package com.artx.artx.product.entity;

import com.artx.artx.common.model.BaseEntity;
import jakarta.persistence.*;

@Entity
public class ProductCategory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

//하위 카테고리 생성 시 사용
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "parent_category_id")
//	private ProductCategory productCategory;

}
