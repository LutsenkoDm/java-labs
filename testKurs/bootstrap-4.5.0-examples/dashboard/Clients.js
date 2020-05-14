var d = document;
var Id = 1;

function addRow()
{
    // Считываем значения с формы
    var FirstName   = d.getElementById('FirstName').value;
    var LastName = d.getElementById('LastName').value;
    var PatherName  = d.getElementById('PatherName').value;
    var PassportSeria  = d.getElementById('PassportSeria').value;
    var PassportNum  = d.getElementById('PassportNum').value;

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
    td2.innerHTML = FirstName;
    td3.innerHTML = LastName;
    td4.innerHTML = PatherName;
    td5.innerHTML = PassportSeria;
    td6.innerHTML = PassportNum;
    
    d.getElementById('FirstName').value= "";
    d.getElementById('LastName').value= "";
    d.getElementById('PatherName').value= "";
    d.getElementById('PassportSeria').value= "";
    d.getElementById('PassportNum').value= "";
    
    Id = Id+1;
}