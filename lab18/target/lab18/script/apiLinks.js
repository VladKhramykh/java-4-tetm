let sessionId;

function getReferences() {
    let url;
    document.getElementById("references").innerHTML = "";
    if (document.getElementById("filterDescription") == null)
        url = 'References';
    else
        url = 'References?filter=' + document.getElementById("filterDescription").value;

    fetch(url, {
        method: 'GET'
    }).then(res => {
        sessionId = res.headers.get('sessionId');
        return res.json();
    }).then(res => {
        if (res) {
            res.forEach(element => {
                let reference = `<table><tr><td>`;
                if (role == "admin")
                    reference += `<input type="button" class="btn btn-danger" value="delete" onclick="deleteContentVisibility(${element.id})">
                                 <input type="button" class="btn btn-info" value="update" onclick="updateContentVisibility('${element.id}','${element.url}','${element.description}')">`
                reference += `<input type="button" class="btn btn-info" value="+${element.plus}" onclick="updateReference('${element.id}','plus', '${element.plus}')">
                             <input type="button" class="btn btn-info" value="-${element.minus}" onclick="updateReference('${element.id}','minus', '${element.minus}')">
                             <input type="button" class="btn btn-primary" value="comments" onclick="getCommentForReference(${element.id})">
                             [${element.id}]
                             <a href="${element.url}">${element.description}</a>
                             </td></tr>
                             <tr><td id="${element.id}">
                             </td></tr>
                </table><br>`
                document.getElementById("references").innerHTML += reference;
            })
        }
    })
}

function addReference() {
    if (document.getElementById('url').value != "" && document.getElementById('description').value != "")
        fetch('References', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                url: document.getElementById('url').value,
                description: document.getElementById('description').value,
                role: role
            })
        }).then(() => {
            clearContent("form")
            getReferences();
        })
    else
        alert('Input fields must be filled')
}

function updateReference(id, recent, value) {
    let plus = -1;
    let minus = -1;
    let url, description;

    if (recent == "plus")
        plus = value;
    else if (recent == "minus")
        minus = value;
    if (document.getElementById('editUrl') != null)
        url = document.getElementById('editUrl').value;
    if (document.getElementById('editDescription') != null)
        description = document.getElementById('editDescription').value;
    fetch('References?role=' + role, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id, url: url, description: description, plus: plus, minus: minus, role: role
        })
    }).then(() => {
        getReferences();
    })
}

function deleteReference(id) {
    fetch('References?id=' + id + '&role=' + role, {
        method: 'DELETE'
    }).then(() => {
        getReferences();
    })
}