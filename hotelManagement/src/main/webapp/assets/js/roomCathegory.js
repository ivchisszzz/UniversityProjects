const roomCategoryUrlGetAll = "http://localhost:8080/roomsCategory/getAll";
const baseCategoryUrl = "http://localhost:8080/roomsCategory/";

function renderRoomCathegoryList(list = []) {
    list.forEach(r => {
        renderRoomCathegory(r);
    })
}

function loadRoomCategories(categories = []) {
    categories.forEach((category) => $("#roomCategory").append("<option>" + category.roomCategoryName + "</option>"));
}
function renderRoomCathegory(category) {
    const $template = $($('#category-list-template').html());
    $template.find('.category-name').text(category.roomCategoryName);
    $template.find('.max-persons').text(category.maxPersons);
    $template.find('.price-per-night').text(`${category.pricePerNight}${'$'}`);
    $template.find('#categoryId').attr('value', category.roomCategoryId);
    const servicesArr = category.hotelServiceNames.values();
    for (const name of servicesArr) {
        $template.find("#services").append("<span>" + name + "</span>")
    }
    const href = `${"/updateRoomCategory.html?categoryId="}${category.categoryId}`;
    $template.find('#update-category-btn').attr('href', href);
    $template.find('#delete-category-btn').click((e) => {
        e.preventDefault();
        deleteRoom(category.roomCategoryId);

    })
    $('#room-category-list').append($template);
}

function getAllRoomCategories() {
    $.ajax({
        method: "GET",
        url: `${roomCategoryUrlGetAll}`
    })
        .done(response => {
            roomCategories = response;
            renderRoomCathegoryList(roomCategories)
            console.log(roomCategories);
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

function deleteRoom(id) {
    $.ajax({
        method: "DELETE",
        url: `${baseCategoryUrl}${'deleteCategory'}`,
        data: { id }
    })
        .done(() => {
            window.location.replace("/roomCategories.html");

        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

getAllRoomCategories();