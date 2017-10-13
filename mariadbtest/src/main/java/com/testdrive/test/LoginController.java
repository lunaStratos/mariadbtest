package com.testdrive.test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.testdrive.test.DAO.accountDAO;
import com.testdrive.test.VO.Account;

@Controller
public class LoginController {

	@Autowired
	accountDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// 로그인 페이지
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String login() {

		return "login/loginPage";
	}

	// 로그인 확인 post
	@RequestMapping(value = "/loginOK", method = RequestMethod.POST)
	public String loginOK(HttpServletResponse response, Model model, HttpSession sesssionm, Account account) {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		logger.debug("login id: {}", account);
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}

		Account result = dao.login(account);

		if (result == null) {
			model.addAttribute("loginResult", "no id ");
			return "login/loginPage";
		}
		if (account.getRememberMe() == null) {

		} else {

			Cookie cookie = new Cookie("ipValue", result.getUid());
			cookie.setMaxAge(600);
			response.addCookie(cookie); // cookie save
		}

		sesssionm.setAttribute("id", result.getUid());

		return "login/loginSuccess";
	}

	// 회원가입 get
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "login/register";
	}

	@ResponseBody // id 중복체크, ajax 부분
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	public String checkId(String id) {
		logger.debug("ajax insert id: {}", id);
		String result = dao.idcheck(id);
		logger.debug("ajax id result: {}", result);
		if (result == null) {
			result = "This id is possible register";
		} else { // 등록이 되어 있다면
			result = "Already, This id was registered ）";
		}
		return result;
	}

	// 회원가입 post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, Account account) {
		logger.debug("register id : {}", account);
		// id check (영어 아니면 숫자만 가능하게)
		boolean idFlag = checkInputOnlyNumberAndAlphabet(account.getUid());
		// name check (전각만 가능)
		boolean nameFlag = nameCheck(account.getName());
		// password check (반각만 가능)
		boolean passwordFlag = passwordCheck(account.getPassword());

		int result = 0; // 결과: 정상적인 경우 1

		logger.debug("idFlag : {}", idFlag);
		logger.debug("nameFlag : {}", nameFlag);
		logger.debug("passwordFlag : {}", passwordFlag);
	
		// 모두 정상인 경우
		if (idFlag == true && nameFlag == true && passwordFlag == true) {
			result = dao.register(account);
			if (result == 0) { // 중복
				model.addAttribute("registerResult", "0");
				return "login/register";
			}

		} else if (idFlag == false) { // 아이디가 영문과 숫자가 아니면
			result = 2;
			model.addAttribute("registerResult", "2");
			return "login/register";
		} else if (nameFlag == false) { // 이름이 전각이 아니면
			result = 3;
			model.addAttribute("registerResult", "3");
			return "login/register";
		} else if (passwordFlag == false) { // 암호가 반각이 아니면
			result = 4;
			model.addAttribute("registerResult", "4");
			return "login/register";
		}

		/*
		 * 0: 중복 1: 정상처리
		 */

		return "login/loginPage";
	}

	// 영어와 숫자를 제외한 언어는 모두 false
	public boolean checkInputOnlyNumberAndAlphabet(String textInput) {

		char chrInput;

		for (int i = 0; i < textInput.length(); i++) {

			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크

			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// 영문(소문자) OK!
			} else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// 영문(대문자) OK!
			} else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// 숫자 OK!
			} else {
				return false; // 영문자도 아니고 숫자도 아님!
			}
		}

		return true;
	}

	// 이름체크 : 전각이면 true 반각이면 false
	public boolean nameCheck(String insertText) {
		boolean value = true;
		for (int i = 0; i < insertText.length(); ++i) {
			// 文字を一つ一つチェックします
			int c = insertText.charAt(i);
			// 全角の条件
			if (c < 256 || (c >= 0xff61 && c <= 0xff9f)) {
				value = false;
			}
		}

		return value;
	}

	// 암호체크 : 전각이면 false 반각이면 true
	public boolean passwordCheck(String insertText) {
		boolean value = false;
		for (int i = 0; i < insertText.length(); ++i) {
			// 文字を一つ一つチェックします
			int c = insertText.charAt(i);
			// 半角の条件
			if (c < 256 || (c >= 0xff61 && c <= 0xff9f)) {
				value = true;
			}
		}

		return value;
	}

}
