package com.lin.imissyou.model;

import com.lin.imissyou.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private String email;
    private String password;
    private String mobile;

    private Integer unifyUid;
//    private String group;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "UserCoupon",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "couponId"))
//    private List<Coupon> couponList;

    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "user", fetch = FetchType.LAZY)

//    @OneToMany
//    @JoinColumn(name="userId")
//    private List<Order> orders = new ArrayList<>();

    //    private String
}
