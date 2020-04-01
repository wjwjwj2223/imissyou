package com.lin.imissyou.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;

    private String snapImg;
    private String snapTitle;
    private Object snapItems;
    private Object snapAddress;

    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;
}
