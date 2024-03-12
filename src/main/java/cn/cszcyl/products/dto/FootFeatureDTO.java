package cn.cszcyl.products.dto;

import java.util.List;
import lombok.Data;

/**
* @Description aaa
* @Author mtxst
* @Date 2024/1/7 5:03 $
*/
@Data
public class FootFeatureDTO {


    private String image;

    private String price;

    private List<FootTxtDesc> featureParamList;
}
