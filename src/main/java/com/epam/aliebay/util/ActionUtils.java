package com.epam.aliebay.util;

import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Language;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.CategoryNotFoundException;
import com.epam.aliebay.exception.ProducerNotFoundException;
import com.epam.aliebay.dto.CategoryDto;
import com.epam.aliebay.dto.ProductDto;
import com.epam.aliebay.dto.RegisterDto;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.model.ShoppingCart;
import com.epam.aliebay.model.ShoppingCartItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;

public final class ActionUtils {
    private static final String PRODUCTS_SPLIT_SYMBOL = "|";
    private static final String PRODUCT_AND_COUNT_SPLIT_SYMBOL = "-";

    public static void getDataForProductForm(HttpServletRequest req) {
        CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();
        ProducerDao producerDao = DaoFactory.getDaoFactory().getProducerDao();
        List<Category> categories = categoryDao.getLeafCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(LEAF_CATEGORIES_ATTRIBUTE, categories);

        List<Producer> producers = producerDao.getAllProducers();
        req.getSession().setAttribute(ALL_PRODUCERS_ATTRIBUTE, producers);
    }

    public static void deleteAttributesFromSessionAfterFinishEdit(HttpServletRequest req) {
        req.getSession().removeAttribute(PRODUCT_DTO_ATTRIBUTE);
        req.getSession().removeAttribute(ALL_PRODUCERS_ATTRIBUTE);
        req.getSession().removeAttribute(LEAF_CATEGORIES_ATTRIBUTE);
    }

    public static void getDataForChangeCategoryForm(HttpServletRequest req, int id) {
        CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();
        List<Category> categories = categoryDao.getAllCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        categories = categories.stream()
                .filter(cat -> cat.getId() != id)
                .collect(Collectors.toList());
        req.getSession().setAttribute(ALL_CATEGORIES_ATTRIBUTE, categories);
    }

    public static void getDataForAddCategoryForm(HttpServletRequest req) {
        CategoryDao categoryDao = DaoFactory.getDaoFactory().getCategoryDao();
        List<Category> categories = categoryDao.getAllCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(ALL_CATEGORIES_ATTRIBUTE, categories);
    }

    public static String serializeShoppingCart(Map<Product, ShoppingCartItem> productsInShoppingCart) {
        StringBuilder cookieCart = new StringBuilder();
        productsInShoppingCart.forEach((id, item) -> cookieCart
                .append(id.getId())
                .append(PRODUCT_AND_COUNT_SPLIT_SYMBOL)
                .append(item.getCount())
                .append(PRODUCTS_SPLIT_SYMBOL));
        return String.valueOf(cookieCart);
    }

