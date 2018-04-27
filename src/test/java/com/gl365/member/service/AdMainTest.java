package com.gl365.member.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.ad.AdMainDto;
import com.gl365.member.mapper.AdMainMapper;

@Ignore
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdMainTest {

	@Autowired
	private AdMainMapper adMainMapper;
	
	@Test
	public void addTest(){
		AdMainDto record = new AdMainDto();
		record.setAdContacts("contacts_test");
		record.setAdDetail("detail_test");
		record.setAdImg("img_test");
		record.setAdPhone("phone_test");
		record.setAdUrl("url_test");
		record.setState(0);
		for (int i = 2; i < 12; i++) {
			record.setAdName("name_test"+i);
			try{
				adMainMapper.insert(record);
			}catch(DuplicateKeyException ee){
				ee.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void updateTest(){
		AdMainDto record = new AdMainDto();
		record.setId(7);
		record.setState(2);
		record.setAuditInfor("refuse_test");
		adMainMapper.updateById(record);
	}
	
	@Test
	public void selectTest(){
		PageHelper.startPage(1, 3);
		AdMainDto req = new AdMainDto();
		req.setState(1);
		List<AdMainDto> list = adMainMapper.select(req);
		PageInfo<AdMainDto> page = new PageInfo<>(list, 10);
		System.out.println(JsonUtils.toJsonString(page));
	}
}
