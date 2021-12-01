const apiBaseUrl = "http://makeup-api.herokuapp.com/api/v1/products.json";
const imgbaseUrl = "http:";

let view = 'list';
let products = [];

function initProductTypeSelect() {
    $('#productType').change((p) => {
        const $select = $(p.currentTarget);
        const value = $select.val();
        const productType = $select.find('option:selected').text();
        $('#product-type-result').text(productType);
        const params = { product_type: value }
        getProducts(params);

    })
}

function renderProductList() {
    $('#product-list').empty();
    const list = products.filter(product => product.price > 0);
    if(list.length == 0){
        $('#no-results-available').removeClass('d-none');
    }
    list.forEach(p => {
        renderProduct(p);
    })

}

function renderProduct(product) {
    const templateSelector = `#product-${view}-template`;
    const $template = $($(templateSelector).html());
    $template.find('.makeup-product-name').text(product.name);
    $template.find('.product-brand').text(product.brand);
    $template.find('.product-description').text(product.description);
    $template.find('.product-price').text(`${product.price}${product.price_sign || '$'}`);
    $template.find('.product-thumbnail').attr('src', `${imgbaseUrl}${product.api_featured_image}`);

    $('#product-list').append($template);
}

function getProducts() {
$('#no-results-available').addClass('d-none');
const params =  getProductsParameters();
    $.ajax({

        method: "GET",
        url: `${apiBaseUrl}`,
        data: params

    })
        .done(response => {
            products = response;
            console.log(products);
            renderProductList();
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

function getProductsParameters() {
    const product_type = $('#productType').val();
    const category = $('#category').val();
    const brand = $('#brand-sort').val();
    const priceGreaterThan = $('#price_greater_than').val();
    const priceLessThan = $('#price_less_than').val();

    params = {
        product_type,
        product_category: category,
        brand,
        price_greater_than: priceGreaterThan,
        price_less_than: priceLessThan
    }
     filteredParams = Object.keys(params).filter((key) => params[key] != "").reduce((a,key) => ({...a, [key]: params[key]}),{});
     console.log(filteredParams);
   
    
     return filteredParams;
    
   

}

$('#get-products').click(() => {
    $('#no-results-available').addClass('d-none');  
   getProducts();
})

$('#grid-view').click((e) => {
    $(e.currentTarget).addClass('btn-dark');
    $('#list-view').addClass('btn-outline-dark').removeClass('btn-dark');
    view = 'grid';
    renderProductList();
})

$('#list-view').click((e) => {
    $(e.currentTarget).addClass('btn-dark');
    $('#grid-view').addClass('btn-outline-dark').removeClass('btn-dark');
    view = 'list';
    renderProductList();

})

$('#clear-fields').click(() => {
    $('#product-filters-form')[0].reset();
    $('#product-list').empty();
    $('#no-results-available').addClass('d-none');
    getProducts();

})

getProducts();

