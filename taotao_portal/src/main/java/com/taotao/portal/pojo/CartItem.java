package com.taotao.portal.pojo;
/**
 * 
 * @ClassName:  CartItem   
 * @Description:为购物车封装的商品实体类
 * @author: hanzeyu
 * @date:   2020年12月27日 下午9:29:46      
 * @Copyright:
 */
public class CartItem {

	private Long id;

    private String title;

    private Long price;

    private Integer num;

    private String image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
