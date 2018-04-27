package com.gl365.member.service;

import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public interface MemberTest {

	final Scanner scanner = new Scanner(System.in);;

	public abstract void test();

	@Test
	public default void run() {
		while (true) {
			System.out.println("========================= continue ?  true or false  =====================");
			boolean sc = scanner.nextBoolean();
			if (!sc)
				break;
			test();
		}
	}

}
