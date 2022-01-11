package com.epam.aliebay.action;

import com.epam.aliebay.action.impl.*;
import com.epam.aliebay.action.impl.admin.*;
import com.epam.aliebay.action.impl.ajax.AddProductToCartAction;
import com.epam.aliebay.action.impl.ajax.ChangeOrderStatusAction;
import com.epam.aliebay.action.impl.ajax.ChangeUserStatusAction;
import com.epam.aliebay.action.impl.ajax.RemoveProductFromCartAction;

import java.util.HashMap;
import java.util.Map;

import static com.epam.aliebay.constant.ActionConstants.*;

public class ActionFactory {
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    static {
        ACTION_MAP.put(GET_PRODUCTS_PAGE_ACTION, new GetAllProductsAction());
        ACTION_MAP.put(GET_ERROR_PAGE_ACTION, new ErrorAction());
        ACTION_MAP.put(GET_LOGIN_PAGE_ACTION, new GetLoginPageAction());
        ACTION_MAP.put(GET_REGISTER_PAGE_ACTION, new GetRegisterPageAction());
        ACTION_MAP.put(GET_HOME_PAGE_ACTION, new GetHomePageAction());
        ACTION_MAP.put(LOGIN_USER_ACTION, new LoginUserAction());
        ACTION_MAP.put(REGISTER_USER_ACTION, new RegisterUserAction());
        ACTION_MAP.put(LOGOUT_ACTION, new LogoutAction());
        ACTION_MAP.put(GET_CATEGORIES_PAGE_ACTION, new GetCategoriesAction());
        ACTION_MAP.put(GET_PRODUCT_BY_ID_ACTION, new GetProductByIdAction());
        ACTION_MAP.put(GET_SHOPPING_CART_PAGE_ACTION, new GetShoppingCartAction());
        ACTION_MAP.put(ADD_PRODUCT_TO_CART_ACTION, new AddProductToCartAction());
        ACTION_MAP.put(REMOVE_PRODUCT_FROM_CART_ACTION, new RemoveProductFromCartAction());
        ACTION_MAP.put(CHANGE_LANGUAGE_ACTION, new ChangeLanguageAction());
        ACTION_MAP.put(GET_ABOUT_COMPANY_PAGE_ACTION, new GetAboutCompanyPageAction());
        ACTION_MAP.put(PURCHASE_POLICY_PAGE_ACTION, new GetPurchasePolicyPageAction());
        ACTION_MAP.put(GET_ACCOUNT_PAGE_ACTION, new GetAccountPageAction());
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction());
        ACTION_MAP.put(GET_CHECKOUT_PAGE_ACTION, new GetCheckoutPageAction());
        ACTION_MAP.put(GET_ADMIN_PAGE_ACTION, new GetAdminPageAction());
        ACTION_MAP.put(CHANGE_ORDER_STATUS_ACTION, new ChangeOrderStatusAction());
        ACTION_MAP.put(CHANGE_USER_STATUS_ACTION, new ChangeUserStatusAction());
        ACTION_MAP.put(MAKE_ORDER_ACTION, new MakeOrderAction());
        ACTION_MAP.put(UPDATE_USER_ACTION, new UpdateUserAction());
        ACTION_MAP.put(GET_ADMIN_ORDERS_PAGE_ACTION, new GetAllOrdersPageAction());
        ACTION_MAP.put(GET_ADMIN_USERS_PAGE_ACTION, new GetAllUsersPageAction());
        ACTION_MAP.put(GET_ADD_PRODUCT_PAGE_ACTION, new GetAddProductPageAction());
        ACTION_MAP.put(GET_ADD_PRODUCER_PAGE_ACTION, new GetAddProducerPageAction());
        ACTION_MAP.put(GET_ADD_CATEGORY_PAGE_ACTION, new GetAddCategoryPageAction());
        ACTION_MAP.put(ADD_PRODUCT_ACTION, new AddProductToDatabaseAction());
        ACTION_MAP.put(ADD_CATEGORY_ACTION, new AddCategoryToDatabaseAction());
        ACTION_MAP.put(ADD_PRODUCER_ACTION, new AddProducerToDatabaseAction());
        ACTION_MAP.put(GET_ADMIN_PRODUCTS_PAGE_ACTION, new GetAllProductsAsAdminAction());
        ACTION_MAP.put(GET_ADMIN_PRODUCERS_PAGE_ACTION, new GetAllProducersAsAdminAction());
        ACTION_MAP.put(GET_ADMIN_CATEGORIES_PAGE_ACTION, new GetAllCategoriesAsAdminAction());
        ACTION_MAP.put(GET_CHANGE_PRODUCT_PAGE_ACTION, new GetChangeProductPageAction());
        ACTION_MAP.put(GET_CHANGE_PRODUCER_PAGE_ACTION, new GetChangeProducerPageAction());
        ACTION_MAP.put(GET_CHANGE_CATEGORY_PAGE_ACTION, new GetChangeCategoryPageAction());
        ACTION_MAP.put(CHANGE_PRODUCT_ACTION, new ChangeProductAction());
        ACTION_MAP.put(CHANGE_PRODUCER_ACTION, new ChangeProducerAction());
        ACTION_MAP.put(CHANGE_CATEGORY_ACTION, new ChangeCategoryAction());
        ACTION_MAP.put(REMOVE_PRODUCT_FROM_DATABASE_ACTION, new RemoveProductFromDatabase());
        ACTION_MAP.put(REMOVE_PRODUCER_FROM_DATABASE_ACTION, new RemoveProducerFromDatabase());
        ACTION_MAP.put(REMOVE_CATEGORY_FROM_DATABASE_ACTION, new RemoveCategoryFromDatabase());
    }

    public static ActionFactory getInstance() {
        return ACTION_FACTORY;
    }

    public Action getCommand(String path) {
        Action action = ACTION_MAP.get(path.toLowerCase());
        if (action == null) {
            action = ACTION_MAP.get(GET_ERROR_PAGE_ACTION);
        }
        return action;
    }
}
