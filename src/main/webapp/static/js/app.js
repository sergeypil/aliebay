$(function () {
    var addProductToCart = function() {
        var idProduct = $(this).data('id-product');
        var count = $('#count-product').val();
        var url = $(this).data('url-add-to-cart');
        var countAvailable = $(this).data('count-available');
        if (count > 0 && count <= countAvailable) {
            $.ajax({
                url: url,
                method: 'POST',
                data: {
                    idProduct: idProduct,
                    count: count
                },
                success: function (data) {
                    $('#count-products-cart').text('(' + data.totalCount + ')');
                },
                error: function (xhr) {
                    alert('Error');
                }
            });
        }
    };

    $('.add-to-cart').click(addProductToCart);

    var removeProductFromCart = function() {
        var idProduct = $(this).data('id-product');
        var url = $(this).data('url-remove-from-cart');
        $.ajax({
            url : url,
            method : 'POST',
            data : {
                idProduct : idProduct,
            },
            success : function(data) {
                $('#count-products-cart').text('(' + data.totalCount + ')');
                $('#tr' + idProduct).remove();
                $('#total-count').text(data.totalCount);
                $('#total-cost').text(data.totalCost);
                if(data.totalCount == 0) {
                    location.reload();
                }
            },
            error : function(xhr) {
                alert('Error');
            }
        });
    };
    $('.remove-from-cart').click(removeProductFromCart);

    var changeOrderStatus = function(e) {
        e.preventDefault();
        var id_order = $(this).data('id-order');
        var id_order_status = $(this).data('id-order-status');
        var current_id_order_status = $(this).data('current-id-order-status');
        if (current_id_order_status !== id_order_status) {
            var url = $(this).data('url-change-order-status');
            var page = $(this).data('page');
            $.ajax({
                url: url,
                method: 'GET',
                data: {
                    id_order: id_order,
                    id_order_status: id_order_status
                },
                success: function (data) {
                    if (data.idOrderStatus == 4) {
                        $('#order-status' + id_order).text(data.orderStatus);
                        $('#order-status' + id_order).removeAttr('href');
                        $('#order-status' + id_order).removeAttr('data-toggle');
                        $('#order-status' + id_order).removeClass('dropdown-toggle');
                    } else {
                        $('#order-status' + id_order).text(data.orderStatus);
                    }
                    $('#orders-tab').addClass('active');
                },
                error: function (xhr) {
                    alert('Error');
                }
            });
        }
    };

    $('.change-order-status').click(changeOrderStatus);

    var changeUserStatus = function(e) {
        e.preventDefault();
        var id_user = $(this).data('id-user');
        var id_user_status = $(this).data('id-user-status');
        var url = $(this).data('url-change-user-status');
        $.ajax({
            url : url,
            method : 'GET',
            data : {
                id_user : id_user,
                id_user_status : id_user_status
            },
            success : function(data) {
                $('#user-status' + id_user).text(data.userStatus);
                $('#dashboard-tab').addClass('active');
            },
            error : function(xhr) {
                alert('Error');
            }
        });
    };

    $('.change-user-status').click(changeUserStatus);


    $( '.dropdown' ).hover(
        function(){
            $(this).children('.dropdown-menu').slideDown(200);
        },
        function(){
            $(this).children('.dropdown-menu').slideUp(200);
        }
    );

    $(".floatNumberField").change(function () {
        $(this).val(parseFloat($(this).val()).toFixed(2))
    })
});
