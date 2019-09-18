;$(function () {
    var init = function () {
        initBuyBtn();

    };
    var showAddProductPopup = function () {
        var idProduct = $(this).attr('data-id-product');
        var product = $('#product' + idProduct);
        $('#addProductPopup').attr('data-id-product', idProduct);
        $('#addProductPopup .product-image').attr('src', product.find('.image-body img').attr('src'));
        $('#addProductPopup .name').text(product.find('.name').text());
        var price = product.find('.price').text();
        $('#addProductPopup .price').text(price);
        $('#addProductPopup .category').text(product.find('.catecory').text());
        $('#addProductPopup .producer').text(product.find('.producer').text());
        $('#addProductPopup .count').val(1);
        $('#addProductPopup .cost').text(price);
        $('#addToCart').removeClass('d-none');
        $('#addToCartIndicator').addClass('d-none');

        $('#addProductPopup').modal({
            show: true
        })
    };


    init();
})
;