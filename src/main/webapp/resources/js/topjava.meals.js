$(function () {
    makeEditable({
            ajaxUrl: "user/meals/",
            datatableApi: $("#meals").DataTable({
                "paging": false,
                "info": true,
                "scrollY": '300px',
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    )
});

$("#datetimepicker").datetimepicker();

$(".datePicker").datetimepicker({
    timepicker: false,
    format: 'Y-m-d',//YYYY-MM-dd
});

$(".timePicker").datetimepicker({
    datepicker: false,
    format: 'H:i',//HH:mm
});
