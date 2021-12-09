const servicesUrlGetAll = "http://localhost:8080/hotelServices/getAll";
const baseSrviceUrl = "http://localhost:8080/hotelServices/";

function renderServiceList(list = []) {
    $('#hotel-service-list').empty();
    list.forEach(r => {
        renderService(r);
    })
}

function renderService(service) {
    const $template = $($('#service-list-template').html());
    $template.find('.hotel-service-name ').text(service.hotelServiceName);
    $template.find('.price').text(`${service.price}${'$'}`);
    $template.find('#serviceId').attr('value', service.hotelServiceId);
    const href = `${"/updateServices.html?serviceId="}${service.serviceId}`;
    $template.find('#update-service').attr('href', href);
    $template.find('#delete-service').click((e) => {
        e.preventDefault();
        deleteRoom(service.serviceId);

    })

    $('#hotel-service-list').append($template);


}

function getAllServices() {
    $.ajax({
        method: "GET",
        url: `${servicesUrlGetAll}`
    })
        .done(response => {
            services = response;
            renderServiceList(services)
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
        url: `${baseSrviceUrl}${'deleteService'}`,
        data: { id }
    })
        .done(() => {
            window.location.replace("/hotelServies.html");

        })
        .fail(response => {
            console.log(response);
        })
        .always(() => {
            console.log('ajax completed');
        });
}
getAllServices();