package com.sim.cloud.zebra.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sim.cloud.zebra.common.util.BeanHump;
import com.sim.cloud.zebra.common.util.DataTableParameter;
import com.sim.cloud.zebra.common.util.DataUtil;
import com.sim.cloud.zebra.common.util.InstanceUtil;
import com.sim.cloud.zebra.common.util.PropertiesUtil;

/**
 * 抽象基础服务类，提供基本CRUD操作
 * 
 * @author TF-2017
 *
 * @param <M>
 * @param <T>
 */
public class AbstractService<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T> {
	protected Logger logger = LogManager.getLogger();

	public M getBaseMapper() {
		return (M) baseMapper;
	}
	
	int maxThread = PropertiesUtil.getInt("db.reader.list.maxThread", 500);
	int threadSleep = PropertiesUtil.getInt("db.reader.list.threadWait", 5);
	ExecutorService executorService = Executors.newFixedThreadPool(maxThread);

	/**
	 * 多表分页查询
	 * 
	 * @param page
	 * @param param
	 */
	public void queryMutiTablePage(Page<T> page, T param) {
		List<T> list = getBaseMapper().selectMutiTablePage(page, InstanceUtil.transBean2Map(param));
		page.setRecords(list);
	}
	
	private int getFromMap(Map<String, Object> params,String key){
		try {
			return Integer.parseInt(params.get(key).toString());
		} catch (Exception e) {
		}
		return 10;
	}

	/** 分页查询 */
	public Page<T> getPage(Map<String, Object> params) {
		    String orderBy = null;
		    boolean asc=false;
		    int pageNum = 1;
		    int pageSize = getFromMap(params,"iDisplayLength");
		    if (params.get("iDisplayStart") != null) {
		      int start = Integer.parseInt(params.get("iDisplayStart").toString());
		      pageNum = start / pageSize + 1;
		    }
		    if (getFromMap(params,"iSortingCols")==1  && params.get("iSortCol_0")!=null) {
		          String sortNum =  params.get("iSortCol_0").toString();
		          String sortField = "mDataProp_" + sortNum;
		          orderBy=params.get(sortField).toString();
		          asc=  params.get("sSortDir_0").toString().equals("asc");
		        }
		    
		if (DataUtil.isNotEmpty(params.get("orderBy"))) {
			orderBy = (String) params.get("orderBy");
			params.remove("orderBy");
		}
		Page<T> page = new Page<T>(pageNum, pageSize, BeanHump.camelToUnderline(orderBy));
		page.setAsc(asc);
		return page;
	}

