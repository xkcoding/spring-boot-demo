package org.nutz.demo.service;

import javax.annotation.PostConstruct;

import org.nutz.demo.bean.T;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TService extends BaseService<T> {

	@PostConstruct
	public void init() {
		dao().create(getEntityClass(), false);
	}

	@Transactional
	public NutMap test() {
		for (int i = 0; i < 10; i++) {
			save(new T());
		}
		if (R.random(0, 10) <= 8) {
			throw Lang.makeThrow("test throw %d", Thread.currentThread().getId());
		}
		return NutMap.NEW();
	}
}
