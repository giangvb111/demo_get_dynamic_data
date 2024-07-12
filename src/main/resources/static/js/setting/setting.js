var number = 0;
var dataOld = [];
var tableName = [];

$(document).ready(function () {
    changeTableMaster();
});

// Function to create and append table
function createTable(data) {
    // Clear any existing table content
    $('.tableSetting').empty();
    var screenId = $('.tableMasterList').val();
    // Create the table element
    var table = $('<table></table>');
    var thead = $('<thead></thead>');
    var tbody = $('<tbody></tbody>');

    // Create table headers
    var headers = ['NO', 'id', 'screenId', 'tableName', 'columnName', 'columnWidth', 'status'];
    var tr = $('<tr></tr>');
    headers.forEach(function (header) {
        tr.append('<th>' + header + '</th>');
    });
    thead.append(tr);

    // Create table rows based on data
    if (data != null && data.length > 0) {
        data.forEach(function (item) {
            number = number + 1;
            var trBody = $('<tr></tr>');
            trBody.append('<td id="' + screenId + '_numberNo_' + number + '"><input id="numberNo_' + number + '" disabled class="numberNo" type="text" name="NO" value="' + number + '" /></td>');
            trBody.append('<td id="' + screenId + '_idSetting_' + number + '"><input id="idSetting_' + number + '" disabled type="text" name="id" value="' + item.id + '" /></td>');
            trBody.append('<td id="' + screenId + '_screenId_' + number + '"><input id="screenId_' + number + '" disabled type="text" name="screenId" value="' + item.screenId + '" /></td>');
            trBody.append('<td id="' + screenId + '_tableName_' + number + '"><input id="tableName_' + number + '" disabled type="text" name="screenId" value="' + item.tableName + '" /></td>');
            trBody.append('<td id="' + screenId + '_columnName_' + number + '"><input id="columnName_' + number + '" disabled type="text" name="screenId" value="' + item.columnName + '" /></td>');
            trBody.append('<td id="' + screenId + '_columnWidth_' + number + '"><input id="columnWidth_' + number + '" type="number" name="columnWidth" value="' + item.columnWidth + '" /></td>');
            trBody.append('<td id="' + screenId + '_status_' + number + '"><input id="status_' + number + '" type="checkbox" name="status"' + (item.status == 1 ? ' checked' : '') + ' /></td>');
            tbody.append(trBody);
        });
    }

    // Append thead and tbody to table
    table.append(thead);
    table.append(tbody);

    // Append table to tableSetting div
    $('.tableSetting').append(table);
}

function customColumnName(data, val, index) {
    var html = "";
    var selected = "";
    html += '<select id="columnName_' + index + '" class="columnName">'
    if (data != null) {
        html += '<option value=""></option>'
        for (let i = 0; i < data.length; i++) {
            if (data[i] == val) {
                selected = "selected"
            } else {
                selected = ""
            }
            html += '<option value="' + data[i] + '" ' + selected + '>' + data[i] + '</option>'
        }
    } else {
        html += '<option value=""></option>'
    }
    html += '</select>';
    return html;
}

function customTableName(data, val, index) {
    var html = "";
    var selected = "";
    html += '<select id="tableName_' + index + '" class="tableName" onchange="changeTableName(' + number + ')">'
    if (data != null) {
        html += '<option value=""></option>'
        for (let i = 0; i < data.length; i++) {
            if (data[i] == val) {
                selected = "selected"
            } else {
                selected = ""
            }
            html += '<option value="' + data[i] + '" ' + selected + '>' + data[i] + '</option>'
        }
    } else {
        html += '<option value=""></option>'
    }
    html += '</select>';
    return html;
}

function openPopupSetting(idScreen) {
    tableName = [];
    $.ajax({
        url: "http://localhost:8080/api/v1/setting/get-column-by-screen-id?screenId=" + idScreen,
        type: "GET",
        dataType: "json",
        success: function (data, json) {
            createTable(data);
            dataOld = data;
            tableName = data[0].tableNameList;
        },
        error: function (data, json) {
            $('.tableSetting').empty();
        }
    });
}

function addColumn() {
    messageError("")
    number = number + 1;
    var screenId = $('.tableMasterList').val();
    var tbody = $('.tableSetting table tbody');
    var trBody = $('<tr></tr>');
    trBody.append('<td id="' + screenId + '_numberNo_' + number + '"><input id="numberNo_' + number + '" disabled class="numberNo" type="text" name="NO" value="' + (number) + '" /></td>');
    trBody.append('<td id="' + screenId + '_idSetting_' + number + '"><input id="idSetting_' + number + '" disabled type="text" name="id" value="" /></td>');
    trBody.append('<td id="' + screenId + '_screenId_' + number + '"><input id="screenId_' + number + '" disabled type="text" name="screenId" value="' + screenId + '" /></td>');
    trBody.append('<td id="' + screenId + '_tableName_' + number + '">'+customTableName(tableName,"",number)+'</td>');
    trBody.append('<td id="' + screenId + '_columnName_' + number + '">' + customColumnName("", "", number) + '</td>');
    trBody.append('<td id="' + screenId + '_columnWidth_' + number + '"><input id="columnWidth_' + number + '" type="number" name="columnWidth" value="0" /></td>');
    trBody.append('<td id="' + screenId + '_status_' + number + '"><input id="status_' + number + '" checked type="checkbox" name="status" /></td>');
    tbody.append(trBody);
}

function changeTableMaster() {
    var value = $('.tableMasterList').val();
    openPopupSetting(value);
}

function changeTableName(index) {
    var tableName = $("#tableName_" + index).val();
    var screenId = $('.tableMasterList').val();
    $.ajax({
        url: "http://localhost:8080/api/v1/setting/list-column-name?tableName=" + tableName,
        type: "GET",
        dataType: "json",
        success: function (data, json) {
            $("#" + screenId + '_columnName_' + index).html(customColumnName(data, "", index));
        },
        error: function (data, json) {
            console.log(data);
        }
    });
}

function getDataFromTable() {
    messageError("")
    var dataArray = [];
    var screenId = $('.tableMasterList').val();
    var listDataChange = [];

    // Hiển thị màn hình loading
    $('#loading').show();

    $('.tableSetting table tbody tr').each(function () {
        var number = $(this).find('.numberNo').val();
        var row = {
            screenId: screenId,
            number: number,
            id: $('#idSetting_' + number).val(),
            tableName: $('#tableName_' + number).val(),
            columnName: $('#columnName_' + number).val(),
            dataType: "",
            columnWidth: parseInt($('#columnWidth_' + number).val()),
            status: $('#status_' + number).is(':checked') ? 1 : 0
        };
        dataArray.push(row);
    });

    for (let i = 0; i < dataOld.length; i++) {
        if (dataArray[i].columnWidth != dataOld[i].columnWidth || dataArray[i].status != dataOld[i].status) {
            listDataChange.push(dataArray[i])
        }
    }
    for (let i = dataOld.length; i < dataArray.length; i++) {
        listDataChange.push(dataArray[i])
    }

    if (listDataChange.length != 0){
        $.ajax({
            url: "http://localhost:8080/api/v1/setting/add-column",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(listDataChange),
            success: function (data, json) {
                changeTableMaster();
                $('#loading').hide();
            },
            error: function (data, json) {
                changeTableMaster();
                $('#loading').hide();
            }
        });
    }else {
        messageError("No data change!!!")
        $('#loading').hide();
    }

}

function messageError(value) {
    $(".errorMessage p").html(value)
}


