package com.zis.storage.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zis.storage.dto.CreateOrderDTO;
import com.zis.storage.dto.StockDTO;
import com.zis.storage.dto.StorageLacknessOpDTO;
import com.zis.storage.entity.StorageIoBatch;
import com.zis.storage.entity.StorageIoDetail;
import com.zis.storage.entity.StorageOrder;
import com.zis.storage.entity.StoragePosStock;
import com.zis.storage.entity.StoragePosition;
import com.zis.storage.entity.StorageProduct;
import com.zis.storage.entity.StorageRepoInfo;

/**
 * 仓储模块对外统一接口
 * 
 * @author yz
 * 
 */
public interface StorageService {

	// ----- 出库相关方法 ------
	/**
	 * 直接出库
	 * <p/>
	 * <ol>
	 * <li>无需新建批次，该方法会自动关联记录到当日批次(type=daily)</li>
	 * <li>更新StorageProduct，减少总量，减少可用量</li>
	 * <li>更新StoragePosStock，减少总量</li>
	 * <li>新增StorageIoDetail，记录posId, skuId, amount, operator, status=sent</li>
	 * </ol>
	 * 
	 * @param repoId
	 *            仓库Id
	 * @param skuId
	 *            商品SkuId
	 * @param amount
	 *            出库数量
	 * @param posLabel
	 *            商品库位标签
	 * @param operator
	 *            操作员Id
	 */
	void directSend(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator);

	/**
	 * 创建订单
	 * <p/>
	 * <ol>
	 * <li>创建StorageOrder, status=created</li>
	 * <li>修改StorageProduct，增加占用库存，减少可用库存（防止超卖）</li>
	 * <li>新增库存占用明细StorageProductOccupy，关联StorageOrder, StorageProduct</li>
	 * </ol>
	 */
	StorageOrder createOrder(CreateOrderDTO request);

	/**
	 * 分配订单到库位
	 * <p/>
	 * <ol>
	 * <li>创建配货批次StorageIoBatch</li>
	 * <li>更新订单状态StorageOrder.tradeStatus=PROCESSING</li>
	 * <li>根据（下单阶段）库存占用明细StorageProductOccupy，查找可用的库位库存StoragePosStock</li>
	 * <li>创建出库明细StorageIoDetail，status=waiting</li>
	 * <li>更新库位库存StoragePosStock，增加占用量（防止同一库位的商品被分配给多个订单）</li>
	 * </ol>
	 */
	int arrangeOrder(Integer repoId, List<Integer> outOrderIds, Integer operator);

	/**
	 * 取件-完成操作
	 * <p/>
	 * 对ioDetailId执行取件操作，完成后返回下一条等待取件的记录；<br/>
	 * 
	 * @param ioDetailId
	 *            出库明细
	 * @param batchId
	 *            批次号
	 * @param operator
	 *            操作员
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail pickupDoneAndLockNext(Integer ioDetailId, Integer operator);

	/**
	 * 取件-锁定记录
	 * <p/>
	 * 返回第一条等待取件的记录并锁定，防止其他操作员查看和操作<br/>
	 * 如果意外退出、关机（锁定但未取货），也可以通过调用此方法获取最近一次未完成操作的记录
	 * 
	 * @param batchId
	 * @param operator
	 * @return 如果完成所有取件，返回null
	 */
	StorageIoDetail pickupLock(Integer batchId, Integer operator);

	/**
	 * (全部)缺货
	 * <p/>
	 * <ol>
	 * <li>对ioDetailId执行缺货处理；</li>
	 * <li>重新生成等待取件的记录，并关联到当前批次中；</li>
	 * <li>返回下一条等待取件的记录；</li>
	 * </ol>
	 * 
	 * @param ioDetailId
	 *            出库明细
	 * @param operator
	 *            操作员
	 * @return 如果完成所有取件，返回null
	 */
	StorageLacknessOpDTO lackAll(Integer ioDetailId, Integer operator);

	/**
	 * 部分缺货
	 * <p/>
	 * <ol>
	 * <li>ioDetail拆分为两条记录：有货和缺货；</li>
	 * <li>有货的执行取件操作（
	 * {@link StorageService#pickupDoneAndLockNext(Integer, Integer)}）；</li>
	 * <li>缺货的执行缺货操作（{@link StorageService#lackAll(Integer, Integer)}）</li>
	 * <li>返回下一条等待取件的记录；</li>
	 * </ol>
	 * 
	 * @param ioDetailId
	 *            出库明细
	 * @param operator
	 *            操作员
	 * @param actualAmt
	 *            实际取件数量
	 * @return 如果完成所有取件，返回null
	 */
	StorageLacknessOpDTO lackPart(Integer ioDetailId, Integer operator, Integer actualAmt);

	/**
	 * 完成配货
	 * <p/>
	 * 检查是否还存在未完成的配货明细，如果存在，抛出异常<br/>
	 * 更新批次为已完成；<br/>
	 * 更新订单为已出库
	 * 
	 * @param batchId
	 * @return 已出库的外部订单Id
	 */
	List<Integer> finishBatchSend(Integer batchId);

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 *            注意是仓储订单Id，而不是订单Id
	 */
	void cancelOrder(Integer orderId);

	/**
	 * 取消订单
	 * 
	 * @param repoId
	 * @param outOrderId
	 */
	void cancelOrder(Integer repoId, Integer outOrderId);

	// ----- 入库相关方法 ------

	/**
	 * 创建入库单
	 * <p/>
	 * 生成入库批次 {@link StorageIoBatch}
	 * 
	 * @param repoId
	 *            仓库Id
	 * @return
	 */
	StorageIoBatch createInStorage(Integer repoId, String memo, Integer operator);

