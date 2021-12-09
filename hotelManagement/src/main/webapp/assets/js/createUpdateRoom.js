const roomCategoryUrlGetAll = "http://localhost:8080/roomsCategory/getAll";
const baseRoomsUrl = "http://localhost:8080/rooms/";

function loadRoomCategories(categories = []) {
    categories.forEach((category) => $("#roomCategory").append("<option>" + category.roomCategoryName + "</option>"));
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

function getRoomValues() {
    const roomCategory = $('#roomCategory').val();
    const available = $('input[id=availability]').is(':checked');
    const roomNumber = $('#room-number').val();


    room = { roomCategory, available, roomNumber }
    return room;
}

function createRoom() {
    const room = getRoomValues();
    $.ajax({
        method: "POST",
        url: `${baseRoomsUrl}${'createRoom'}`,
        data: room

    }).done(response => {
        window.location.replace("/rooms.html");
    })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

$('#create-room-btn').click((e) => {
    e.preventDefault();
    createRoom();
})


const id = window.location.href.slice(window.location.href.indexOf('=') + 1);

function renderRoomValues(room) {
    $('#room-number').val(room.roomNumber);
    $('#roomCategory').val(room.roomCategory).change();
    $('#availability').attr('checked', room.available);

}
function getRoom() {

    $.ajax({
        method: "GET",
        url: `${baseRoomsUrl}${"getRoom"}`,
        data: { id }
    })
        .done(response => {
            renderRoomValues(response);
            console.log(response);
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

$('#update-room-btn').click((e) => {
    e.preventDefault();
    updateRoom(id);

})

function updateRoom(id) {
    const roomDto = getRoomValues();
    $.ajax({
        method: "PUT",
        url: `${baseRoomsUrl}${"updateRoom"}`,
        data: { ...roomDto, roomId: id }
    })
        .done(response => {
            window.location.replace("/rooms.html");
        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}

getAllRoomCategories();
getRoom();