package com.epam.aliebay.util;

import com.epam.aliebay.constant.AttributeConstants;
import com.epam.aliebay.dao.Interface.CategoryDao;
import com.epam.aliebay.dao.PostgreSqlDaoFactory;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Category;
import com.epam.aliebay.entity.Language;
import com.epam.aliebay.entity.Producer;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.CategoryNotFoundException;
import com.epam.aliebay.exception.ProducerNotFoundException;
import com.epam.aliebay.model.ShoppingCartItem;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.AttributeConstants.WRONG_EMPTY_IMAGE_ATTRIBUTE;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.IMAGE_PARAMETER;

public final class ActionUtils {
    private static final Logger logger = Logger.getLogger(ActionUtils.class);
    private static final int BUFFER_LENGTH = 4 * 1024;

    public static void getDataForProductForm(HttpServletRequest req) {
        CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();
        ProducerDao producerDao = PostgreSqlDaoFactory.getInstance().getProducerDao();
        List<Category> categories = categoryDao.getLeafCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(LEAF_CATEGORIES_ATTRIBUTE, categories);

        List<Producer> producers = producerDao.getAllProducers();
        req.getSession().setAttribute(ALL_PRODUCERS_ATTRIBUTE, producers);
    }

    public static void getDataForChangeCategoryForm(HttpServletRequest req, int id) {
        CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();
        List<Category> categories = categoryDao.getAllCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        categories = categories.stream()
                .filter(cat -> cat.getId() != id)
                .collect(Collectors.toList());
        req.getSession().setAttribute(ALL_CATEGORIES_ATTRIBUTE, categories);
    }

    public static void getDataForAddCategoryForm(HttpServletRequest req) {
        CategoryDao categoryDao = PostgreSqlDaoFactory.getInstance().getCategoryDao();
        List<Category> categories = categoryDao.getAllCategories(
                (String) req.getSession().getAttribute(AttributeConstants.CURRENT_LANGUAGE_ATTRIBUTE));
        req.getSession().setAttribute(ALL_CATEGORIES_ATTRIBUTE, categories);
    }

