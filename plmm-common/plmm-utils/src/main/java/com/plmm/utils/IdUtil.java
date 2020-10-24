package com.plmm.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Strings;

/**
 * 主键生成器
 *
 */
public class IdUtil {
	private static AtomicInteger counter = new AtomicInteger(0);
	
	/**
	 * seqid
	 * @return
	 */
	public static Long getSeqId() {
		if (counter.get() > 90000) {
			counter.set(1);
		}
		StringBuilder sb = new StringBuilder(18);
		sb.append(System.currentTimeMillis());
		sb.append(Strings.padStart(String.valueOf(counter.incrementAndGet()), 5, '0'));
		return Long.valueOf(sb.toString());
	}
	
	/**
	 * UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

}
