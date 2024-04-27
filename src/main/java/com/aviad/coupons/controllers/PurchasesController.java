package com.aviad.coupons.controllers;

import com.aviad.coupons.dto.Purchase;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.PurchaseLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {
    private PurchaseLogic purchaseLogic;

    @Autowired
    public PurchasesController(PurchaseLogic purchaseLogic) {
        this.purchaseLogic = purchaseLogic;
    }

    @PostMapping
    public long createPurchase(@RequestBody Purchase purchase, @RequestHeader("Authorization") String token) throws ApplicationException {
        return this.purchaseLogic.addPurchase(purchase, token);
    }

    @PutMapping
    public void updatePurchase(@RequestBody Purchase purchase, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.purchaseLogic.updatePurchase(purchase, token);
    }

    @GetMapping
    public List<Purchase> getAllPurchases() throws ApplicationException {
        return this.purchaseLogic.getAllPurchases();
    }

    @GetMapping("/{id}")
    public Purchase getPurchase(@PathVariable("id") Long id) throws ApplicationException {
        return this.purchaseLogic.getPurchase(id);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable("id") Long id) throws ApplicationException {
        this.purchaseLogic.deletePurchase(id);
    }

    @GetMapping("/byCompanyId")
    public List<Purchase> getPurchasesByCompanyId(@RequestParam("id") int id) throws ApplicationException {
        return this.purchaseLogic.getPurchasesByCompanyId(id);
    }

    @GetMapping("/byUserId")
    public List<Purchase> getPurchasesByUserId(@RequestParam("id") int id) throws ApplicationException {
        return this.purchaseLogic.getPurchasesByUserId(id);
    }

    @GetMapping("/byCategoryId")
    public List<Purchase> getPurchasesByCategoryId(@RequestParam("id") int id) throws ApplicationException {
        return this.purchaseLogic.getPurchasesByCategoryId(id);
    }
}


