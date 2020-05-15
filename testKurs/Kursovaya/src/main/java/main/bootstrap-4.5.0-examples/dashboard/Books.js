"use strict"

var d = document;
var Id = 1;

async function addRow()
{
    // Считываем значения с формы
    var Name   = d.getElementById('Name').value;
    var Cnt = d.getElementById('Cnt').value;
    var TypeId  = d.getElementById('TypeId').value;


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

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);

    // Наполняем ячейки
    td1.innerHTML = Id;
    td2.innerHTML = Name;
    td3.innerHTML = Cnt;
    td4.innerHTML = TypeId;

    
    d.getElementById('Name').value= "";
    d.getElementById('Cnt').value= "";
    d.getElementById('TypeId').value= "";

    
    Id = Id+1;
/*
    let xhr = new XMLHttpRequest();

    xhr.open('GET', "http://localhost:8080/lib/bookTypes");

    xhr.send();

    xhr.onload = () => {
        alert( 'Ошибка: ' + xhr.status + JSON.parse(xhr.response()));
    }
    */

    let json = await JSON.stringify({
        name:"bookType111",
        cnt : 10,
        fine : 100,
        dayCount : 1000
    });

    const response = await fetch('http://localhost:8080/lib/addBookType', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: json
    });

    if (response.ok) {
        alert((await response.json()).name);
    }




   // HTTP ошибка?
  // получим ответ из xhr.response


    // обработаем ошибку

}

async function getOne() {
    const response = await fetch('http://localhost:8080/lib/addBookType');

    if (response.ok) {
        alert(await response.json());
    }
}