	/** 根据Id查询(默认类型T) */
	public Page<T> getPage(final Page<Long> ids) {
		if (ids != null) {
			Page<T> page = new Page<T>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<T> records = InstanceUtil.newArrayList();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				records.add(null);
			}
			final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							records.set(index, queryById(ids.getRecords().get(index)));
						} catch (Exception e) {
							logger.error("", e);
						} finally {
							thread.put(index, 0);
						}
					}
				});
			}
			while (thread.size() < records.size()) {
				try {
					Thread.sleep(threadSleep);
				} catch (InterruptedException e) {
					logger.error("", e);
				}
			}
			page.setRecords(records);
			return page;
		}
		return new Page<T>();
	}

	/** 根据Id查询(默认类型T) */
	public Page<Map<String, Object>> getPageMap(final Page<Long> ids) {
		if (ids != null) {
			Page<Map<String, Object>> page = new Page<Map<String, Object>>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<Map<String, Object>> records = InstanceUtil.newArrayList();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				records.add(null);
			}
			final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							records.set(index, InstanceUtil.transBean2Map(queryById(ids.getRecords().get(index))));
						} catch (Exception e) {
							logger.error("", e);
						} finally {
							thread.put(index, 0);
						}
					}
				});
			}
			while (thread.size() < records.size()) {
				try {
					Thread.sleep(threadSleep);
				} catch (InterruptedException e) {
					logger.error("", e);
				}
			}
			page.setRecords(records);
			return page;
		}
		return new Page<Map<String, Object>>();
	}

	/** 根据Id查询(cls返回类型Class) */
	public <K> Page<K> getPage(final Page<Long> ids, final Class<K> cls) {
		if (ids != null) {
			Page<K> page = new Page<K>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<K> records = InstanceUtil.newArrayList();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				records.add(null);
			}
			final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							T t = queryById(ids.getRecords().get(index));
							K k = InstanceUtil.to(t, cls);
							records.set(index, k);
						} catch (Exception e) {
							logger.error("", e);
						} finally {
							thread.put(index, 0);
						}
					}
				});
			}
			while (thread.size() < records.size()) {
				try {
					Thread.sleep(threadSleep);
				} catch (InterruptedException e) {
					logger.error("", e);
				}
			}
			page.setRecords(records);
			return page;
		}
		return new Page<K>();
	}

	/** 根据Id查询(默认类型T) */
	public List<T> getList(final List<Long> ids) {
		final List<T> list = InstanceUtil.newArrayList();
		if (ids != null) {
			for (int i = 0; i < ids.size(); i++) {
				list.add(null);
			}
			final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
			for (int i = 0; i < ids.size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							list.set(index, queryById(ids.get(index)));
						} catch (Exception e) {
							logger.error("", e);
						} finally {
							thread.put(index, 0);
						}
					}
				});
			}
			while (thread.size() < list.size()) {
				try {
					Thread.sleep(threadSleep);
				} catch (InterruptedException e) {
					logger.error("", e);
				}
			}
		}
		return list;
	}

	/** 根据Id查询(cls返回类型Class) */
	public <K> List<K> getList(final List<Long> ids, final Class<K> cls) {
		final List<K> list = InstanceUtil.newArrayList();
		if (ids != null) {
			for (int i = 0; i < ids.size(); i++) {
				list.add(null);
			}
			final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
			for (int i = 0; i < ids.size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							T t = queryById(ids.get(index));
							K k = InstanceUtil.to(t, cls);
							list.set(index, k);
						} catch (Exception e) {
							logger.error("", e);
						} finally {
							thread.put(index, 0);
						}
					}
				});
			}
			while (thread.size() < list.size()) {
				try {
					Thread.sleep(threadSleep);
				} catch (InterruptedException e) {
					logger.error("", e);
				}
			}
		}
		return list;
	}

	@Transactional
	public void delete(Long id) {
		try {
			baseMapper.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Transactional
	public Integer deleteByEntity(T t) {
		Wrapper<T> wrapper = new EntityWrapper<T>(t);
		return baseMapper.delete(wrapper);
	}

	protected void sleep(int millis) {
		try {
			Thread.sleep(RandomUtils.nextLong(10, millis));
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	public T queryById(Long id) {
		return queryById(id, 1);
	}

	private T queryById(Long id, int times) {
		T record = baseMapper.selectById(id);
		return record;
	}

	public Page<T> query(Map<String, Object> params) {
		Page<Long> page = (Page<Long>) getPage(params);
		page.setRecords(baseMapper.selectIdPage(page, params));
		return getPage(page);
	}
	
	
	
	public Page<T> selectPage(Map<String, Object> params, Class<T> cls){
		Page<T> page =  getPage(params);
		T t=null;
		try {
			t = cls.newInstance();
		} catch (Exception e) {
		} 
		Map<String, Object> pm=InstanceUtil.transMap2Bean(params, t);
		Wrapper<T> wrapper=new EntityWrapper<>();
		List<String> likeStr=new ArrayList<>();
		if(params.get("zebraLike")!=null){
			likeStr.addAll(Arrays.asList(params.get("zebraLike").toString().split(",")));
		}
		pm.entrySet().stream().forEach(e->{
			if(likeStr.contains(e.getKey())){
				wrapper.like(BeanHump.camelToUnderline(e.getKey()), e.getValue().toString());
			}else{
				wrapper.eq(BeanHump.camelToUnderline(e.getKey()), e.getValue());
			}
		});
		return super.selectPage(page,wrapper);
	}

	public List<T> queryList(Map<String, Object> params) {
		List<Long> ids = baseMapper.selectIdPage(params);
		List<T> list = getList(ids);
		return list;
	}

	protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
		Page<Long> page = (Page<Long>) getPage(params);
		page.setRecords(baseMapper.selectIdPage(page, params));
		return getPage(page, cls);
	}

	public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
		Page<Long> page = (Page<Long>) getPage(params);
		page.setRecords(baseMapper.selectIdPage(page, params));
		return getPageMap(page);
	}

	public T selectOne(T entity) {
		return baseMapper.selectOne(entity);
	}

	/**
	 * 返回Jquery Datatable 格式分页数据
	 * 
	 * @param page
	 * @param sEcho
	 * @return
	 */
	public DataTableParameter<T> selectDataTablePage(Page<T> page, String sEcho) {
		Page<T> resultPage = selectPage(page);
		return new DataTableParameter<T>(resultPage.getTotal(), sEcho, resultPage.getRecords());
	}
}
