package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.Promotion;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * PromotionService 接口定义了与推广活动相关的服务操作。
 * 它旨在为应用程序提供一个清晰的服务层，用于处理与推广活动相关的业务逻辑。
 */
@Service
public interface PromotionService {
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
    Response<Boolean> createPromotion(Promotion promotion);

    /**
     * 获取正在进行中的主页推广活动列表。
     *
     * @return Response<Promotion> 返回一个响应对象，包含符合条件的 Promotion 对象列表。
     */
    Response<List<Promotion>>  getOngoingMainPromotions(@Nullable Integer type);

    /**
     * 获取正在进行中的页面推广活动列表。此方法返回所有当前日期内正在进行的推广活动。
     *
     * @return Response<Promotion> 返回一个响应对象，包含符合条件的 Promotion 对象列表。
     */
    Response<List<Promotion>> getOngoingPagePromotions();
}
