package com.elifly.test;

import com.eliefly.utils.PinYin4jUtils;

/**
 * ClassName:PinyinTest <br/>
 * Function: <br/>
 * Date: 2017年12月2日 上午12:10:28 <br/>
 */
public class PinyinTest {

    public static void main(String[] args) {

        String province = "广东省";
        String city = "深圳市";
        String district = "朝阳区";

        province = province.substring(0, province.length() - 1);
        city = city.substring(0, city.length() - 1);
        district = district.substring(0, district.length() - 1);

        String[] headByString =
                PinYin4jUtils.getHeadByString(province + city + district);

        String code = PinYin4jUtils.stringArrayToString(headByString, "");

        System.out.println(code);

        String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
        System.out.println(cityCode);

    }

}
