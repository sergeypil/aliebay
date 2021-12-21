package com.epam.aliebay.util;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public class ValidationUtils {
    private static final String MAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,45}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_.-]{1,45}$";
    private static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    private static  final String SECURITY_CODE_REGEX = "^\\d{3,4}$";
    private static  final String CARD_HOLDER_NAME_REGEX = "^[A-Za-z-]+\\s+[A-Za-z-]+$";
    private static final String PHONE_NUMBER_REGEX = "^[0-9\\+]{1,}[0-9\\-]{3,15}$";
    private static final String INTEGER_REGEX = "^\\d+$";
    private static final String PRICE_REGEX = "^[0-9]{0,6}[,.]{1}[0-9]{0,2}";
    private static final int MIN_IMAGE_SIZE = 0;
    private static final int MAX_IMAGE_SIZE = 1024 * 1024 * 5;
    private static final String JPEG_CONTENT_TYPE = "image/jpeg";
    private static final String JPG_CONTENT_TYPE = "image/jpg";
    private static final String PNG_CONTENT_TYPE = "image/png";
    private static final int MAX_ADDRESS_LENGTH = 300;


    public static boolean isParameterNullOrEmpty(String param) {
        if (param == null || param.isEmpty()) {
            return true;
        }
        else return false;
    }

    public static boolean isValidUsername(String username) {
        return !isParameterNullOrEmpty(username) && username.matches(USERNAME_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return !isParameterNullOrEmpty(email) && email.matches(MAIL_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return  !isParameterNullOrEmpty(password) && password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidConfirmedPassword(String password, String confirmedPassword) {
        return !isParameterNullOrEmpty(password) && password.equals(confirmedPassword);
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return !isParameterNullOrEmpty(cardNumber) && cardNumber.matches(CARD_NUMBER_REGEX);
    }

    public static boolean isValidSecurityCode(String securityCode) {
        return !isParameterNullOrEmpty(securityCode) && securityCode.matches(SECURITY_CODE_REGEX);
    }

    public static boolean isValidCardHolderName(String cardHolderName) {
        return !isParameterNullOrEmpty(cardHolderName) && cardHolderName.matches(CARD_HOLDER_NAME_REGEX);
    }

    public static boolean isValidDate(String expirationDate) {
        if (isParameterNullOrEmpty(expirationDate)) return false;
        YearMonth yearMonth = YearMonth.parse(expirationDate);
        return yearMonth.isAfter(YearMonth.now());
    }

    public static boolean isValidAddress(String address) {
        return !isParameterNullOrEmpty(address) && address.length() <= MAX_ADDRESS_LENGTH;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !isParameterNullOrEmpty(phoneNumber) && phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean isValidBirthDate(String birthDate) {
        if (isParameterNullOrEmpty(birthDate)) return false;
        LocalDate localDate = LocalDate.parse(birthDate);
        return localDate.isBefore(LocalDate.now());
    }

    public static boolean isValidPrice(String price) {
        return !isParameterNullOrEmpty(price) && price.matches(PRICE_REGEX) &&
                new BigDecimal(price).compareTo(new BigDecimal(0.00)) > 0;
    }

    public static boolean isValidInteger(String param) {
        return !isParameterNullOrEmpty(param) && param.matches(INTEGER_REGEX);
    }

    public static boolean isParameterLessThanOrEqual(String param, int length) {
        return param.length() <= length;
    }

    public static boolean isValidImage(Part filePart) {
        return filePart.getSize() > MIN_IMAGE_SIZE && filePart.getSize() < MAX_IMAGE_SIZE &&
                (filePart.getContentType().equals(JPEG_CONTENT_TYPE) ||
                filePart.getContentType().equals(JPG_CONTENT_TYPE) ||
                filePart.getContentType().equals(PNG_CONTENT_TYPE));
    }

    public static boolean isImageLoaded(Part filePart) {
        return filePart.getSize() > 0;
    }
}
