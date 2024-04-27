package com.aviad.coupons.dal;

import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.entities.CouponEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {
    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c")
    List<Coupon> getCoupons();

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c WHERE c.id = :id")
    Coupon getCoupon(@Param("id") int id);

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c WHERE c.amount>0 and c.startDate<:currentDate and c.endDate>:currentDate")
    List<Coupon> getAvailableCoupons(@Param("currentDate") Date currentDate);

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c WHERE c.category.id IN :categoryIds AND (LOWER(c.name) LIKE %:searchInput% OR LOWER(c.description) LIKE %:searchInput% OR LOWER(c.company.name) LIKE %:searchInput% OR LOWER(c.category.name) LIKE %:searchInput%)" +
            "AND c.price >= :minPrice AND c.price <= :maxPrice")
    Page<Coupon> getByFiltersForAdmin(@Param("categoryIds") Integer[] categoryIds,
                                      @Param("searchInput") String searchInput,
                                      @Param("minPrice") float minPrice,
                                      @Param("maxPrice") float maxPrice,
                                      Pageable pageable);

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c WHERE c.category.id IN :categoryIds AND c.company.id = :companyId AND (LOWER(c.name) LIKE %:searchInput% OR LOWER(c.description) LIKE %:searchInput% OR LOWER(c.company.name) LIKE %:searchInput% OR LOWER(c.category.name) LIKE %:searchInput%)" +
            "AND c.price >= :minPrice AND c.price <= :maxPrice")
    Page<Coupon> getByFiltersForCompany(@Param("companyId") int companyId,
                                        @Param("categoryIds") Integer[] categoryIds,
                                        @Param("searchInput") String searchInput,
                                        @Param("minPrice") float minPrice,
                                        @Param("maxPrice") float maxPrice,
                                        Pageable pageable);

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price, c.imageData)" +
            " FROM CouponEntity c WHERE c.amount>0 AND c.startDate<:currentDate AND c.endDate>:currentDate AND c.category.id IN :categoryIds AND (LOWER(c.name) LIKE %:searchInput% OR LOWER(c.description) LIKE %:searchInput% OR LOWER(c.company.name) LIKE %:searchInput% OR LOWER(c.category.name) LIKE %:searchInput%)" +
            "AND c.price >= :minPrice AND c.price <= :maxPrice")
    Page<Coupon> getByFiltersForCustomer(@Param("currentDate") Date currentDate,
                                         @Param("categoryIds") Integer[] categoryIds,
                                         @Param("searchInput") String searchInput,
                                         @Param("minPrice") float minPrice,
                                         @Param("maxPrice") float maxPrice,
                                         Pageable pageable);

    @Query("SELECT MAX(c.price) FROM CouponEntity c")
    Float getMaxPrice();

    @Query("SELECT MIN(c.price) FROM CouponEntity c")
    Float getMinPrice();
}
