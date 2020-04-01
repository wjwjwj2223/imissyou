package com.lin.imissyou.logic;

import com.lin.imissyou.core.enumeration.CouponType;
import com.lin.imissyou.core.money.IMoneyDiscount;
import com.lin.imissyou.exception.http.ForbiddenException;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.model.Coupon;
import com.lin.imissyou.model.UserCoupon;
import com.lin.imissyou.util.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import sun.util.resources.is.CalendarData_is;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class CouponChecker {

    private Coupon coupon;
    private UserCoupon userCoupon;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    private void isOk() {
        Date now = new Date();
        Boolean isExpired = CommonUtil.isInTimeLine(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isExpired) {
            throw new ForbiddenException(40005);
        }
    }

    private void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                     BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice,this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);

        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }

    }

}
