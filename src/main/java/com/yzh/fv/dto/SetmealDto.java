package com.yzh.fv.dto;

import com.yzh.fv.entity.Setmeal;
import com.yzh.fv.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    private String dishName;

    //图片
    private String image;


    //描述信息
    private String description;

}
