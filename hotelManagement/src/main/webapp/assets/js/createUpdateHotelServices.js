const baseServicesUrl = "http://localhost:8080/hotelServices/";

function getHotelServiceValues() {
    const hotelServiceName = $('#service-name').val();
    const price = $('#service-price').val();
    service = { hotelServiceName, price }
    return service;
}

function createHotelService() {
    const service = getHotelServiceValues();
    $.ajax({
        method: "POST",
        url: `${baseServicesUrl}${'createService'}`,
        data: service

    }).done(response => {
        window.location.replace("/hotelServies.html");
    })
        .fail(response => {
            alert(response.responseJSON.join("\n"));
        })
        .always(() => {
            console.log('ajax completed');
        });
}

const id = window.location.href.slice(window.location.href.indexOf('=') + 1);

function renderServiceValues(service) {
    $('#service-name').val(service.hotelServiceName);
    $('#service-price').val(service.price);

}
function getHotelService() {
    $.ajax({
        method: "GET",
        url: `${baseServicesUrl}${'getService'}`,
        data: { id }

    }).done(response => {

        renderServiceValues(response);
    })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });

}

$('#create-service').click((e) => {
    e.preventDefault();
    createHotelService();

})

function updateHotelService() {
    const dto = getHotelServiceValues();

    $.ajax({
        method: "PUT",
        url: `${baseServicesUrl}${'updateService'}`,
        data: { ...dto, serviceId: id }

    }).done(response => {
        window.location.replace("hotelServies.html");
    })
        .fail(response => {
            alert(response.responseJSON.join("\n"));
        })
        .always(() => {
            console.log('ajax completed');
        });
}

$('#update-service').click((e) => {
    e.preventDefault();
    updateHotelService();

})

