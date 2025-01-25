package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.PromotionMapper;
import org.cssa.wxcloudrun.model.Promotion;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionMapper promotionMapper;

    /**
     * 创建新的推广活动。
     *
     * 该方法接收一个 Promotion 对象作为参数，包含了推广活动的所有必要信息，如类型、标签、图片URL、目标URL、起始日期和结束日期等。
     * 方法将这些信息用于在数据库中创建一个新的推广记录。如果创建成功，返回一个包含 true 的 Response 对象；
     * 如果创建过程中遇到任何错误，返回一个包含 false 的 Response 对象，并可能包含错误信息。
     *
     * @param promotion 包含推广活动详细信息的 Promotion 对象。
     * @return Response<Boolean> 如果推广活动成功创建，返回 true，否则返回 false。
     */
    @Override
    public Response<Boolean> createPromotion(Promotion promotion) {
        try {
            System.out.println(promotion);
            // 调用数据访问层将推广信息保存到数据库
            promotionMapper.createPromotion(promotion);
            // 返回成功响应
            return new Response<>(true );
        } catch (Exception e) {
            // 记录错误（根据实际情况选择日志记录方式）
            // 返回失败响应
            return new Response<>(false);
        }
    }

    /**
     * 获取正在进行中的推广活动列表。
     * <p>
     * 此方法返回所有当前日期内正在进行的推广活动。如果提供了类型参数（type），则只返回该特定类型的推广活动。
     * 如果类型参数为 null，则返回所有类型的正在进行的推广。
     *
     * @param type 推广活动的类型标识符（例如：0 代表广告，1 代表活动），如果为 null，则不按类型筛选。
     * @return Response<Promotion> 返回一个响应对象，包含符合条件的 Promotion 对象列表。
     */
    @Override
    public Response<List<Promotion>> getOngoingPromotions(Integer type) {
        return new Response<>(promotionMapper.getOngoingPromotions(type));
    }
}