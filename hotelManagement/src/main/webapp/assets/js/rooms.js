const roomCategoryUrlGetAll = "http://localhost:8080/roomsCategory/getAll";
const baseRoomsUrl = "http://localhost:8080/rooms/";
const baseCategoryUrl = "http://localhost:8080/roomsCategory/";


function loadRoomCategories(categories = []) {
    categories.forEach((category) => $("#roomCategory").append("<option>" + category.roomCategoryName + "</option>"));
}

function renderRoomList(roomList = []) {
    $('#room-list').empty();
    roomList.forEach(r => {
        renderRoom(r);
    })
}

function getRandomRoomThumbnail() {
    var images = ['assets/img/pexels-pixabay-164595.jpg', 'assets/img/pexels-suhel-vba-3659683.jpg', 'assets/img/pexels-vecislavas-popa-1743231.jpg'];
    var randomNum = Math.floor(Math.random() * images.length);
    return images[randomNum];
}

function renderRoom(room) {
    const $template = $($('#room-template').html());
    $template.find('.room-category-name').text(room.roomCategory);
    getRoomCategoryByName(room.roomCategory, $template);
    const image = getRandomRoomThumbnail();
    $template.find('.room-thumbnail').attr('src', image);
    const availability = room.available ? 'Yes' : 'No';
    $template.find('.room-availability').text(availability);
    $template.find('.room-number').text(room.roomNumber);
    $template.find('#roomId').attr('value', room.roomId);
    const href = `${"/updateRoom.html?roomId="}${room.roomId}`;
    $template.find('#update-room-btn').attr('href', href);

    $template.find('#delete-room-btn').click((e) => {
        e.preventDefault();
        deleteRoom(room.roomId);

    })

    $('#room-list').append($template);
}

function getAllRoomCategories() {
    $.ajax({
        method: "GET",
        url: `${roomCategoryUrlGetAll}`
    })
        .done(response => {
            roomCategories = response;
            loadRoomCategories(roomCategories);

        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

function getAllRoomFilters() {
    const roomCategory = $('#roomCategory').val();
    const priceGreaterThan = $('#price_greater_than').val();
    const priceLessThan = $('#price_less_than').val();
    filters = { roomCategory, priceGreaterThan, priceLessThan }
    getAllRooms(filters);
}


function getAllRooms(filters) {
    const getAll = "getAll";
    $.ajax({
        method: "GET",
        url: `${baseRoomsUrl}${getAll}`,
        data: { ...filters }
    })
        .done(response => {

            renderRoomList(response);
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
        url: `${baseRoomsUrl}${'deleteRoom'}`,
        data: { id }
    })
        .done(() => {
            window.location.replace("/rooms.html");

        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

function getRoomCategoryValues(category, $template) {
    $template.find('.max-persons').text(category.maxPersons);
    $template.find('.room-price').text(`${category.pricePerNight}${'$'}`);

}

function getRoomCategoryByName(name, $template) {
    return $.ajax({
        method: "GET",
        url: `${baseCategoryUrl}${'getCategoryByName'}`,
        data: { name }
    }).done((response) => {
        console.log(response);
        getRoomCategoryValues(response, $template);
    })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

getAllRooms();
getAllRoomCategories();

