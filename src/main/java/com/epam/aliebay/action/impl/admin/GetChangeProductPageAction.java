package com.epam.aliebay.action.impl.admin;

import com.epam.aliebay.action.Action;
import com.epam.aliebay.dao.DaoFactory;
import com.epam.aliebay.dao.Interface.ProductDao;
import com.epam.aliebay.entity.Product;
import com.epam.aliebay.exception.ProductNotFoundException;
import com.epam.aliebay.dto.ProductDto;
import com.epam.aliebay.util.ActionUtils;
import com.epam.aliebay.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static com.epam.aliebay.constant.AttributeConstants.*;
import static com.epam.aliebay.constant.JspNameConstants.ADD_CHANGE_PRODUCT_JSP;
import static com.epam.aliebay.constant.OtherConstants.*;
import static com.epam.aliebay.constant.RequestParameterNamesConstants.ID_PARAMETER;

public class GetChangeProductPageAction implements Action {
    private final ProductDao productDao = DaoFactory.getDaoFactory().getProductDao();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        Product product = productDao.getProductById(id).orElseThrow(
                () -> new ProductNotFoundException("Cannot find product with id = " + id));
        ProductDto productDto = mapProductToProductDto(product);
        req.getSession().setAttribute(PRODUCT_DTO_ATTRIBUTE, productDto);
        String imageWithoutCodePrefix = product.getImage().substring(LENGTH_OF_PREFIX_TO_SHOW_IMAGE_ON_HTML_PAGE);
        byte[] imageBytes = Base64.getDecoder().decode(imageWithoutCodePrefix);
        req.getSession().setAttribute(EDITED_PRODUCT_IMAGE_BYTES_ATTRIBUTE, imageBytes);
        req.getSession().setAttribute(EDITED_PRODUCT_ID_ATTRIBUTE, product.getId());

        ActionUtils.getDataForProductForm(req);
        req.setAttribute(ACTION_ATTRIBUTE, CHANGE_PRODUCT_FORM_ACTION);
        RoutingUtils.forwardToPage(ADD_CHANGE_PRODUCT_JSP, req, resp);
    }

    private ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(String.valueOf(product.getPrice()));
        productDto.setCategoryId(String.valueOf(product.getCategory().getId()));
        productDto.setProducerId(String.valueOf(product.getProducer().getId()));
        productDto.setCount(String.valueOf(product.getCount()));
        return productDto;
    }
}
