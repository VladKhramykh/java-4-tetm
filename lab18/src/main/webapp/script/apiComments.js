function getCommentForReference(id) {
    fetch('Comments?refId=' + id, {
        method: 'GET'
    }).then(res => {
        return res.json();
    }).then(res => {
        let data = `<table class="table">
                            <tr>
                                <td>
                                    <h1>Comments - ${id}--</h1>
                                    <input type="button" class="btn btn-secondary" onclick="visibilityCommentsInsertForm(${id})" value="insert">
                                    <input type="button" class="btn btn-danger" onclick="clearContent(${id})" value="Close comments">
                                    <div id="insertComment${id}"></div>
                                </td>
                            </tr>
                    </table>
                    <br>`
        if (res) {

            res.forEach(element => {
                let comments = `<table><tr><td>[${element.stamp}]`
                if (role == 'admin' || element.sessionId == sessionId)
                    comments += `<input type="button" class="btn btn-danger" value="delete" onclick="deleteComment(${element.id},${element.refId})"/>
                                 <input type="button" class="btn btn-warning" value="update" onclick="updateComment(${element.id},${element.refId})"/><br>
                                 <textarea id="txt${element.id}">${element.comment}</textarea>`
                else
                    comments += `<br><textarea readonly id="txt${element.id}">${element.comment}</textarea>`
                comments += `</td></tr></table><br>`
                data += comments;
            })
        }
        document.getElementById(id).innerHTML = data;
    })
}


function addComment(id) {
    fetch('Comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            comment: document.getElementById('comment').value,
            refId: id,
            sessionId: sessionId
        })
    }).then(() => {
        clearContent(id);
        getCommentForReference(id);
    })
}

function updateComment(id, refId) {
    let needId = 'txt' + id;
    fetch('Comments', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            comment: document.getElementById(needId).value
        })
    }).then(() => {
        getCommentForReference(refId);
    })
}

function deleteComment(id, refId) {
    fetch('Comments?id=' + id, {
        method: 'DELETE'
    }).then(() => {
        getCommentForReference(refId);
    })
}