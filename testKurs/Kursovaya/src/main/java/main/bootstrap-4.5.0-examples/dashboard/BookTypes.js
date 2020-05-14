var d = document;
var Id = 1;

function addRow()
{
    // Считываем значения с формы
    var Name   = d.getElementById('Name').value;
    var Cnt = d.getElementById('Cnt').value;
    var Fine  = d.getElementById('Fine').value;
    var DayCount  = d.getElementById('DayCount').value;


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


    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);

    // Наполняем ячейки
    td1.innerHTML = Id;
    td2.innerHTML = Name;
    td3.innerHTML = Cnt;
    td4.innerHTML = Fine;
    td5.innerHTML = DayCount;

    
    d.getElementById('Name').value= "";
    d.getElementById('Cnt').value= "";
    d.getElementById('Fine').value= "";
    d.getElementById('DayCount').value= "";
    
    Id = Id+1;
    
    let xhr = new XMLHttpRequest();
    let json = JSON.stringify({
      name:"bookType111",
      cnt : 10,
      fine : 100,
      dayCount : 1000
    });

    xhr.open("POST", '//localhost:8080/lib/addBookType')
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');

    xhr.send(json);

}