	/**
	 * 入库
	 * <p/>
	 * 生成入库明细 {@link StorageIoDetail},st=待确认
	 * 
	 * @param batchId
	 *            批次号
	 * @param skuId
	 * @param amount
	 *            数量
	 * @param posLabel
	 *            库位标签
	 * @param operator
	 *            操作员
	 */
	StorageIoDetail addInStorageDetail(Integer batchId, Integer skuId, Integer amount, String posLabel, Integer operator);

	/**
	 * 确认入库
	 * <p/>
	 * <ol>
	 * <li>更新入库明细，st=已完成</li>
	 * <li>更新入库批次，st=已完成</li>
	 * <li>增加库位库存</li>
	 * <li>增加总库存</li>
	 * </ol>
	 * 
	 * @param batchId
	 * @param operator
	 */
	void confirmInStorage(Integer batchId, Integer operator);

	/**
	 * 取消入库
	 * <p/>
	 * <ol>
	 * <li>更新入库明细，st=已取消</li>
	 * <li>更新入库批次，st=已取消</li>
	 * </ol>
	 * 
	 * @param batchId
	 * @param operator
	 */
	void cancelInStorage(Integer batchId, Integer operator);

	/**
	 * 直接入库
	 * <p/>
	 * <ol>
	 * <li>增加入库明细，st=已完成</li>
	 * <li>自动关联当日批次</li>
	 * <li>增加库位库存</li>
	 * <li>增加总库存</li>
	 * </ol>
	 * 
	 * @param repoId
	 *            仓库Id
	 * @param skuId
	 * @param amount
	 *            数量
	 * @param posLabel
	 *            库位标签
	 * @param operator
	 *            操作员
	 */
	void directInStorage(Integer repoId, Integer skuId, Integer amount, String posLabel, Integer operator);

	// ----- 库位相关方法 -----
	StoragePosition createStoragePosition(Integer repoId, String posLabel);

	// ----- 查询相关方法 -----
	/**
	 * 按照仓库Id和skuIds查询库存商品
	 * 
	 * @param skuIds
	 * @param repoId
	 * @return
	 */
	List<StorageProduct> findStorageProductBySkuIdsAndRepoId(List<Integer> skuIds, Integer repoId);

	/**
	 * 按照仓库Id和skuId查询库存商品
	 * 
	 * @param skuId
	 * @param repoId
	 * @return
	 */
	StorageProduct findStorageProductBySkuIdAndRepoId(Integer skuId, Integer repoId);

	/**
	 * 按照productId查询商品的库存分布明细
	 * 
	 * @param productId
	 *            库存商品表Id，注意不是skuId
	 * @return
	 */
	List<StockDTO> findAllStockByProductId(Integer productId);

	/**
	 * 查询变动明细-按照库存商品Id
	 * 
	 * @return
	 */
	Page<StorageIoDetail> findStorageIoDetailByProductId(Integer productId, Pageable page);

	/**
	 * 查询变动明细-按照库存商品Id、库位
	 * 
	 * @return
	 */
	Page<StorageIoDetail> findStorageIoDetailByProductIdAndPosId(Integer productId, Integer posId, Pageable page);

	// ----- 库位相关方法 -----
	/**
	 * 新建库位
	 * 
	 * @param label
	 * @param repoId
	 */
	void savePosition(String label, Integer repoId);

	/**
	 * 更新库位
	 * 
	 * @param label
	 * @param repoId
	 */
	void updatePosition(Integer posId, String label, Integer repoId);

	/**
	 * 库位检查
	 * 
	 * @param posId
	 * @param repoId
	 * @return
	 */
	StoragePosition findByPosIdAndRepoId(Integer posId, Integer repoId);

	/**
	 * 库位查询
	 * 
	 * @param label
	 * @param repoId
	 * @return
	 */
	StoragePosition findByLabelAndRepoId(String label, Integer repoId);

	/**
	 * 根据StoragePosStockList查询商品
	 * 
	 * @param posId
	 * @return
	 */
	List<StorageProduct> findByPosStockList(List<StoragePosStock> posStockList);

	/**
	 * 根据库位Id 查询StoragePosStock表
	 * 
	 * @param posId
	 * @param page
	 * @return
	 */
	Page<StoragePosStock> findByPosId(Integer posId, Pageable page);

	/**
	 * 根据公司Id查找仓库
	 * 
	 * @param companyId
	 * @return
	 */
	List<StorageRepoInfo> findStorageRepoInfoByCompanyId(Integer companyId);

	/**
	 * 根据skuId 和仓库Id 查找商品
	 * 
	 * @param skuId
	 * @param repoId
	 * @return
	 */
	StorageProduct findBySkuIdAndRepoId(Integer skuId, Integer repoId);

	StorageIoDetail findByIoDetailId(Integer ioDetailId);

	/**
	 * 根据批次Id 状态 类型查找 StorageIoDetail
	 * 
	 * @param batchId
	 * @param ioDetailType
	 * @param DetailStatusList
	 * @return
	 */
	List<StorageIoDetail> findStorageIoDetailByRepoIdAndBatchIdInAndIoDetailTypeAndDetailStatusIn(Integer repoId,
			List<Integer> batchIds, String ioDetailType, List<String> DetailStatusList);

	/**
	 * 查询某一时间段的库存变动量
	 * 
	 * @param startTime
	 * @param endTime
	 * @param repoId
	 * @return
	 */
	List<StorageProduct> findByUpdateTimeBetweenStartTimeAndEndTimeAndRepoId(Date startTime, Date endTime,
			Integer repoId);
}
