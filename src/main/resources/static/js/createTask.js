function isEmpty(el) {
    return !$.trim(el.html())
}

$(document).ready(function () {
    //Test
    var testCategory;
    var testComp;
    var testName;
    //Question
    var questionType;
    var questionText;
    var variant;
    var freeAnswer;
    var level;
    var testId;
    var questionComp;
    $('.addQuestion').click(function () {
        testCategory = $('#testCategory').val();
        testComp = $('#testComp').val();
        testName = $('#testName').val();
        if (!isEmpty($('#testQuestions'))) {
            questionType = $('#question' + $(this).attr("id") + ' #questionType').val();
            questionText = $('#question' + $(this).attr("id") + ' #questionText').val();
            variant = $('#question' + $(this).attr("id") + ' #variant').val();
            freeAnswer = $('#question' + $(this).attr("id") + ' #answer').val();
            level = $('#question' + $(this).attr("id") + ' #level').val();
            questionComp = $('#question' + $(this).attr("id") + ' #comp').val();
            testId = $(this).attr("id");
        }
        var testRequest = {
            test: {
                testCategory: testCategory,
                testComp: testComp,
                testName: testName,
            },
            testQuestion: {
                questionType: questionType,
                questionText: questionText,
                answer: freeAnswer,
                level: level,
                comp: questionComp
            },
            variant: variant,
            testId: testId
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/createTest",
            data: JSON.stringify(testRequest),
            dataType: 'json',
            success: function (data) {
                var oldQuestions = '';
                if (data[0].questionType != null) {
                    $.each(data, function (index, value) {
                        oldQuestions += '<div id="oldQuestion' + index + '">'
                            + '<p id="questionNumber' + index + '">Вопрос номер: ' + index + '</p>'
                            + '<p id="questionType' + index + '">Тип вопроса: ' + value.questionType + '</p>'
                            + '<p id="questionText' + index + '">Текст вопроса: ' + value.questionText + '</p>'
                            + '<p id="variant' + index + '">Варианты ответа: ' + value.questionVariants + '</p>'
                            + '<p id="answer' + index + '">Правильный ответ: ' + value.answer + '</p>'
                            + '<p id="level' + index + '">Уровень сложности: ' + value.level + '</p>'
                            + '<p id="competency' + index + '">Компетенция: ' + value.comp + '</p>'
                            + '<br/>'
                            + '</div>'
                    });
                }
                $('#oldTestQuestions').html(oldQuestions);
                $('.addQuestion').attr('id', data[0].test.id);
                var questions = '<div id="question' + data[0].test.id + '">';
                questions += '<select class="form-control" id="questionType" name="questionType">\n' +
                    '                                <option value="TEST">\n' +
                    '                                    Тестовый вопрос\n' +
                    '                                </option>\n' +
                    '                                <option value="FREE">\n' +
                    '                                    Свободный ответ\n' +
                    '                                </option>\n' +
                    '                                <option value="FILE">Вопрос\n' +
                    '                                    с загрузкой файла\n' +
                    '                                </option>\n' +
                    '                            </select>\n' +
                    '                            <br/><input type="text" class="form-control"\n' +
                    '                                   id="questionText" name="questionText"\n' +
                    '                                   placeholder="Введите текст вопроса"/>\n' +
                    '                            <br/>\n' +
                    '                            <input type="text" class="form-control" id="variant" name="variant"\n' +
                    '                                   placeholder="Введите варианты ответа через точку с запятой"/>\n' +
                    '                            <br/>\n' +
                    '                            <input type="text" class="form-control" id="answer"\n' +
                    '                                   name="answer"\n' +
                    '                                   placeholder="Введите правильный ответ"/>\n' +
                    '                            <label style="margin-top: 10px;">\n' +
                    '                                Уровень сложности:\n' +
                    '                                <select class="form-control" id="level" name="level">\n' +
                    '                                    <option value="LOW">\n' +
                    '                                        Легкий\n' +
                    '                                    </option>\n' +
                    '                                    <option value="MEDIUM">\n' +
                    '                                        Средний\n' +
                    '                                    </option>\n' +
                    '                                    <option value="HARD">\n' +
                    '                                        Сложный\n' +
                    '                                    </option>\n' +
                    '                                </select>\n' +
                    '                            </label>\n' +
                    '<br/>' +
                    '<label style="margin-top: 10px;">\n' +
                    'Название компетенции (для базовых тестов)' +
                    '<input type="text" class="form-control" id="comp" name="comp"\n' +
                    '                                   placeholder="Введите название компетенции"/>\n' +
                    '                            <br/>\n' +
                    '</label>' +
                    '                            <br/>\n' +
                    '                            <br/>';
                questions += '</div>';
                $('#testQuestions').html(questions);
            }
        })
    });
});