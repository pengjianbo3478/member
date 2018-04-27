package com.gl365.member.service;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.member.dto.merchant.command.SaveComment4MemberCommand;

@Ignore
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest {

	@Autowired
	private CommentService	commentService;
	
	@Test
	public void test(){
		insertComment();
	}

	private void insertComment() {
		commentService.saveDefaultComment(new SaveComment4MemberCommand("511", "123213131", "sdfsa9sa8fasfua", new BigDecimal(100)));
	}
}
