package com.hmsgtech.controller.old;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsgtech.domain.Category;
import com.hmsgtech.domain.FrontPage;
import com.hmsgtech.domain.goods.Goods;
import com.hmsgtech.repository.CategoryRepository;
import com.hmsgtech.repository.FrontPageRepository;
import com.hmsgtech.repository.GoodsRepository;
import com.hmsgtech.web.model.IndexModel;
import com.hmsgtech.web.model.SlideImageModel;

/**
 * Created by 晓丰 on 2016/5/10.
 */
@Controller
public class FrontPageController {

    @Autowired
    FrontPageRepository frontPageRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/frontPage")
    public @ResponseBody IndexModel index(@RequestParam(value = "token",required = false,defaultValue = "")String token) throws IOException {
        FrontPage next = frontPageRepository.findAll().iterator().next();
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType SlideImageArraylist = objectMapper.getTypeFactory()
				.constructParametrizedType(ArrayList.class, ArrayList.class,
						SlideImageModel.class);
		List<SlideImageModel> SlideImageModels = objectMapper.readValue(next.getImgs(), SlideImageArraylist);
        
        JavaType longArraylist = objectMapper.getTypeFactory().constructType(ArrayList.class, Integer.class);
        List<Integer> categoryIds=objectMapper.readValue(next.getCategories(),longArraylist);
        List<Category> categories=new ArrayList<>();
        for(long id:categoryIds){
            categories.add(categoryRepository.findOne(id));
        }
        List<Goods> goodses=new ArrayList<>();
        if(!StringUtils.isEmpty(next.getGoodsIds())){
            List<Integer> goodsIds =objectMapper.readValue(next.getGoodsIds(),longArraylist);
            for(int goods:goodsIds){
                Goods one = goodsRepository.findGoodsByIdAndIsSell((long) goods);
                if(one!=null){
                    goodses.add(one);
                }
            }
        }
        IndexModel indexModel=new IndexModel(SlideImageModels,categories,goodses);
        return indexModel;
    }

}