    public static boolean validateRequestFromProductForm(HttpServletRequest req, Product editedProduct) throws IOException, ServletException {
        boolean areAllParametersValid = true;
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(PRODUCT_NAME_PARAMETER)) ||
                !ValidationUtils.isParameterLessThanOrEqual(req.getParameter(PRODUCT_NAME_PARAMETER), LIMIT_LENGTH_OF_PRODUCT_NAME)) {
            req.setAttribute(WRONG_NAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedProduct.setName(req.getParameter(PRODUCT_NAME_PARAMETER));
        }
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(DESCRIPTION_PARAMETER)) ||
                !ValidationUtils.isParameterLessThanOrEqual(req.getParameter(DESCRIPTION_PARAMETER), LIMIT_LENGTH_OF_PRODUCT_DESCRIPTION)) {
            req.setAttribute(WRONG_DESCRIPTION_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedProduct.setDescription(req.getParameter(DESCRIPTION_PARAMETER));
        }
        if (!ValidationUtils.isValidPrice(req.getParameter(PRICE_PARAMETER))) {
            req.setAttribute(WRONG_PRICE_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedProduct.setPrice(new BigDecimal(req.getParameter(PRICE_PARAMETER)));
        }
        if (!ValidationUtils.isParameterNullOrEmpty(req.getParameter(CATEGORY_PARAMETER))) {
            int idCategory = Integer.parseInt(req.getParameter(CATEGORY_PARAMETER));
            List<Category> categories = (List<Category>) req.getSession().getAttribute(LEAF_CATEGORIES_ATTRIBUTE);
            Category category = categories.stream()
                    .filter(el -> el.getId() == idCategory)
                    .findAny().orElseThrow(() -> new CategoryNotFoundException("Cannot find category with id = " + idCategory));
            editedProduct.setCategory(category);
        }
        if (!ValidationUtils.isParameterNullOrEmpty(req.getParameter(PRODUCER_PARAMETER))) {
            int idProducer = Integer.parseInt(req.getParameter(PRODUCER_PARAMETER));
            List<Producer> producers = (List<Producer>) req.getSession().getAttribute(ALL_PRODUCERS_ATTRIBUTE);
            Producer producer = producers.stream()
                    .filter(el -> el.getId() == idProducer)
                    .findAny().orElseThrow(() -> new ProducerNotFoundException("Cannot find producer with id = " + idProducer));
            editedProduct.setProducer(producer);
        }
        if (!ValidationUtils.isValidInteger(req.getParameter(COUNT_PARAMETER))) {
            req.setAttribute(WRONG_COUNT_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedProduct.setCount(Integer.parseInt(req.getParameter(COUNT_PARAMETER)));
        }
        if (ValidationUtils.isImageLoaded(req.getPart(IMAGE_PARAMETER))) {
            if (ValidationUtils.isValidImage(req.getPart(IMAGE_PARAMETER))) {
                String image = transformPartToString(req);
                editedProduct.setImage(image);
            } else {
                req.setAttribute(WRONG_IMAGE_ATTRIBUTE, true);
                areAllParametersValid = false;
            }
        } else {
            if (editedProduct.getImage() == null) {
                req.setAttribute(WRONG_EMPTY_IMAGE_ATTRIBUTE, true);
                areAllParametersValid = false;
            }
        }
        return areAllParametersValid;
    }

    public static boolean validateRequestFromProducerForm(HttpServletRequest req, Producer editedProducer) throws IOException, ServletException {
        boolean areAllParametersValid = true;
        if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(PRODUCER_NAME_PARAMETER)) ||
                !ValidationUtils.isParameterLessThanOrEqual(req.getParameter(PRODUCER_NAME_PARAMETER), LIMIT_LENGTH_OF_PRODUCER_NAME)) {
            req.setAttribute(WRONG_NAME_ATTRIBUTE, true);
            areAllParametersValid = false;
        } else {
            editedProducer.setName(req.getParameter(PRODUCER_NAME_PARAMETER));
        }
        return areAllParametersValid;
    }
    public static boolean validateRequestFromCategoryForm(HttpServletRequest req, Map<Integer, Category> langToCategory,
                                                          Map<Integer, Boolean> langToWrongName)
            throws IOException, ServletException {
        List<Language> languages = (List<Language>) req.getServletContext().getAttribute(APP_LANGUAGES_ATTRIBUTE);
        AtomicBoolean areAllParametersValid = new AtomicBoolean(true);
        if (ValidationUtils.isImageLoaded(req.getPart(IMAGE_PARAMETER))) {
            if (ValidationUtils.isValidImage(req.getPart(IMAGE_PARAMETER))) {
                String image = transformPartToString(req);
                langToCategory.get(INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA).setImage(image);
            } else {
                req.setAttribute(WRONG_IMAGE_ATTRIBUTE, true);
                areAllParametersValid.set(false);
            }
        } else {
            if (langToCategory.get(INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA).getImage() == null) {
                req.setAttribute(WRONG_EMPTY_IMAGE_ATTRIBUTE, true);
                areAllParametersValid.set(false);
            }
        }
        int idParentCategory = Integer.parseInt(req.getParameter(PARENT_CATEGORY_PARAMETER));
        langToCategory.get(INDEX_OF_MAP_LANG_TO_CATEGORY_WHERE_STORE_ENTERED_DATA).setParentCategoryId(idParentCategory);
        languages.stream()
                .forEach(el -> {
                    if (ValidationUtils.isParameterNullOrEmpty(req.getParameter(CATEGORY_LANG_ID_PARAMETER + el.getId())) ||
                            !ValidationUtils.isParameterLessThanOrEqual(req.getParameter(CATEGORY_LANG_ID_PARAMETER + el.getId()),
                                    LIMIT_LENGTH_OF_CATEGORY_NAME)) {
                        langToWrongName.put(el.getId(), true);
                        areAllParametersValid.set(false);
                    } else {
                        langToCategory.get(el.getId()).setName(req.getParameter(CATEGORY_LANG_ID_PARAMETER + el.getId()));
                    }
                });

        return areAllParametersValid.get();
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[BUFFER_LENGTH];
        int readLen;
        IOException exception = null;
        try {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readLen = inputStream.read(buffer, 0, BUFFER_LENGTH)) != -1)
                    outputStream.write(buffer, 0, readLen);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            logger.error("Cannot read file", e);
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }

    private static String transformPartToString(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart(IMAGE_PARAMETER);
        InputStream fileContent = filePart.getInputStream();
        byte[] imageBytes = ActionUtils.readAllBytes(fileContent);
        return PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE +
                Base64.getEncoder().encodeToString(imageBytes);
    }

    public static String serializeShoppingCart(Map<Product, ShoppingCartItem> productsInShoppingCart) {
        StringBuilder coockieCart = new StringBuilder();
        productsInShoppingCart.entrySet().stream()
                .forEach(el -> coockieCart
                        .append(el.getKey().getId())
                        .append("-")
                        .append(el.getValue().getCount())
                        .append("|"));
        return String.valueOf(coockieCart);
    }
}
