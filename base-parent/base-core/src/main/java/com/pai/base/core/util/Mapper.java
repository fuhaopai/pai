package com.pai.base.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;

public class Mapper {

	private org.dozer.Mapper mapper;

	private Mapper() {
		List<String> files = Arrays.asList("conf/dozer-converter.xml");
		mapper = new DozerBeanMapper(files);
	}

	private static class SingletonHolder {
		public final static Mapper instance = new Mapper();
	}

	public static Mapper getInstance() {
		return SingletonHolder.instance;
	}

    /**
     * 复制Bean属性值
     *
     * @param source      源对象
     * @param targetClass 目标对象类
     */
    public <S, T> T copyProperties(S source, Class<T> targetClass) {

        return mapper.map(source, targetClass);
    }

    /**
     * 复制Bean集合到目标Bean集合当中
     *
     * @param sourceList  源集合
     * @param targetList  目标集合
     * @param targetClass 指定Bean class
     */
    public <S, T> void copyList(List<S> sourceList, List<T> targetList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList))
            return;
        for (S s : sourceList) {
            targetList.add(copyProperties(s, targetClass));
        }
    }

    /**
     * 复制Bean集合到目标Bean集合当中
     *
     * @param sourceList  源集合
     * @param targetClass 指定Bean class
     */
    public <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList))
            return new ArrayList<>();
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S s : sourceList) {
            targetList.add(mapper.map(s, targetClass));
        }
        return targetList;
    }

    public <T, V> Map<T, V> map(Map<?, ?> source, Class<T> keyClz, Class<V> valueClz) {
		Map<T, V> target = new HashMap<T, V>();
		Set<?> entrySet = source.keySet();
		for (Object sourcekey : entrySet) {
			T key = copyProperties(sourcekey, keyClz);
			V value = copyProperties(source.get(sourcekey), valueClz);
			target.put(key, value);
		}
		return target;
	}

	public <T, V> Map<T, V> map(Map<T, ?> source, Class<V> valueClz) {
		Map<T, V> target = new HashMap<T, V>();
		Set<T> entrySet = source.keySet();
		for (T sourcekey : entrySet) {
			// T key = copyProperties(sourcekey, keyClz);
			V value = copyProperties(source.get(sourcekey), valueClz);
			target.put(sourcekey, value);
		}
		return target;
	}
}