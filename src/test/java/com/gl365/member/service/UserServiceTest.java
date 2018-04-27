package com.gl365.member.service;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.dto.PageDto;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.UserForSDto;
import com.gl365.member.dto.users.UserInfotForSDto;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService;

	@Test
	public void test() throws Exception {
		// testGetMemberInfo();
		testGetMemberInfoToS();
		// updateUserTransFlag();
	}

	public void updateUserTransFlag() {
		userService.updateUserTransFlag("511");
	}

	@SuppressWarnings("unchecked")
	public void testGetMemberInfo() throws Exception {
		LOG.info("获取会员信息 提供给支付系统调用>>>>>>>>开始");
		try {
			// userid为空，或者不正确时会报错
			HashMap<String, Object> map = (HashMap<String, Object>) userService.getMemberInfo("52ecaa6c3bb545aea27429a6de3c6d46");
			if (null != map) {
				LOG.info("获取会员信息 提供给支付系统调用>>>>>>>>成功" + map.toString());
			} else {
				LOG.info("获取会员信息 提供给支付系统调用>>>>>>>>成功>>>>没有数据");
			}
		} catch (Exception e) {
			LOG.error("获取会员信息 提供给支付系统调用>>>>>>>>失败");
		}
	}

	public void testGetMemberInfoToS() throws Exception {
		LOG.info("获取会员ID 提供给S端系统调用>>>>>>>>开始");
		try {
			UserForSDto userForSDto = new UserForSDto();
			userForSDto.setRecommendAgentId("6");
			userForSDto.setRecommendAgentT("6");
			userForSDto.setCurPage(1);
			userForSDto.setBeginTime(LocalDate.now().minusMonths(2L));
			userForSDto.setEndTime(LocalDate.now());
			ResultDto<PageDto<UserInfotForSDto>> result = userService.getMemberInfoToS(userForSDto);
			if (null != result.getData()) {
				LOG.info("获取会员ID 提供给S端系统调用>>>>>>>>成功\n" + JsonUtils.toJsonString(result.getData()));
			} else {
				LOG.info("获取会员ID 提供给S端系统调用>>>>>>>>成功>>>>没有数据");
			}
		} catch (Exception e) {
			LOG.error("获取会员ID 提供给S端系统调用>>>>>>>>失败");
		}
	}
}
