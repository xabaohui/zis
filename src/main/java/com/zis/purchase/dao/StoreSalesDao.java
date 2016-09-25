package com.zis.purchase.dao;

import java.util.List;

import com.zis.purchase.bean.Storesales;

public interface StoreSalesDao {

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#save(com.zis.purchase.bean.Storesales)
	 */
	public void save(Storesales transientInstance);

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findById(java.lang.Integer)
	 */
	public Storesales findById(java.lang.Integer id);

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findByExample(com.zis.purchase.bean.Storesales)
	 */
	public List<Storesales> findByExample(Storesales instance);

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findByBookId(java.lang.Object)
	 */
	public List<Storesales> findByBookId(Object bookId);

}