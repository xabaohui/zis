package com.zis.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zis.storage.entity.StorageCheckPlan;

public interface StorageCheckPlanDao extends JpaRepository<StorageCheckPlan, Integer> {
}