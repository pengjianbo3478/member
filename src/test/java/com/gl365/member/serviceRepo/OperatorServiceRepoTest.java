package com.gl365.member.serviceRepo;

import java.util.List;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.member.common.JsonUtils;
import com.gl365.member.common.enums.OperatorRoleTypeEnum;
import com.gl365.member.dto.ResultDto;
import com.gl365.member.dto.users.MerchantOperatorDto;
import com.gl365.member.dto.users.command.GetOperatorListCommand;
import com.gl365.member.service.repo.OperatorInfoServiceRepo;

@Ignore
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OperatorServiceRepoTest {

	Scanner sc = new Scanner(System.in);

	@Autowired
	OperatorInfoServiceRepo operatorInfoServiceRepo;

	@Test
	public void test() {
		while (true) {
			boolean cont = sc.nextBoolean();
			if (!cont)
				break;
			receive();
		}
	}

	private void receive() {
		try {
			String merchantNo = "1706021000059";
			ResultDto<List<MerchantOperatorDto>> result = operatorInfoServiceRepo.getMerchantOpertatorList(new GetOperatorListCommand(merchantNo, OperatorRoleTypeEnum.ADMIN.getValue()));
			System.out.println(JsonUtils.toJsonString(result));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
