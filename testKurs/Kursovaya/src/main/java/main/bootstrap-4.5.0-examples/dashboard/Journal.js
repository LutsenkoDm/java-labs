var d = document;
var Id = 1;

function addRow()
{
    // Считываем значения с формы
    var BookId   = d.getElementById('BookId').value;
    var ClientId = d.getElementById('ClientId').value;
    var DateBeg  = d.getElementById('DateBeg').value;
    var DateEnd  = d.getElementById('DateEnd').value;
    var DateRet  = d.getElementById('DateRet').value;

    // Находим нужную таблицу
    var tbody = d.getElementById('table').getElementsByTagName('TBODY')[0];

    // Создаем строку таблицы и добавляем ее
    var row = d.createElement("TR");
    tbody.appendChild(row);

    // Создаем ячейки в вышесозданной строке
    // и добавляем тх
    var td1 = d.createElement("TD");
    var td2 = d.createElement("TD");
    var td3 = d.createElement("TD");
    var td4 = d.createElement("TD");
    var td5 = d.createElement("TD");
    var td6 = d.createElement("TD");

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);

    // Наполняем ячейки
    td1.innerHTML = Id;
    td2.innerHTML = BookId;
    td3.innerHTML = ClientId;
    td4.innerHTML = DateBeg;
    td5.innerHTML = DateEnd;
    td6.innerHTML = DateRet;
    
    d.getElementById('BookId').value= "";
    d.getElementById('ClientId').value= "";
    d.getElementById('DateBeg').value= "";
    d.getElementById('DateEnd').value= "";
    d.getElementById('DateRet').value= "";
    
    Id = Id+1;
}