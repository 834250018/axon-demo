package com.ywy.learn.web.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

import java.math.BigDecimal;

/**
 * 车商的竞拍VO
 * Auction Query Entry
 *
 * @author ve
 * @create 2018-02-05 上午12:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Message
public class AuctionDTO {

    @Index(0)
    private String id;

    // 此次竞拍第一名(最高)的报价
    @Index(1)
    private BigDecimal highestPrice;

    // 当前用户的排名
    @Index(2)
    private Integer ranking;

    // 当前用户的排名
    @Index(3)
    private Long time;

}