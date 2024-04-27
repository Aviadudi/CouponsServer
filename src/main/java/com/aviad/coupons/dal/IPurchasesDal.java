package com.aviad.coupons.dal;


import com.aviad.coupons.dto.Purchase;
import com.aviad.coupons.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPurchasesDal extends CrudRepository<PurchaseEntity, Long> {
    @Query("SELECT new com.aviad.coupons.dto.Purchase (p.id, p.amount, p.purchaseDate,p.user.id, p.coupon.name, p.company.name, p.price)" +
            "FROM PurchaseEntity p")
    List<Purchase> getPurchases();

    @Query("SELECT new com.aviad.coupons.dto.Purchase (p.id, p.amount, p.purchaseDate, p.user.id, p.coupon.name, p.company.name, p.price)" +
            "FROM PurchaseEntity p WHERE p.id = :id")
    Purchase getPurchase(@Param("id") long id);

    @Query("SELECT new com.aviad.coupons.dto.Purchase (p.id, p.amount, p.purchaseDate, p.user.id, p.coupon.name, p.company.name, p.price)" +
            "FROM PurchaseEntity p WHERE p.company.id = :id")
    List<Purchase> getPurchasesByCompanyId(@Param("id") int id);

@Query("SELECT new com.aviad.coupons.dto.Purchase (p.id, p.amount, p.purchaseDate,p.user.id, p.coupon.id, p.company.id, p.price, p.coupon.name, p.coupon.description, p.company.name)" +
        "FROM PurchaseEntity p WHERE p.user.id = :id")
    List<Purchase> getPurchasesByUserId(@Param("id") int id);

    @Query("SELECT new com.aviad.coupons.dto.Purchase (p.id, p.amount, p.purchaseDate, p.user.id, p.coupon.name, p.company.name, p.price)" +
            "FROM PurchaseEntity p INNER JOIN p.coupon c WHERE c.category.id = :id")
    List<Purchase> getPurchasesByCategoryId(@Param("id") int id);
}
