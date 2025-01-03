package com.bkmarriott.coupon.infrastructure.persistence.entity;

import com.bkmarriott.coupon.domain.Coupon;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "m_coupon")
@Entity(name = "Coupon")
public class CouponEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicyEntity couponPolicy;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float discountRate;

    public Coupon toDomain() {
        return new Coupon(id, couponPolicy.toDomain(), name, discountRate);
    }
}
