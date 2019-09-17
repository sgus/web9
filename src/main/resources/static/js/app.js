;$(function () {
    var init = function () {
        initBuyBtn();
        $('#editProfile').click(addProductToCart);
        $('#addProductPopup .count').change(calculateCost);
        $('#loadMore').click(loadMoreProducts);
        initSearchForm();
        $('#goSearch').click(goSearch);
        $('.remove-product').click(removeProductFromCart);
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

    var initBuyBtn = function () {
        $('.buy-btn').click(showAddProductPopup);
    };

    var addProductToCart = function () {
        var idProduct = $('#addProductPopup').attr('data-id-product');
        console.log(idProduct);
        var count = $('#addProductPopup .count').val();
        $('#addToCart').addClass('d-none');
        $('#addToCartIndicator').removeClass('d-none');
        setTimeout(function () {
            var data = {
                totalCount: count,
                totalCost: 2000
            };
            $('#currentShoppingCart .total-count').text(data.totalCount);
            $('#currentShoppingCart .total-cost').text(data.totalCost);
            $('#currentShoppingCart').removeClass('d-none');
            $('#addProductPopup').modal('d-none');

        }, 800);
    };

    var calculateCost = function () {
        var priceStr = $('#addProductPopup .price').text();
        var price = parseFloat(priceStr.replace('₽', ' '));
        var count = parseInt($('#addProductPopup .count').val());
        var min = parseInt($('#addProductPopup .count').attr('min'));
        var max = parseInt($('#addProductPopup .count').attr('max'));
        if (count >= min && count <= max) {
            var cost = price * count;
            $('#addProductPopup .cost').text(cost + ' ₽');

        } else {
            $('#addProductPopup .count').val(1);
            $('#addProductPopup .cost').text(priceStr);
        }
    };
    var loadMoreProducts = function () {
        var btn = $('#loadMore');
        convertButtonToLoader(btn, 'btn-success');
        var pageNumber = parseInt($('#productList').attr('data-page-number'));
        var url = '/ajax/html/more/' + (pageNumber + 1);
        $.ajax({
            url: url,
            success: function (data) {
                console.log(data);
                // $('#productList tbody').append(html);
                pageNumber++;
                var pageCount = parseInt($('#productList').attr('data-page-count'));
                $('#productList').attr('data-page-number', pageNumber);


                if (pageNumber < pageCount) {
                    convertLoaderToButton(btn, 'btn-success', loadMoreProducts);
                } else {
                    btn.remove();
                }
            },
            error: function (xhr) {
                convertLoaderToButton(btn, 'btn-success', loadMoreProducts);
                if (xhr.status == 401) {
                    window.location.href = '/sign-in';
                } else {
                    console.log("error")
                }
            }
        });

        setTimeout(function () {
            $('#loadMoreIndicator').addClass('d-none');
            $('#loadMore').removeClass('d-none');
        }, 800);
    };
    var convertButtonToLoader = function (btn, btnClass) {
        btn.removeClass(btnClass);
        btn.removeClass('btn');
        btn.addClass('load-indicator');
        var text = btn.text();
        btn.text('');
        btn.attr('data-btn-text', text);
        btn.off('click');
    };

    var convertLoaderToButton = function (btn, btnClass, actionClick) {
        btn.removeClass('load-indicator');
        btn.addClass('btn');
        btn.addClass(btnClass);
        btn.text(btn.attr('data-btn-text'));
        btn.removeAttr('data-btn-text');
        btn.click(actionClick);
    };
    var initSearchForm = function () {
        $('#allGenre').click(function () {
            $('.genres .search-opt').prop('checked', $(this).is(':checked'));
        });
        $('.genres .search-opt').click(function () {
            $('#allGenre').prop('checked', false);
        });
        $('#allAuthor').click(function () {
            $('.authors .search-opt').prop('checked', $(this).is(':checked'));
        });
        $('.authors .search-opt').click(function () {
            $('#allAuthor').prop('checked', false);
        });
    };

    var goSearch = function () {

        var isAllSelected = function (selector) {
            var checked = 0;
            var unchecked = 0;
            $(selector).each(function (index, value) {
                if (!$(value).is(':checked')) {
                    unchecked++;
                }
            });
            return unchecked === 0;
        };
        if (isAllSelected('.genres search-opt')) {
            $('.genres search-opt').prop('checked', false);
        }
        if (isAllSelected('.authors search-opt')) {
            $('.authors search-opt').prop('checked', false);
        }

        $('form.search').submit();
    };

    var confirm = function (message, okFunction) {
        if (window.confirm(message)) {
            okFunction();
        }
    };
    var removeProductFromCart = function () {
        var btn = $(this);
        if (confirm('Вы уверены?', function () {
            executeRemoveProduct(btn);
        })) ;
    };
    var executeRemoveProduct = function (btn) {
        var idProduct = $(this).attr('data-id-product');
        var count = $('.count');
        btn.removeClass('btn-danger');
        btn.removeClass('btn');
        btn.addClass('load-indicator');
        var text = btn.text();
        btn.text('');
        btn.off('click');


        setTimeout(function () {
            var idProduct = btn.attr('data-id-product');
            var count = btn.attr('data-count');
            btn.removeClass('btn-danger');
            btn.removeClass('btn');
            btn.addClass('load-indicator');
            btn.text('');
            btn.off('click');
            console.log(text);


            setTimeout(function () {
                var data = {
                    totalCount: 1,
                    totalCost: 1
                };
                if (data.totalCount === 0) {
                    window.location.href = 'index.html';
                } else {
                    var prevCount = parseInt($('#product' + idProduct + ' .count').text());
                    var remCount = parseInt(count);
                    if (remCount === prevCount) {
                        $('#product' + idProduct).remove();


                    } else {
                        btn.removeClass('load-indicator');
                        btn.text(text);

                        btn.addClass('btn');
                        btn.addClass('btn-danger');
                        btn.click(removeProductFromCart);
                        $('#product' + idProduct + ' .count').text(prevCount - remCount);
                        if (prevCount - remCount == 1) {
                            $('#product' + idProduct + ' a.remove-product.all').remove();
                        }
                    }
                    refreshTotalCost();
                }
            }, 1000);

        })

    };

    var refreshTotalCost = function () {
        var total = 0;
        $('#shoppingCart .item').each(function (index, value) {
            var count = parseInt($(value).find('.count').text());
            var price = parseFloat($(value).find('.price').text().replace('₽', ' '));
            var val = price * count;
            total = total + val;
        });
        $('#shoppingCart .total').text(total + ' ₽');
    };
    init();
})
;