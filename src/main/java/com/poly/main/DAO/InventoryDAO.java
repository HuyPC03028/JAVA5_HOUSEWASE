package com.poly.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.main.model.Inventory;

public interface InventoryDAO extends JpaRepository<Inventory, Integer> {

}
