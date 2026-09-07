package com.hrms.lifecycle.repository;

import com.hrms.lifecycle.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetInventoryRepository extends JpaRepository<AssetInventory, UUID> {
}
