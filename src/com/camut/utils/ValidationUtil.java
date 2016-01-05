package com.camut.utils;

import com.camut.framework.constant.MessageConstant.PASSWORD_VALIDATION;;

public class ValidationUtil {

	/**
	 * @Title: validatePassword
	 * @Description: validation rules: password's length is at least 6 and
	 *               cannot contain special characters
	 * @param: String
	 * @return: PASSWORD_VALIDATION
	 */
	public static PASSWORD_VALIDATION validatePassword(String password) {
		if (password == "" || password.length() < 6) {
			return PASSWORD_VALIDATION.PASSWORD_TOO_SHORT;
		}

		// String passwordRegExp = "^[a-zA-Z0-9]*$";
		// if (!Pattern.matches(passwordRegExp, password)) {
		// return PASSWORD_VALIDATION.PASSWORD_HAS_SPECIAL_CHARACTERS;
		// }

		return PASSWORD_VALIDATION.VALID;
	}

}
