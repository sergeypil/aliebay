(function ($) {
    "use strict";
    
    // Header slider
    $('.header-slider').slick({
        autoplay: true,
        dots: true,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1
    });
    
    // Product Slider 3 Column
    $('.product-slider-3').slick({
        autoplay: true,
        infinite: true,
        dots: false,
        slidesToShow: 3,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 2,
                }
            },
            {
                breakpoint: 576,
                settings: {
                    slidesToShow: 1,
                }
            },
        ]
    });
    
    // Brand Slider
    $('.brand-slider').slick({
        speed: 5000,
        autoplay: true,
        autoplaySpeed: 0,
        cssEase: 'linear',
        slidesToShow: 5,
        slidesToScroll: 1,
        infinite: true,
        swipeToSlide: true,
        centerMode: true,
        focusOnSelect: false,
        arrows: false,
        dots: false,
        responsive: [
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 4,
                }
            },
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 576,
                settings: {
                    slidesToShow: 2,
                }
            },
            {
                breakpoint: 300,
                settings: {
                    slidesToShow: 1,
                }
            }
        ]
    });

    // Widget slider
    $('.sidebar-slider').slick({
        autoplay: true,
        dots: false,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1
    });
    
    
  /*  // Quantity
    $('.qty button').on('click', function () {
        var countAvailable = $(this).data('count-available');
        var $button = $(this);
        var oldValue = $button.parent().find('input').val();
        var btnMinus = $('.btn-minus');
        var btnPlus = $('.btn-plus');
        var newValue;
        if ($button.hasClass('btn-plus')) {
            if (oldValue < countAvailable) {
                newValue = parseFloat(oldValue) + 1;
                btnPlus.show();
                btnMinus.show();
            }
            else {
                newValue = oldValue;
                btnPlus.hide();
                btnMinus.show();
            }
        } else {
            if (oldValue > 1) {
                newValue = parseFloat(oldValue) - 1;
                btnPlus.show();
                btnMinus.show();
            } else {
                newValue = 1;
                btnMinus.hide();
                btnPlus.show();
            }
        }
        $button.parent().find('input').val(newValue);
    });*/

})(jQuery);

