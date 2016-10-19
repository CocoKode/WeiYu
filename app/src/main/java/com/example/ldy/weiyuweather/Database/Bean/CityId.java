package com.example.ldy.weiyuweather.Database.Bean;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by LDY on 2016/10/7.
 * 建立数据库
 */
@Table (name = "city_id")
public class CityId extends SugarRecord {
    String citySpell;
    String cityName;
    String cityId;

    //默认的必要构造方法
    public CityId() {}

    public CityId(String cityId, String citySpell, String cityName) {
        this.citySpell = citySpell;
        this.cityName = cityName;
        this.cityId = cityId;
    }
}
