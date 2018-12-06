$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    // submit 클릭시 서버에 데이터가 넘어가지 않도록 설정
    e.preventDefault();
    console.log("click!!");

    var queryString = $(".answer-write").serialize();
    console.log("query: " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url: " + url);

    // 서버에 데이터 전송
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {

}

function onSuccess(data, status) {
    console.log(data);
    // show.html의 id = answerTemplate에 데이터를 동적으로 차례대로 넣기
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);

    // 해당 클래스 아래에 template 추가
    $(".qna-comment-slipp-articles").prepend(template);

    // jquery reset input
    $("textarea[name=contents]").val("");
}

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
    // 삭제 클릭시 이동하지 않도록 설정
    e.preventDefault();

    // 선택한 댓글의 url 정보를 가져옴
    var deleteBtn = $(this); // this 변수 유지
    var url = deleteBtn.attr("href");
    console.log("url: " + url);

    // 서버에 데이터 전송
    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};