var settingList = [];
var listData = [];
$(document).ready(function () {
    var screenId = $('#screenId').val()
    $.ajax({
        url: "http://localhost:8080/api/v1/goods-receive/get-list?screenId="+screenId,
        type: "GET",
        dataType: "json",
        success: function (data, json) {
            if (data.length > 0) {
                listData = data.slice(0, -1);
                settingList = data[data.length - 1].settingDataList;
            }
            createTableList(listData);
        },
        error: function (data, json) {
            console.log(data)
        }
    })
})

function createTableList(data) {
    // Clear any existing table content
    $('.tableList').empty();

    // Create the table element
    var table = $('<table></table>');
    var thead = $('<thead></thead>');
    var tbody = $('<tbody></tbody>');

    // Create table rows based on data
    // Create table headers
    if (data != null && data.length > 0) {
        var trHead = $('<tr></tr>');
        // Get headers from keys of the first object in data
        var headers = Object.keys(data[0]);

        // Create a map for quick access to column widths
        var columnWidths = {};
        settingList.forEach(function (setting) {
            columnWidths[setting.columnName] = setting.columnWidth;
        });

        headers.forEach(function (key) {
            var th = $('<th>' + key + '</th>');
            if (columnWidths[key]) {
                th.css('width', columnWidths[key] + 'px');
            }
            trHead.append(th);
        });
        thead.append(trHead);

        // Create table rows based on data
        data.forEach(function (item, index) {
            var trBody = $('<tr></tr>');
            headers.forEach(function (key) {
                var value = item[key] !== undefined ? item[key] : '';
                var inputType = (typeof value === 'boolean') ? 'checkbox' : 'text';
                var inputValue = (typeof value === 'boolean') ? '' : ' value="' + value + '"';
                var checked = (typeof value === 'boolean' && value) ? ' checked' : '';
                var td = $('<td><input class="inputList" disabled type="' + inputType + '" name="' + key + '"' + inputValue + checked + ' /></td>');
                if (columnWidths[key]) {
                    td.css('width', columnWidths[key] + 'px');
                }
                trBody.append(td);
            });
            tbody.append(trBody);
        });
    }

    // Append thead and tbody to table
    table.append(thead);
    table.append(tbody);

    // Append table to tableList div
    $('.tableList').append(table);
}
