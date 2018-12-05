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
        type: "post",
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
}