package com.gl365.member.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.member.Application;
import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.users.UserInfoDto;
import com.gl365.member.dto.users.UserRealNameInfo;
import com.gl365.member.mapper.ExternalAccountMapper;
import com.gl365.member.mapper.UserMapper;
import com.gl365.member.model.ExternalAccount;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExternalAccountMapperTest {

	@Autowired
	private ExternalAccountMapper service;
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void insertTest(){
		ExternalAccount account = new ExternalAccount();
		account.setUserId("test123456789");
		account.setExternalChannel("10019999");
		try{
			int i = service.insertSelective(account);
			System.out.println(i);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectTest(){
		try{
			Boolean r = service.queryIsRegisterByUserId("test123456789","10019999");
			System.out.println(r);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryTest(){
		try{
			UserRealNameInfo un = userMapper.getRealNameInfo("508");
			List<String> list = new ArrayList<>();
			list.add("508");
			List<UserInfoDto> rlt = userMapper.queryUserInfoListByUserIds(list);
			System.out.println(un);
			System.out.println(JsonUtils.toJsonString(rlt));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
