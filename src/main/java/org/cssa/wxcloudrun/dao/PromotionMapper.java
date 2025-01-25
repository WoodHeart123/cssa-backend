package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.Promotion;
import java.util.List;

/**
 * PromotionMapper 接口提供了数据库访问功能，用于获取和管理与推广活动相关的数据。
 * 此接口利用 MyBatis 的映射机制将方法调用转换为数据库查询。
 */
@Mapper
public interface PromotionMapper {
    /**
     * 在数据库中创建一个新的推广活动。
     *
     * @param promotion 包含推广活动详细信息的 Promotion 对象。
     */
    void createPromotion(Promotion promotion);

    /**
     * 根据推广类型获取正在进行中的推广活动列表。
     *
     * @param type 推广活动的类型标识（例如：0代表广告，1代表活动），如果为null，则返回所有类型的活动。
     * @return List<Promotion> 返回符合条件的 Promotion 实体列表。
     */
    List<Promotion> getOngoingPromotions(@Param("type") Integer type);
}
