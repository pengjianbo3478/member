package com.gl365.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.ad.AdDetailDto;
import com.gl365.member.dto.ad.AdPutDto;
import com.gl365.member.dto.ad.req.AdDetailReq;
import com.gl365.member.dto.ad.req.AdPutMReq;
import com.gl365.member.mapper.AdPutMapper;

@Ignore
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdPutTest {

	@Autowired
	private AdPutMapper adPutMapper;
	
	@Test
	public void insertTest(){
		List<AdPutDto> list = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			AdPutDto a = new AdPutDto();
			a.setAdMainID(i);
			a.setAdName("test_name"+i);
			a.setCity(String.valueOf(i));
			a.setProvince(String.valueOf(i));
			a.setEndTime(LocalDateTime.now().plusDays(i));
			a.setPlace(i);
			a.setPutDetail("test_detail");
			a.setPutState(1);
			a.setStartTime(LocalDateTime.now().minusDays(i));
			list.add(a);
		}
		
//		AdPutDto a = new AdPutDto();
//		a.setAdMainID(1);
//		a.setAdName("test_name"+1);
//		a.setCity("1");
//		a.setEndTime(LocalDateTime.of(2017, Month.JUNE, 28, 0, 0, 0));
//		a.setPlace(1);
//		a.setPutDetail("test_detail");
//		a.setPutState(1);
//		a.setStartTime(LocalDateTime.of(2017, Month.JUNE, 27, 0, 0, 0));
//		list.add(a);
//		
//		AdPutDto b = new AdPutDto();
//		b.setAdMainID(1);
//		b.setAdName("test_name"+1);
//		b.setCity("1");
//		b.setEndTime(LocalDateTime.of(2017, Month.JUNE, 30, 0, 0, 0));
//		b.setPlace(1);
//		b.setPutDetail("test_detail");
//		b.setPutState(1);
//		b.setStartTime(LocalDateTime.of(2017, Month.JUNE, 28, 23, 59, 59));
//		list.add(b);
		adPutMapper.insertBatch(list);
	}
	
	@Test
	public void selectTest(){
		AdPutMReq req = new AdPutMReq();
		req.setQueryBegin(LocalDateTime.of(2017, Month.JULY, 26, 0, 0, 0));
		req.setQueryEnd(LocalDateTime.of(2017, Month.JULY, 27, 0, 0, 0));
//		req.setAdName("test_name27");
//		req.setCity("27");
//		req.setPlace(27);
//		req.setProvince("0");
//		req.setPutState(1);
		PageHelper.startPage(1, 10);
		List<AdPutDto> list = adPutMapper.queryByM(req);
		PageInfo<AdPutDto> page = new PageInfo<>(list, 10);
		System.out.println(JsonUtils.toJsonString(page));
	}
	
	@Test
	public void updateTest(){
		AdPutDto req = new AdPutDto();
		req.setId(28);
		req.setPutState(0);
		req.setPlace(14);
		req.setProvince("2");
		req.setCity("66");
		adPutMapper.updateById(req);
	}
	
	@Test
	public void queryByCTest(){
		LocalDate date = LocalDate.of(2017, 8, 8);
		LocalTime begintime = LocalTime.of(0,0,0);
		LocalTime endtime = LocalTime.of(23,59,59);
		AdDetailReq req = new AdDetailReq();
		req.setCity("10");
		req.setPlace(14);
		req.setQueryBegin(LocalDateTime.of(date, begintime));
		req.setQueryEnd(LocalDateTime.of(date, endtime));
		PageHelper.startPage(1, 4);
		List<AdDetailDto> list = adPutMapper.queryByC(req);
		PageInfo<AdDetailDto> page = new PageInfo<>(list, 4);
		System.out.println(JsonUtils.toJsonString(page));
	}
	
}
