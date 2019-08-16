package org.nutz.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.demo.bean.R;
import org.nutz.demo.bean.T;
import org.nutz.demo.service.TService;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import com.chinare.axe.Result;
import com.chinare.axe.captcha.CaptchaView;
import com.google.common.collect.Lists;

@RestController
@SpringBootApplication
@EnableTransactionManagement
public class NutzDemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NutzDemoApplication.class, args);
	}

	static final Log logger = Logs.get();
	@Autowired
	TService tService;

	@GetMapping("captcha/{length}")
	public View captcha(@PathVariable("length") int length) {
		return new CaptchaView(length);
	}

	@GetMapping("enum")
	public R enumtest() {
		return new R();
	}

	@GetMapping("test")
	public NutMap test() {
		return tService.test();
	}

	@GetMapping("/dao")
	public Object dao() {
		return dao.meta();
	}

	public static class DTO {
		private boolean fb;
		private Boolean fb1;

		private short s;
		private Short s1;

		private byte b;
		private Byte b1;

		private int i;
		private Integer i1;

		private long l;
		private Long l1;

		private float f;
		private Float f1;

		private double d;
		private Double d1;

		private char c;
		private Character c1;

		public boolean isFb() {
			return fb;
		}

		public void setFb(boolean fb) {
			this.fb = fb;
		}

		public Boolean getFb1() {
			return fb1;
		}

		public void setFb1(Boolean fb1) {
			this.fb1 = fb1;
		}

		public short getS() {
			return s;
		}

		public void setS(short s) {
			this.s = s;
		}

		public Short getS1() {
			return s1;
		}

		public void setS1(Short s1) {
			this.s1 = s1;
		}

		public byte getB() {
			return b;
		}

		public void setB(byte b) {
			this.b = b;
		}

		public Byte getB1() {
			return b1;
		}

		public void setB1(Byte b1) {
			this.b1 = b1;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public Integer getI1() {
			return i1;
		}

		public void setI1(Integer i1) {
			this.i1 = i1;
		}

		public long getL() {
			return l;
		}

		public void setL(long l) {
			this.l = l;
		}

		public Long getL1() {
			return l1;
		}

		public void setL1(Long l1) {
			this.l1 = l1;
		}

		public float getF() {
			return f;
		}

		public void setF(float f) {
			this.f = f;
		}

		public Float getF1() {
			return f1;
		}

		public void setF1(Float f1) {
			this.f1 = f1;
		}

		public double getD() {
			return d;
		}

		public void setD(double d) {
			this.d = d;
		}

		public Double getD1() {
			return d1;
		}

		public void setD1(Double d1) {
			this.d1 = d1;
		}

		public char getC() {
			return c;
		}

		public void setC(char c) {
			this.c = c;
		}

		public Character getC1() {
			return c1;
		}

		public void setC1(Character c1) {
			this.c1 = c1;
		}

	}

	public static class K<F> {
		F data;

		/**
		 * @return the data
		 */
		public F getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(F data) {
			this.data = data;
		}

	}

	@GetMapping("json")
	public NutMap json() {
		return NutMap.NEW().addv("status", 0).addv("d", new DTO());
	}

	@PostMapping("dto")
	public Result dto(@RequestBody K<DTO> k) {
		return Result.success().addData("dto", k.getData());
	}

	@PostMapping("echo")
	public NutMap echo(@RequestBody Map<String, Object> data) {
		return NutMap.WRAP(data);
	}

	@GetMapping("run")
	public Object run() {

		List<NutMap> target = Lists.newArrayList();
		dao.run(new ConnCallback() {
			@Override
			public void invoke(Connection conn) throws Exception {
				try (PreparedStatement p = conn.prepareStatement("SELECT * FROM t_tt_ttt_tttt")) {
					p.executeQuery();
					ResultSet rs = p.getResultSet();
					while (rs.next()) {
						target.add(NutMap.WRAP(Record.create(rs)));
					}
				}
			}
		});
		return target;
	}

	@GetMapping("/sqls")
	public Object sqls() {
		return dao.sqls();
	}

	@GetMapping("dd")
	public NutMap dd() {
		return NutMap.NEW().addv("t", new T());
	}

	@Autowired
	Dao dao;
}