    public static List<Producer> getProducersByProducts(List<Product> products) {
        return products.stream()
                .map(Product::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }

    public static CategoryDto createCategoryDtoFromRequest(HttpServletRequest req) throws IOException, ServletException {
        List<Language> languages = (List<Language>) req.getSession().getAttribute(APP_LANGUAGES_ATTRIBUTE);
        CategoryDto categoryDto = new CategoryDto();
        if (ValidationUtils.isRequestContainsMultipartContent(req)) {
            categoryDto.setImagePart(req.getPart(IMAGE_PARAMETER));
        }
        categoryDto.setParentCategoryId(req.getParameter(PARENT_CATEGORY_PARAMETER));
        Map<Integer, String> langIdToCategoryName = categoryDto.getLangIdToCategoryName();
        languages.forEach(lang -> langIdToCategoryName.put(lang.getId(), req.getParameter(CATEGORY_LANG_ID_PARAMETER + lang.getId())));
        return categoryDto;
    }

    public static ProductDto createProductDtoFromRequest(HttpServletRequest req) throws IOException, ServletException {
        ProductDto productDto = new ProductDto();
        productDto.setName(req.getParameter(PRODUCT_NAME_PARAMETER));
        productDto.setDescription(req.getParameter(DESCRIPTION_PARAMETER));
        productDto.setPrice(req.getParameter(PRICE_PARAMETER));
        productDto.setCategoryId(req.getParameter(CATEGORY_PARAMETER));
        productDto.setProducerId(req.getParameter(PRODUCER_PARAMETER));
        productDto.setCount(req.getParameter(COUNT_PARAMETER));
        if (ValidationUtils.isRequestContainsMultipartContent(req)) {
            productDto.setImagePart(req.getPart(IMAGE_PARAMETER));
        }
        return productDto;
    }

    public static void setAttributesWhenValidationErrorOnCategoryForm(HttpServletRequest req, CategoryDto categoryDto,
                                                                      Map<Integer, Set<String>> langToValidationErrorAttributes,
                                                                      String action) {
        langToValidationErrorAttributes.get(null).forEach(attr -> req.setAttribute(attr, true));
        Map<Integer, Boolean> langToWrongName = langToValidationErrorAttributes.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), true))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        req.getSession().setAttribute(CATEGORY_DTO_ATTRIBUTE, categoryDto);
        req.setAttribute(LANG_TO_WRONG_NAME_ATTRIBUTE, langToWrongName);
        req.setAttribute(ACTION_ATTRIBUTE, action);
    }

    public static void setAttributesWhenValidationErrorOnProductForm(HttpServletRequest req, ProductDto productDto,
                                                                     Set<String> validationErrorAttributes,
                                                                     String action) {
        validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
        req.getSession().setAttribute(PRODUCT_DTO_ATTRIBUTE, productDto);
        req.setAttribute(ACTION_ATTRIBUTE, action);
    }

    public static void setAttributesWhenValidationErrorOnProducerForm(HttpServletRequest req, String editedProducerName,
                                                                      Set<String> validationErrorAttributes,
                                                                      String action) {
        validationErrorAttributes.forEach(attr -> req.setAttribute(attr, true));
        req.getSession().setAttribute(EDITED_PRODUCER_NAME_ATTRIBUTE, editedProducerName);
        req.setAttribute(ACTION_ATTRIBUTE, action);
    }

    public static Product mapProductDtoToProduct(HttpServletRequest req, ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        BigDecimal price = (productDto.getPrice().contains(",")) ?
                new BigDecimal(productDto.getPrice().replace(",", ".")) :
                new BigDecimal(productDto.getPrice());
        product.setPrice(price);
        int idCategory = Integer.parseInt(productDto.getCategoryId());
        List<Category> categories = (List<Category>) req.getSession().getAttribute(LEAF_CATEGORIES_ATTRIBUTE);
        Category category = categories.stream()
                .filter(cat -> cat.getId() == idCategory)
                .findAny().orElseThrow(() -> new CategoryNotFoundException("Cannot find category with id = " + idCategory));
        product.setCategory(category);
        int idProducer = Integer.parseInt(req.getParameter(PRODUCER_PARAMETER));
        List<Producer> producers = (List<Producer>) req.getSession().getAttribute(ALL_PRODUCERS_ATTRIBUTE);
        Producer producer = producers.stream()
                .filter(pr -> pr.getId() == idProducer)
                .findAny().orElseThrow(() -> new ProducerNotFoundException("Cannot find producer with id = " + idProducer));
        product.setProducer(producer);
        product.setCount(Integer.parseInt(productDto.getCount()));
        return product;
    }

    public static RegisterDto createRegisterDtoFromRequest(HttpServletRequest req) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(req.getParameter(USERNAME_PARAMETER));
        registerDto.setFirstName(req.getParameter(FIRST_NAME_PARAMETER));
        registerDto.setLastName(req.getParameter(LAST_NAME_PARAMETER));
        registerDto.setBirthDate(req.getParameter(BIRTH_DATE_PARAMETER));
        registerDto.setEmail(req.getParameter(EMAIL_PARAMETER));
        registerDto.setPhoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER));
        registerDto.setPassword(req.getParameter(PASSWORD_PARAMETER));
        registerDto.setConfirmedPassword(req.getParameter(CONFIRMED_PASSWORD_PARAMETER));
        return registerDto;
    }


    public static Map<Integer, Set<String>> createMapLangToValidationsErrorAttributes(CategoryDto categoryDto) {
        Map<Integer, Set<String>> langToValidationErrorNames = new HashMap<>();
        categoryDto.getLangIdToCategoryName().forEach((langId, name) -> langToValidationErrorNames.put(langId, new HashSet<>()));
        langToValidationErrorNames.put(null, new HashSet<>());
        return langToValidationErrorNames;
    }

    public static boolean isCountOfProductsInCartMatchesCountInDb(HttpServletRequest req, ProductDao productDao) {
        AtomicBoolean isMatch = new AtomicBoolean(true);
        ShoppingCart cart = (ShoppingCart) req.getSession().getAttribute(CURRENT_SHOPPING_CART_ATTRIBUTE);
        cart.getProducts().forEach(((product, shoppingCartItem) -> {
            Product productFromDb = productDao.getProductById(product.getId()).orElseThrow(
                    () -> new ProductNotFoundException("Cannot find product with id = " + product.getId()));
            if (productFromDb.getCount() < shoppingCartItem.getCount()) {
                isMatch.set(false);
            }
        }));
        return isMatch.get();
    }
}
