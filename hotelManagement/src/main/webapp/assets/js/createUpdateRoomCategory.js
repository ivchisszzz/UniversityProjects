const baseCategoryUrl = "http://localhost:8080/roomsCategory/";
const servicesUrlGetAll = "http://localhost:8080/hotelServices/getAll";

function getCategoryValues() {
    const roomCategoryName = $('#room-category-name').val();
    const pricePerNight = $('#room-categoty-price').val();
    const maxPersons = $('#max-persons').val();
    const hotelServiceNames = $('#hotel-services').val();
    category = { roomCategoryName, pricePerNight, maxPersons, hotelServiceNames }
    return category;
}

function loadHotelServices(services = []) {
    services.forEach((service) => $("#hotel-services").append("<option>" + service.hotelServiceName + "</option>"));
}
function getAllServices() {
    $.ajax({
        method: "GET",
        url: `${servicesUrlGetAll}`
    })
        .done(response => {
            services = response;
            loadHotelServices(services);
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}




function createRoomCategory() {
    const category = getCategoryValues();
    $.ajax({
        method: "POST",
        url: `${baseCategoryUrl}${'createRoomCategory'}`,
        data: category

    }).done((response) => {
        window.location.replace("/roomCategories.html");
    })
        .fail(response => {
            alert(response.responseJSON.join("\n"));
        })
        .always(() => {
            console.log('ajax completed');
        });
}

$('#create-category').click((e) => {
    e.preventDefault();
    createRoomCategory();
})

const id = window.location.href.slice(window.location.href.indexOf('=') + 1);

function renderCategoryValues(category) {
    $('#room-category-name').val(category.roomCategoryName);
    $('#room-categoty-price').val(category.pricePerNight);
    $('#max-persons').val(category.maxPersons);
    $('#hotel-services').val(category.hotelServiceNames);
}
function getRoomCategory() {

    $.ajax({
        method: "GET",
        url: `${baseCategoryUrl}${"getCategory"}`,
        data: { id }
    })
        .done(response => {
            renderCategoryValues(response);
            console.log(response);
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

$('#update-category').click((e) => {
    e.preventDefault();
    updateRoomCategory(id);

})

function updateRoomCategory(id) {
    const categoryDto = getCategoryValues();
    $.ajax({
        method: "PUT",
        url: `${baseCategoryUrl}${"updateRoomCategory"}`,
        data: { ...categoryDto, categoryId: id }
    })
        .done((response) => {
            window.location.replace("/roomCategories.html");
        })
        .fail(response => {
            alert(response.responseJSON.join("\n"));
        })
        .always(() => {
            console.log('ajax completed');
        });
}

getAllServices();