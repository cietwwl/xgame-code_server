package com.game.gameServer.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家
 * 
 * @author hjj2019
 *
 */
public final class Player {
	/** Id */
	public long _UId = -1L;
	/** 会话 Id, 主要是用于通信层 */
	public long _sessionUId = -1L;
	/** 登陆 IP 地址 */
	public String _loginIpAddr = null;
	/** 玩家的属性字典 */
	private final Map<Object, Object> _propMap = new ConcurrentHashMap<>();

	/**
	 * 类默认构造器
	 * 
	 */
	public Player() {
	}

	/**
	 * 根据类定义获取属性值, 如果没有就新建
	 *
	 * @param byClazzDef
	 * @param <T>
	 * @return
	 *
	 */
	public<T> T getPropValOrCreate(Class<T> byClazzDef) {
		if (byClazzDef == null) {
			// 如果参数对象为空,
			// 则直接退出!
			return null;
		}

		// 事先获取属性值
		Object objVal = this._propMap.get(byClazzDef);

		if (objVal == null) {
			try {
				// 如果属性值为空,
				// 创建类对象
				objVal = byClazzDef.newInstance();
				// 添加到字典, 并且看看原来有没有值?
				if (this._propMap.putIfAbsent(byClazzDef, objVal) != null) {
					// 如果添加到字典的时候已经有值,
					// 那么再重新获取一次!
					objVal = this._propMap.get(byClazzDef);
				}
			} catch (Exception ex) {
				// 记录错误日志
				FrameworkLog.LOG.error(
					ex.getMessage(),
					ex
				);
			}
		}

		return (T)objVal;
	}
}
