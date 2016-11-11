package com.hmsgtech.web.model;

import com.hmsgtech.domain.Category;
import com.hmsgtech.domain.goods.Goods;

import java.util.List;

/**
 * Created by 晓丰 on 2016/5/18.
 */
public class IndexModel {
    private List<SlideImageModel> SlideImages;
    private List<Category> categories;
    private List<Goods> goodses;

    public IndexModel(List<SlideImageModel> SlideImages, List<Category> categories, List<Goods> goodses) {
        this.SlideImages = SlideImages;
        this.categories = categories;
        this.goodses = goodses;
    }

    public List<SlideImageModel> getSlideImages() {
		return SlideImages;
	}

	public void setSlideImages(List<SlideImageModel> slideImages) {
		SlideImages = slideImages;
	}

	public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(List<Goods> goodses) {
        this.goodses = goodses;
    }
}
