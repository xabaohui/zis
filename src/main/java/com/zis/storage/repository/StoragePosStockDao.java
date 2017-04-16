package com.zis.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zis.storage.entity.StoragePosStock;

public interface StoragePosStockDao extends JpaRepository<StoragePosStock, Integer> {

	/**
	 * 查询指定库位的商品
	 * 
	 * @param posId
	 * @return
	 */
	@Query(value = "from StoragePosStock where posId = :posId and totalAmt > occupyAmt")
	List<StoragePosStock> findByPosId(@Param(value = "posId") Integer posId);
	
	/**
	 * 查询指定库位指定商品
	 * 
	 * @param posId
	 * @return
	 */
	StoragePosStock findByPosIdAndProductId(Integer posId, Integer productId);
	
	/**
	 * 查询指定库位指定商品
	 * 
	 * @param posId
	 * @return
	 */
	@Query(value = "select stock.* from storage_pos_stock stock, storage_position pos " +
			"where stock.pos_id=pos.pos_id and pos.repo_id=:repoId and pos.label=:posLabel and stock.product_id=:productId",
			nativeQuery=true)
	StoragePosStock findByLabelAndProductId(@Param("repoId")Integer repoId, @Param("posLabel")String posLabel, @Param("productId")Integer productId);
	
	/**
	 * 查询库存分布（可用）
	 * @param productId
	 * @return
	 */
	@Query("select new com.zis.storage.dto.StockDTO(s.stockId, pos.posId, pos.label, prod.repoId, prod.productId, prod.skuId, s.totalAmt, s.occupyAmt) " +
			"from StoragePosStock s, StoragePosition pos, StorageProduct prod " +
			"where s.posId=pos.posId and s.productId=prod.productId " +
			"and s.totalAmt > s.occupyAmt and s.productId = :productId " +
			"order by (s.totalAmt - s.occupyAmt) desc")
	List<com.zis.storage.dto.StockDTO> findAvailableStock(@Param("productId")Integer productId);
	
	/**
	 * 查询库存分布（所有）
	 * @param productId
	 * @return
	 */
	@Query("select new com.zis.storage.dto.StockDTO(s.stockId, pos.posId, pos.label, prod.repoId, prod.productId, prod.skuId, s.totalAmt, s.occupyAmt) " +
			"from StoragePosStock s, StoragePosition pos, StorageProduct prod " +
			"where s.posId=pos.posId and s.productId=prod.productId " +
			"and s.productId = :productId " +
			"order by (s.totalAmt - s.occupyAmt) desc")
	List<com.zis.storage.dto.StockDTO> findAllStock(@Param("productId")Integer productId);
}